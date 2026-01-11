package com.ecommerce.Ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    
    @Override
    public String uploadFile(String path, MultipartFile image) throws IOException {
        String originalFileName = image.getOriginalFilename();
        String random = UUID.randomUUID().toString();
        assert originalFileName != null;
        String uniqueFileName = random.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + uniqueFileName;

        // create folder
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();

        // upload to the server
        Files.copy(image.getInputStream() , Paths.get(filePath));


        return uniqueFileName;
    }
    
}
