package com.tsp.service;

import com.tsp.bean.User;
import com.tsp.cluster.common.Algorithm;
import com.tsp.cluster.instance.ProblemInstance;
import com.tsp.graph.SymmetricMatrix;
import com.tsp.repository.ProblemInstanceRepository;
import com.tsp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

@Component
public class FileThread {
    @Autowired
    ProblemInstanceRepository problemRepository;

    @Autowired
    UserRepository userRepository;

    private String path;
    private String username;
    public FileThread()
    {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void run() {
        Vector<Vector<Integer>> matrix = new Vector<Vector<Integer>>();
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
            for(int i = 0;i<size;i++)matrix.get(i).set(i,Integer.MAX_VALUE);
            SymmetricMatrix sm = new SymmetricMatrix(matrix);
            Files.deleteIfExists(Paths.get(this.path));
            ProblemInstance problem = new ProblemInstance(Algorithm.BRUTE_FORCE, sm);
            User user = userRepository.findOne(username);
            problem.setUser(user);
            problem.setDateOfOrdering(new Date());
            problemRepository.save(problem);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
