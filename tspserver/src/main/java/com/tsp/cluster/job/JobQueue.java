package com.tsp.cluster.job;

import com.tsp.cluster.common.Solution;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.repository.ProblemInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class JobQueue {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobQueue.class.getName());
    private static ProblemInstanceRepository problemRepository;

    private static BlockingQueue<JobContext> jobQueue = new LinkedBlockingQueue<>();
    private static JobContext currentJob;

    private JobQueue() {

    }

    public static void setProblemRepository(ProblemInstanceRepository repository) {
        problemRepository = repository;
    }

    public synchronized static void receiveTaskSolution(Solution solution) {
        currentJob.updateSolution(solution);
    }

    private synchronized static void saveAndUpdateCurrentJob() {
        saveCurrentJobState();
        updateCurrentJob();
    }

    public synchronized static void saveCurrentJobState() {
        problemRepository.save(currentJob.getProblemInstance());
        LOGGER.info("Saved current job state");
    }

    public synchronized static void updateCurrentJobProgress() {
        ProblemInstance currentInstance = currentJob.getProblemInstance();
        currentInstance.setPercentageOfProgress(currentJob.getPercentageOfProgress());
        currentInstance.setCost(currentJob.getBestSolution().getCost());
        currentInstance.setTour(currentJob.getBestSolution().getTour());
        currentInstance.setPercentageOfProgress(currentJob.getPercentageOfProgress());
        long timeInSeconds = new Date().getTime() - currentInstance.getDateOfOrdering().getTime();
        currentInstance.setTimeOfRunningInSecconds(timeInSeconds);
        currentJob.setProblemInstance(currentInstance);
        if (currentJob.getNumOfAvailableTasks() == 0)
            markCurrentJobAsFinished();
    }

    public synchronized static void markCurrentJobAsFinished() {
        ProblemInstance currentInstance = currentJob.getProblemInstance();
        currentInstance.setSolving(false);
        long timeInSeconds = new Date().getTime() - currentInstance.getDateOfOrdering().getTime();
        currentInstance.setTimeOfRunningInSecconds(timeInSeconds);
        currentInstance.setIndexInQueue(-1);
        currentJob.setProblemInstance(currentInstance);
        currentJob.setJobState(JobState.FINISHED);
        LOGGER.info("Current job: JOB ID {} Was marked as FINISHED", currentJob.getProblemInstance().getId());
    }

    public synchronized static void markCurrentJobAsCancelled() {
        ProblemInstance currentInstance = currentJob.getProblemInstance();
        currentInstance.setSolving(false);
        currentInstance.setIndexInQueue(-1);
        long timeInSeconds = new Date().getTime() - currentInstance.getDateOfOrdering().getTime();
        currentInstance.setTimeOfRunningInSecconds(timeInSeconds);
        currentJob.setProblemInstance(currentInstance);
        currentJob.setJobState(JobState.CANCELLED);
        LOGGER.info("Current job: JOB ID {} Was marked as CANCELLED", currentJob.getProblemInstance().getId());
    }

    public synchronized static void updateCurrentJob() {
        currentJob = getNextAvailableJob();
        currentJob.setJobState(JobState.RUNNING);
        currentJob.problemInstance.setIndexInQueue(1);
        updateJobIndices();
        LOGGER.info("Updated current job: JOB ID: {}", currentJob.getProblemInstance().getId());
    }

    public synchronized static void cancelJob(long id) {
        if (currentJob.getProblemInstance().getId() == id) {
            markCurrentJobAsCancelled();
        } else {
            removeFromQueue(id);
        }
    }


    private synchronized static void handleJobPause() {

    }

    public synchronized static void handleJobFinish() {
        if (currentJob != null) {
            saveCurrentJobState();
        }
        updateCurrentJob();
    }

    private static void handleRunningJob() {
        if (!currentJob.areAnyTasksAvailable())
            handleJobFinish();
    }

    public synchronized static void changeCurrentJobIfNeeded() {
        switch (currentJob.jobState) {
            case ENQUEUED:
                break;
            case RUNNING:
                handleRunningJob();
                break;
            case PAUSED:
                handleJobPause();
                break;
            case FINISHED:
            case CANCELLED:
                break;
        }
    }

    public synchronized static JobContext getCurrentJob() {
        if (currentJob == null) {
            updateCurrentJob();
        }
        return currentJob;
    }

    public static boolean shouldContinueExecution() {
        return currentJob != null
                && isCurrentJobStateRunnable()
                && currentJob.areAnyTasksAvailable();
    }

    private synchronized static JobContext getNextAvailableJob() {
        JobContext ctx = null;
        try {
            LOGGER.info("Waiting for next job to appear");
            ctx = jobQueue.take();
            ctx.setJobState(JobState.RUNNING);
            ctx.setDate();
            LOGGER.info("Next job appeared");
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted while waiting for next job due to: {}", e.getMessage());
            e.printStackTrace();
        }
        return ctx;
    }

    private synchronized static void updateJobIndices() {
        ArrayList<JobContext> list = new ArrayList<>(jobQueue);
        for(int i = 0; i < jobQueue.size(); i++) {
            JobContext ctx = list.get(i);
            ctx.setPositionInQueue(ctx.getPositionInQueue() -1);
            list.set(i, ctx);
        }
        jobQueue.clear();
        jobQueue.addAll(list);
        jobQueue.forEach((jobContext -> problemRepository.save(jobContext.getProblemInstance())));
    }

    public synchronized static void initQueue(Collection<JobContext> jobs) {
        jobQueue.addAll(jobs);
    }

    public synchronized static void pauseJob(int id) {

    }

    public synchronized static void resumeJob(int id) {

    }


    public synchronized static void changeJobPriority(int id, int priority) {

    }

    public synchronized static void enqueue(JobContext context) {
        LOGGER.info("Trying to enqueue new job. ID: {}", context.getProblemInstance().getId());
        if (!isCurrentJobStateRunnable()) {
            context.problemInstance.setIndexInQueue(1);
        }
        else if (jobQueue.isEmpty()) {
            context.problemInstance.setIndexInQueue(2);
        }
        else {
            context.problemInstance.setIndexInQueue(jobQueue.size() + 2);
        }
        context.problemInstance.setState(JobState.ENQUEUED);
        jobQueue.add(context);
        problemRepository.save(context.getProblemInstance());
        LOGGER.info("Enqueued new job. ID: {}", context.getProblemInstance().getId());
        if (currentJob == null)
            updateCurrentJob();
    }

    public synchronized static JobContext removeFromQueue(long id) {
        ArrayList<JobContext> list = new ArrayList<>(jobQueue);
        int index = getJobIndex(list, id);
        if (index != -1) {

            JobContext ctx = list.get(index);
            for (int i = index; i < list.size(); i++) {
                JobContext ct = list.get(i);
                ProblemInstance p = ct.getProblemInstance();
                p.setIndexInQueue(p.getIndexInQueue() - 1);
                ct.setProblemInstance(p);
                list.set(i, ct);
            }
            list.remove(ctx);
            ctx.setJobState(JobState.CANCELLED);
            problemRepository.save(ctx.getProblemInstance());
            jobQueue.clear();
            jobQueue.addAll(list);
            jobQueue.forEach((jobContext -> problemRepository.save(jobContext.getProblemInstance())));
            return ctx;
        }
        return null;
    }


    private static boolean isCurrentJobStateRunnable() {
        return currentJob != null && !(currentJob.jobState == JobState.FINISHED || currentJob.jobState == JobState.CANCELLED || currentJob.jobState == JobState.PAUSED);
    }

    public synchronized static void moveUp(long id) {
        if (jobQueue.size() > 1)
            return;
        ArrayList<JobContext> list = new ArrayList<>(jobQueue);
        int index = getJobIndex(list, id);
        if (index > 1) {
            JobContext temp = list.get(index - 1);
            JobContext job = list.get(index);
            list.set(index - 1, job);
            list.set(index, temp);
            list.forEach((jobContext -> problemRepository.save(jobContext.getProblemInstance())));
            jobQueue.clear();
            jobQueue.addAll(list);
        }

    }

    public synchronized static void moveDown(long id) {
        if (jobQueue.size() < 2)
            return;
        ArrayList<JobContext> list = new ArrayList<>(jobQueue);
        int index = getJobIndex(list, id);
        if (index < jobQueue.size() - 1) {
            JobContext temp = list.get(index + 1);
            JobContext job = list.get(index);
            list.set(index + 1, job);
            list.set(index, temp);
            list.forEach((jobContext -> problemRepository.save(jobContext.getProblemInstance())));
            jobQueue.clear();
            jobQueue.addAll(list);
        }

    }

    public static int getJobIndex(ArrayList<JobContext> list, long id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).problemInstance.getId() == id)
                return i;
        }
        return -1;
    }

    public synchronized void saveQueueState() {

    }

}
