package com.tsp.cluster.instance;

import com.tsp.graph.SymmetricMatrix;
import com.tsp.cluster.common.Algorithm;

import java.util.Arrays;
import java.util.Vector;

public class HardcodedInstanceProvider implements ProblemInstanceProvider {
    @Override
    public ProblemInstance getProblemInstance() {
        int INF = Integer.MAX_VALUE;
        Integer[][] arr =  {
                { INF, 10,  8,   9,   7 },
                { 10,  INF, 10,  5,   6 },
                { 8,   10,  INF, 8,   9 },
                { 9,   5,   8,   INF, 6 },
                { 7,   6,   9,   6,  INF }
        };
        Vector<Vector<Integer>> vec = new Vector<>();
        for(Integer[] list : arr) {
            vec.add(new Vector<>(Arrays.asList(list)));
        }
        SymmetricMatrix mat = new SymmetricMatrix(vec);
        return new ProblemInstance(Algorithm.BRUTE_FORCE, mat);
    }
}
