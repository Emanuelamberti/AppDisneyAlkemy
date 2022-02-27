package com.appDisney.application.Controller;

import com.appDisney.application.Entities.Movie;
import com.appDisney.application.Entities.Personaje;
import com.appDisney.application.Errors.ErrorServices;
import com.appDisney.application.Repository.MovieRepository;
import com.appDisney.application.Services.MovieServices;
import com.appDisney.application.Services.PersonajeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class MovieControllers {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieServices movieServices;

    @Autowired
    private PersonajeServices personajeServices;

    @GetMapping("/listMovies")
    public String movies(ModelMap model){
        model.put("listMovies", movieRepository.findAll());
        return "listMovies";
    }

    //@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
    @GetMapping("/newMovie")
    public String newMovie(ModelMap model){

        return "newMovie";
    }

    @PostMapping("/newMovie")
    public String newMovie(ModelMap model, @RequestParam String title,@RequestParam String gender, @RequestParam String creationDate,
                                 @RequestParam Double qualification,
                                 @RequestParam(required = false) List<Personaje> personajes, @RequestParam MultipartFile image) throws Exception {
        try {
            movieServices.saveMovie(image, title, gender, creationDate, qualification, personajes);
            return "redirect:/listMovies";
        } catch (ErrorServices ex) {
            model.put("error", ex.getMessage());
            model.put("title",title);
            model.put("gender", gender);
            model.put("creationDate",creationDate);
            model.put("qualification",qualification);
            model.put("personaje", personajes);
            model.put("image",image);
            return "redirect:/listMovies";
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
    @GetMapping("/editMovie/{id}")
    public String editMovie(ModelMap model, @PathVariable("id") Integer id){
        model.put("movie", movieServices.getById(id));
        model.put("listPersonajes", personajeServices.allCharacters());
        return "editMovie";
    }

    @PostMapping("/editMovie/{id}")
    public String editMovie(ModelMap model, @PathVariable("id") Integer id, @RequestParam String title, @RequestParam String gender, @RequestParam String creationDate,
                                 @RequestParam Double qualification,
                                 @RequestParam(required = false) Integer idPersonaje, @RequestParam MultipartFile image) throws Exception {
        try {

            movieServices.editMovie(id, image, title, gender, creationDate, qualification, idPersonaje);
            return "redirect:/listMovies";
        } catch (ErrorServices ex) {
            model.put("error", ex.getMessage());
            model.put("title",title);
            model.put("gender", gender);
            model.put("creationDate",creationDate);
            model.put("qualification",qualification);
            model.put("personaje", idPersonaje);
            model.put("image",image);
            return "redirect:/listMovies";
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
    @GetMapping("/eliminateMovie/{id}")
    public String eliminateMovie(ModelMap model, HttpSession session, @PathVariable ("id") Integer id) throws ErrorServices{
        Movie movie = movieRepository.findMovieById(id);
        model.put("titleMovie", movie.getTitle());
        model.put("idMovie", movie.getId());
        return "redirect:/listMovies";
    }

    @GetMapping("/movie/{idMovie}")
    public String movie(ModelMap model, @PathVariable(name = "idMovie", required = true) Integer idMovie){

        Movie movie = movieServices.getById(idMovie);

        model.put("movieId", movie.getId());
        model.put("titleMovie", movie.getTitle());
        model.put("genderMovie", movie.getGender());
        model.put("creationDateMovie", movie.getCreationDate());
        model.put("qualificationMovie" , movie.getQualification());
        model.put("imageMovie", movie.getImage());
        model.put("listPersonajeMovie", movie.getPersonajes());
        return "movie";
    }

}
