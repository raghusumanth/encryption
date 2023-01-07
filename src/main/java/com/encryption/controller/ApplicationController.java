package com.encryption.controller;

import com.encryption.model.EncryptInfo;
import com.encryption.model.InputData;
import com.encryption.service.AESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApplicationController {


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AESService aesService;

    @GetMapping("/beans")
    public ResponseEntity<List<String>> getBeans(){
        List<String> beans= Arrays.asList(applicationContext.getBeanDefinitionNames());
        return new ResponseEntity<>(beans, HttpStatus.OK);
    }


    @PostMapping("/aesencrypt")
    public ResponseEntity<EncryptInfo> aesEncrypt(@RequestBody InputData inputData){
        //AES only supports key sizes of 16, 24 or 32 bytes
        EncryptInfo encryptInfo = aesService.encryptAES(inputData.getData(), inputData.getKey());
        return new ResponseEntity<>(encryptInfo,HttpStatus.OK);
    }

    @PostMapping("/aesdecrypt")
    public ResponseEntity<String> aesEncrypt(@RequestBody EncryptInfo encryptInfo, @RequestParam String key){
        //AES only supports key sizes of 16, 24 or 32 bytes
        String content = aesService.aesDecrypt(encryptInfo, key);
        return new ResponseEntity<>(content,HttpStatus.OK);
    }
}
