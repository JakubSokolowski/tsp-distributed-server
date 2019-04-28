package com.tsp.cluster.task.context;

import org.junit.Assert;


class BruteForceTaskContextTest {

    @org.junit.jupiter.api.Test
    void serialize() {
        // given
        BruteForceTaskContext context = new BruteForceTaskContext(new Integer[]{1, 2});
        String expected = "[1, 2]";

        // when
        String actual = context.serialize();

        // then
        Assert.assertEquals(expected, actual);
    }
}