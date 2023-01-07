package com.encryption.service;

import com.encryption.model.EncryptInfo;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AESService {


    public EncryptInfo encryptAES(String data,String key){
        try{

            byte[] iv=new byte[16];
            SecureRandom random=new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivParameterSpec=new IvParameterSpec(iv);

            byte[] plainBytes=data.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKeySpec=new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8),"AES");

            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParameterSpec);

            byte[] encrypted=cipher.doFinal(plainBytes);

            String ivNew= Base64.getEncoder().encodeToString(iv);
            String dataNew=Base64.getEncoder().encodeToString(encrypted);
            EncryptInfo encryptInfo=EncryptInfo.builder().processK(ivNew).processD(dataNew).build();
            return encryptInfo;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

    }


    public String aesDecrypt(EncryptInfo encryptInfo, String key){
        try{
            if(null==encryptInfo.getProcessK()
                    || encryptInfo.getProcessK().trim().length()==0
            ||null==encryptInfo.getProcessD()
            || encryptInfo.getProcessD().trim().length()==0){
                return null;
            }
            String processK=encryptInfo.getProcessK();
            String processD=encryptInfo.getProcessD();
            byte[]  kBytes=Base64.getDecoder().decode(processK.getBytes(StandardCharsets.UTF_8));
            byte[] dBytes=Base64.getDecoder().decode(processD.getBytes(StandardCharsets.UTF_8));
            IvParameterSpec ivParameterSpec=new IvParameterSpec(kBytes);
            SecretKeySpec secretKeySpec=new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8),"AES");
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivParameterSpec);
            byte[] decrypted=cipher.doFinal(dBytes);
            return new String(decrypted,StandardCharsets.UTF_8);
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
