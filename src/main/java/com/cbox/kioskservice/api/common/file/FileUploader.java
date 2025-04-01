package com.cbox.kioskservice.api.common.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class FileUploader {

    public static class UploadException extends RuntimeException{
        public UploadException(String msg){
            super(msg);
        }
    }

    @Value("${com.kiosk.upload.path}")
    private String path;

    @PostConstruct
    public void init(){
        File tempFolder = new File(path);
        if(tempFolder.exists() == false){
            tempFolder.mkdir();
        }
        path = tempFolder.getAbsolutePath();
        log.info("path : "+ path);
        log.info("--------------------");
    }
    

    public List<String> uploadFiles(List<MultipartFile> files, boolean makeThumbnail){

        if (files == null || files.size()==0){
            throw new UploadException("Can Not Find Files");
        }

        List<String> uploadFileName = new ArrayList<>();

        for(MultipartFile mfile : files){

            String originFileName = mfile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            String fileName = uuid +"_"+ originFileName;

            File saveFile = new File(uuid, fileName);

            try (InputStream in = mfile.getInputStream();
                OutputStream out = new FileOutputStream(saveFile))
                {

                    FileCopyUtils.copy(in, out);
                    if(makeThumbnail){
                        File thumbOutFile = new File(path,"s_"+fileName);
                        Thumbnailator.createThumbnail(saveFile, thumbOutFile,200,200);
                    }

                    uploadFileName.add(fileName);



            } catch (Exception e) {
                throw new UploadException("FileUpload_ Error : "+e.getMessage());
            }

        }

        return uploadFileName;

    }

     // Get방식 파일이름 호출, 헤더 메시지
     public ResponseEntity<Resource> getFile(String fileName){
        Resource resource = new FileSystemResource(path+ File.separator + fileName);

        if(!resource.isReadable()){
            resource = new FileSystemResource(path+File.separator+"default.jpeg");
        }

        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    public void removeFiles(List<String> fileNames){

        if (fileNames == null || fileNames.size()==0){
            return;
        }
        for (String fname : fileNames){

            File original = new File(path, fname);
            File thumb = new File(path,"s_"+fname);

            if(thumb.exists()){
                thumb.delete();
            }
            original.delete();

        }

    }

    
    
}
