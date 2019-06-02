package com.tsp.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class GraphParser {
    public static GraphRepresentation parse(String data) {
        Vector<Vector<Integer>> matrix = new Vector<Vector<Integer>>();
        ArrayList<String> words = new ArrayList<String>(Arrays.asList(data.split("\\s+")));
        words.removeIf(w -> w.equals(":"));
        int size = 0;
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).equals("DIMENSION:") || words.get(i).equals("DIMENSION")) {
                i++;
                size = Integer.parseInt(words.get(i));
            } else if (words.get(i).equals("EDGE_WEIGHT_SECTION:") || words.get(i).equals("EDGE_WEIGHT_SECTION")) {
                Vector<Integer> buffer;
                for (int a = 0; a < size; a++) {
                    buffer = new Vector<>();
                    for (int b = 0; b < size; b++) {
                        i++;
                        buffer.add(Integer.parseInt(words.get(i)));
                    }
                    matrix.add(buffer);
                }
            }
        }
        for (int i = 0; i < size; i++) matrix.get(i).set(i, Integer.MAX_VALUE);
        return new SymmetricMatrix(matrix);
    }
}
