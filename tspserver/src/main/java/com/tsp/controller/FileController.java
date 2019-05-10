package com.tsp.controller;

import com.tsp.bean.Order;
import com.tsp.service.FileThread;
import com.tsp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/Files")
public class FileController {
    @Autowired
    OrderService orderService;

    @Autowired
    FileThread ft;
    
    @RequestMapping(method = RequestMethod.GET)
    public Collection<Order> getAllOrder(){
        List<Order> orders = (List<Order>) orderService.findAll();
        return orders;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable("id") long id){
        return orderService.findOne(id);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOrderById(@PathVariable("id") long id){
        orderService.deleteOne(id);
    }
    
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateOrderById(@RequestBody Order order){
        orderService.updateOne(order);
    }

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
        ft.setPath(newFile.getAbsolutePath());
        ft.start();
        return new ResponseEntity("Wysyłanie pliku powiodło się!", HttpStatus.OK);
    }

}
