package com.tsp.controller;

import com.tsp.service.FileThread;
import com.tsp.service.ProblemInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/Files")
public class FileController {
    @Autowired
    ProblemInstanceService orderService;

    @Autowired
    FileThread ft;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity insertFile(@RequestParam("file") MultipartFile file) throws IOException {
        String directoryName = "./files";
        Path path = Paths.get(directoryName);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        File newFile = File.createTempFile("tsp_",".txt",new File(directoryName ));
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ft.setPath(newFile.getAbsolutePath());
        ft.setUsername(auth.getName() + "");
        ft.run();
        return new ResponseEntity("Wysyłanie pliku powiodło się!", HttpStatus.OK);
    }

}
