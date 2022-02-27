package com.appDisney.application.Services;

import com.appDisney.application.Entities.Image;
import com.appDisney.application.Entities.Movie;
import com.appDisney.application.Entities.Personaje;
import com.appDisney.application.Errors.ErrorServices;
import com.appDisney.application.Repository.CharacterRepository;
import com.appDisney.application.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PersonajeServices {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ImageServices imageServices;

    public void validate(String name, Integer age, Double weight, String history) throws ErrorServices{
        if (name==null || name.isEmpty()){
            throw new ErrorServices("The name cannot be empty.");
        }
        if (age==null || age < 0){
            throw new ErrorServices("The age cannot be empty, or less than 0.");
        }
        if (weight==null || weight < 0){
            throw new ErrorServices("The weight cannot be empty, or less than 0.");
        }
        if (history==null || history.isEmpty()){
            throw new ErrorServices("The history cannot be empty.");
        }
    }

    @Transactional
    public Personaje saveCharacter(String name, Integer age, Double weight, String history, MultipartFile archive, List movies) throws ErrorServices{

        validate(name, age, weight, history);

        Personaje personaje = new Personaje();

        personaje.setName(name);
        personaje.setAge(age);
        personaje.setWeight(weight);
        personaje.setHistory(history);

        if (archive!=null && !archive.isEmpty()){
            Image image = imageServices.saveImage(archive);
            personaje.setImage(image);
        }
        personaje.setMovies(movies);

        characterRepository.save(personaje);

        return personaje;
    }


    @Transactional
    public Personaje editCharacter(Integer idCharacter , String name,
                                   Integer age, Double weight,
                                   String history, MultipartFile archive,
                                   Integer idMovie) throws ErrorServices{

        validate(name, age, weight, history);

        Optional<Personaje> answer1 = characterRepository.findById(idCharacter);

        if (answer1.isPresent()){
            Personaje character = answer1.get();

            character.setName(name);
            character.setAge(age);
            character.setWeight(weight);
            character.setHistory(history);

            if (archive!=null && !archive.isEmpty()){
                if (character.getImage()!=null){
                    Integer idImage = character.getImage().getId();
                    Image image = imageServices.editImage(idImage, archive);
                    character.setImage(image);
                }
                else {
                    Image image = imageServices.saveImage(archive);
                    character.setImage(image);
                }
            }

            Movie movieEnviada = movieRepository.getById(idMovie);//Traigo la peli segun la id
            List<Movie> peliculasActuales = character.getMovies();//Creo una lista igual a las peliculas de mi personaje actual
            peliculasActuales.add(movieEnviada);//Añado la pelicula enviada a la lista de mi personaje
            character.setMovies(peliculasActuales);//Setteo a mi personaje actual la lista actualizada de peliculas

            List<Personaje> personajesDeLaPeli = movieEnviada.getPersonajes();//Creo una lista de personajes igual a la lista de personajes de mi pelicula
            personajesDeLaPeli.add(character);//Añado el personaje actual a la lista de peliculas de la pelicula enviada
            movieEnviada.setPersonajes(personajesDeLaPeli);//Setteo la nueva lista de personajes actualizada a la pelicula

            characterRepository.save(character);
            movieRepository.save(movieEnviada);

            return character;

        }else{
            throw new ErrorServices("No found the character solicited.");
        }
    }
    //Creado por Franco

    @Transactional
    public void eliminateCharacter(Integer idCharacter) throws ErrorServices{
        Optional<Personaje> answer = characterRepository.findById(idCharacter);
        if (answer.isPresent()){
            Personaje character = answer.get();
            characterRepository.delete(character);
        }
        else {
            throw new ErrorServices("Requested character not found.");
        }
    }

    @Transactional
    public Personaje findByName(String name) throws ErrorServices {
        Optional<Personaje> respuesta = Optional.ofNullable(characterRepository.findCharacterByName(name));
        if (respuesta.isPresent()) {
            Personaje personaje = respuesta.get();
            return personaje;
        } else {
            throw new ErrorServices("Not found the character solicited.");
        }
    }

    @Transactional
    public List<Personaje> allCharacters(){
        return characterRepository.findAll();
    }

    @Transactional
    public Personaje getByid(Integer id){
        return characterRepository.getById(id);
    }

    @Transactional
    public List<Personaje> findByAge(Integer age) throws ErrorServices {
        List<Personaje> personajes = characterRepository.findCharactersByAge(age);
            return personajes;
    }

    @Transactional
    public Personaje findByWeight(Double weight) throws ErrorServices {
        Optional<Personaje> respuesta = Optional.ofNullable(characterRepository.findCharacterByWeight(weight));
        if (respuesta.isPresent()) {
            Personaje personaje = respuesta.get();
            return personaje;
        } else {
            throw new ErrorServices("Not found the character solicited.");
        }
    }

    @Transactional
    public List<Personaje> findByMovie(String movieTitle) throws ErrorServices {
        List<Personaje> personajes = characterRepository.findCharacterByMovie(movieTitle);
        return personajes;
    }

}
