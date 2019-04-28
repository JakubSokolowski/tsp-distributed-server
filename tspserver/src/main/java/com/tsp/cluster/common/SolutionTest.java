package com.tsp.cluster.common;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SolutionTest {

    @Test
    void compareToGreater() {
        // given
        Solution left = new Solution(5);
        Solution right = new Solution(6);
        int expected = 1;

        // when
        int actual = left.compareTo(right);

        // then
        Assert.assertEquals(expected, actual);
    }
}