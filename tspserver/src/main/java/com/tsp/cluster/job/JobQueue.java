package com.tsp.cluster.job;

import com.tsp.cluster.common.Solution;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.repository.ProblemInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
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
        currentJob.setProblemInstance(currentInstance);
    }

    public synchronized static void markCurrentJobAsFinished() {
        ProblemInstance currentInstance = currentJob.getProblemInstance();
        currentInstance.setSolving(false);
        currentJob.setProblemInstance(currentInstance);
    }

    public static void updateCurrentJob() {
        currentJob = getNextAvailableJob();
        LOGGER.info("Updated current job state");
    }

    private synchronized static void handleJobPause() {

    }

    private synchronized static void handleJobFinish() {
        saveCurrentJobState();
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
        if (currentJob == null)
            updateCurrentJob();
        return currentJob;
    }

    public static boolean shouldContinueExecution() {
        return currentJob != null && currentJob.areAnyTasksAvailable();
    }

    private static synchronized JobContext getNextAvailableJob() {
        JobContext ctx = null;
        try {
            LOGGER.info("Waiting for next job to appear");
            ctx = jobQueue.take();
            ctx.setJobState(JobState.RUNNING);
            LOGGER.info("Next job to appeared");
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted while waiting for next job due to: {}", e.getMessage());
            e.printStackTrace();
        }
        return ctx;
    }

    public synchronized static void initQueue(Collection<JobContext> jobs) {
        jobQueue.addAll(jobs);
    }

    public synchronized static void pauseJob(int id) {

    }

    public synchronized static void resumeJob(int id) {

    }

    public synchronized static void cancelJob(int id) {

    }

    public synchronized static void changeJobPriority(int id, int priority) {

    }

    public static void enqueue(JobContext context) {
        jobQueue.offer(context);
        LOGGER.info("Enqueued new job. ID: {}", context.getProblemInstance().getId());
        if (currentJob == null)
            updateCurrentJob();
    }

    public synchronized static void notifyEnqueued() {
        jobQueue.notifyAll();
    }

    private synchronized void handleJobCancel() {

    }
}
