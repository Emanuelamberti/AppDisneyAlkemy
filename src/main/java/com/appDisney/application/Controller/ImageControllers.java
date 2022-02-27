package com.appDisney.application.Controller;

import com.appDisney.application.Entities.Movie;
import com.appDisney.application.Entities.Personaje;
import com.appDisney.application.Entities.Usuario;
import com.appDisney.application.Errors.ErrorServices;
import com.appDisney.application.Repository.CharacterRepository;
import com.appDisney.application.Repository.MovieRepository;
import com.appDisney.application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/image")
public class ImageControllers {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/personaje")
    public ResponseEntity<byte[]> imageCharacter(@RequestParam Integer id) throws ErrorServices {
        try {
            Personaje character = characterRepository.getById(id);
            if (character.getImage()==null){
                throw new ErrorServices("This character does not have a photo");
            }
            byte[] image = character.getImage().getContent();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(image,headers, HttpStatus.OK);
        }
        catch(Exception ex){
            System.out.println("ERROR_IMAGE");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/movie")
    public ResponseEntity<byte[]> imageMovie(@RequestParam Integer id) throws ErrorServices{
        try {
            Movie movie = movieRepository.getById(id);
            if (movie.getImage()==null){
                throw new ErrorServices("This movie does not have a photo");
            }
            byte[] image = movie.getImage().getContent();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(image,headers,HttpStatus.OK);
        }
        catch(Exception ex){
            System.out.println("ERROR_IMAGE");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<byte[]> imageUsuario(@RequestParam Integer id) throws ErrorServices{
        try {
            Usuario usuario = userRepository.getById(id);
            if (usuario.getProfilePicture()==null){
                throw new ErrorServices("This user does not have a photo");
            }
            byte[] image = usuario.getProfilePicture().getContent();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(image,headers,HttpStatus.OK);
        }
        catch(Exception ex){
            System.out.println("ERROR_IMAGE");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
