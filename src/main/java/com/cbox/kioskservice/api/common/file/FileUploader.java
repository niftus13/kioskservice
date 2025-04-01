package com.cbox.kioskservice.api.common.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

import java.io.*;
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


    public List<String> uploadFiles(List<MultipartFile> files, boolean makeThumbnail){

        if (files == null || files.size()==0){
            throw new UploadException("Can Not Find Files");
        }

        List<String> uploadFileName = new ArrayList<>();
        log.info("path : "+path);
        log.info(files);

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
