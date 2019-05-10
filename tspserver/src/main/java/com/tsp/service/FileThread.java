package com.tsp.service;

import com.tsp.graph.SymmetricMatrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class FileThread extends Thread {
    private String path;
    public FileThread(String path)
    {
        this.path = path;
    }
    @Override
    public void run() {
        Vector<Vector<Integer>> matrix = new Vector<Vector<Integer>>();
        super.run();
        try {
            String data = "";
            data = new String(Files.readAllBytes(Paths.get(path)));
            ArrayList<String> words = new ArrayList<String>(Arrays.asList( data.split("\\s+") ) );
            words.removeIf(w -> w.equals(":"));
            int size = 0;
            for(int i=0;i<words.size();i++)
            {
                if(words.get(i).equals("DIMENSION:") || words.get(i).equals("DIMENSION"))
                {
                    i++;
                    size = Integer.parseInt(words.get(i));
                }else if(words.get(i).equals("EDGE_WEIGHT_SECTION:") || words.get(i).equals("EDGE_WEIGHT_SECTION"))
                {
                    Vector<Integer> bufor = new Vector<Integer>();
                    for(int a=0;a<size;a++) {
                        bufor = new Vector<Integer>();
                        for (int b = 0; b < size; b++) {
                            i++;
                            bufor.add(new Integer(Integer.parseInt(words.get(i))));
                        }
                        matrix.add(bufor);
                    }
                }
            }
            for(int i = 0;i<words.size();i++)matrix.get(i).set(i,Integer.MAX_VALUE);
            SymmetricMatrix sm = new SymmetricMatrix(matrix);
            Files.deleteIfExists(Paths.get(this.path));
            System.out.println(sm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
