package com.appDisney.application.Services;

import com.appDisney.application.Entities.Image;
import com.appDisney.application.Errors.ErrorServices;
import com.appDisney.application.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServices {

    @Autowired
    private ImageRepository imageRepository;

    public void validate(MultipartFile archive) throws ErrorServices {
        if (archive==null || archive.isEmpty()){
            throw new ErrorServices("The file is empty.");
        }
    }

    @Transactional
    public Image saveImage(MultipartFile archive) throws ErrorServices{
        validate(archive);
        try {
            Image image = new Image();
            image.setMime(archive.getContentType());
            image.setContent(archive.getBytes());
            imageRepository.save(image);
            return image;
        }
        catch (IOException ex) {
            System.err.print(ex.getMessage());
            return null;
        }
    }

    @Transactional
    public Image editImage(Integer id, MultipartFile archive) throws ErrorServices{
        validate(archive);
        try {
            Image image = new Image();
            Optional<Image> answer = imageRepository.findById(id);
            if (answer.isPresent()){
                image.setMime(archive.getContentType());
                image.setContent(archive.getBytes());
                imageRepository.save(image);
            }
            else {
                throw new ErrorServices("Photo to replace not found.");
            }
            return image;
        }
        catch (IOException ex) {
            System.err.print(ex.getMessage());
            return null;
        }
    }

}
