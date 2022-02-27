package com.appDisney.application.Controller;

import com.appDisney.application.Entities.Personaje;
import com.appDisney.application.Errors.ErrorServices;
import com.appDisney.application.Repository.CharacterRepository;
import com.appDisney.application.Services.MovieServices;
import com.appDisney.application.Services.PersonajeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CharacterControllers {

    @Autowired
    private CharacterRepository characterRepositorsy;

    @Autowired
    private PersonajeServices personajeServices;

    @Autowired
    private MovieServices movieService;

    @GetMapping("/listPersonajes")
    public String characters(Model model){
        model.addAttribute("listaPersonajes" , personajeServices.allCharacters() );
        return "listPersonajes";
    }

    //@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
    @GetMapping("/newPersonaje")
    public String newCharacter(ModelMap model){

        return "newPersonaje";
    }

    @PostMapping("/newPersonaje")
    public String newCharacter(ModelMap model, @RequestParam String name, @RequestParam Integer age,
                                 @RequestParam Double weight,
                                 @RequestParam String history, @RequestParam(required = false) MultipartFile image, @RequestParam(required = false) List movie){
        try {

            personajeServices.saveCharacter(name, age, weight, history, image, movie);
            return "redirect:/listPersonajes";
        } catch (ErrorServices ex) {
            model.put("error", ex.getMessage());
            model.put("image",image);
            model.put("name",name);
            model.put("age",age);
            model.put("weight",weight);
            model.put("history", history);
            model.put("movie",movie);
            return "redirect:/listPersonajes";
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
    @GetMapping("/editPersonaje/{id}")
    public String editCharacter(ModelMap model, @PathVariable ("id") Integer id){
        Personaje personaje = personajeServices.getByid(id);
        model.put("personaje", personaje);
        model.put("movies", movieService.allMovies());
        return "editPersonajes";
    }

    @PostMapping("/editPersonaje/{id}")
    public String editCharacter(ModelMap model, @PathVariable (name = "id", required = true) Integer id, @RequestParam String name, @RequestParam Integer age,
                                @RequestParam Double weight,
                                @RequestParam String history, @RequestParam MultipartFile image, @RequestParam Integer idMovie){
        try {
            personajeServices.editCharacter(id, name, age, weight, history, image, idMovie);

            return "redirect:/listPersonajes";
        } catch (ErrorServices ex) {
            model.put("error", ex.getMessage());
            model.put("image",image);
            model.put("name",name);
            model.put("age",age);
            model.put("weight",weight);
            model.put("history", history);
            model.put("idMovie",idMovie);
            return "redirect:/listPersonajes";
        }
    }

    //@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
    @GetMapping("/eliminateCharacter/{id}")
    public String eliminateCharacter(ModelMap model, HttpSession session, @PathVariable ("id") Integer id) throws ErrorServices{
        Personaje personaje = personajeServices.getByid(id);
        model.put("nameCharacter", personaje.getName());
        model.put("idCharacter", personaje.getId());
        return "redirect:/listPersonajes";
    }

    @GetMapping("/personaje/{idPersonaje}")
    public String personaje(ModelMap model, @PathVariable(name = "idPersonaje", required = true) Integer idPersonaje){
        Personaje personaje = personajeServices.getByid(idPersonaje);

        model.put("personaje", personaje);

        model.put("idPersonaje", personaje.getId());
        model.put("namePersonaje", personaje.getName());
        model.put("agePersonaje", personaje.getAge());
        model.put("weightPersonaje" , personaje.getWeight());
        model.put("historyPersonaje", personaje.getHistory());
        model.put("imagePersonaje", personaje.getImage());
        model.put("listMoviePersonaje", personaje.getMovies());
        return "personaje";
    }


}
