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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServices {

    @Autowired
    private ImageServices imageServices;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterRepository characterRepository;

    public void validate(String title, Date creationDate, Double qualification) throws ErrorServices {
        if (title==null || title.isEmpty()){
            throw new ErrorServices("The title cannot be empty.");
        }
        if (creationDate==null){
            throw new ErrorServices("The date cannot be empty.");
        }
        if (qualification==null || qualification < 0){
            throw new ErrorServices("The qualification cannot be empty.");
        }
    }

    @Transactional
    public Movie saveMovie(MultipartFile archive, String title, String gender, String creationDate, Double qualification, List personajes) throws ErrorServices, Exception {

        Movie movie = new Movie();

        if (archive!=null && !archive.isEmpty()){
            Image image = imageServices.saveImage(archive);
            movie.setImage(image);
        }
        movie.setTitle(title);
        movie.setGender(gender);
        movie.setCreationDate(new Date());
        movie.setQualification(qualification);
        movie.setPersonajes(personajes);

        movieRepository.save(movie);
        return movie;

    }

    @Transactional
    public Movie editMovie(Integer idMovie, MultipartFile archive, String title, String gender, String creationDate, Double qualification, Integer idPersonaje) throws ErrorServices, Exception {

        Optional<Movie> answer1 = movieRepository.findById(idMovie);

        if (answer1.isPresent()){
            Movie movie = answer1.get();

            if (archive!=null && !archive.isEmpty()){
                if (movie.getImage()!=null){
                    Integer idImage = movie.getImage().getId();
                    Image image = imageServices.editImage(idImage, archive);
                    movie.setImage(image);
                }
                else {
                    Image image = imageServices.saveImage(archive);
                    movie.setImage(image);
                }
            }
            movie.setTitle(title);
            movie.setGender(gender);
            movie.setCreationDate(new Date());
            movie.setQualification(qualification);

            Personaje personaje = characterRepository.getById(idPersonaje);
            List<Personaje> listPersonajes = movie.getPersonajes();
            listPersonajes.add(personaje);
            movie.setPersonajes(listPersonajes);

            List<Movie> listMovie = personaje.getMovies();
            listMovie.add(movie);
            personaje.setMovies(listMovie);

            movieRepository.save(movie);
            characterRepository.save(personaje);

            return movie;

        }else{
            throw new ErrorServices("No found the movie solicited.");
        }
    }

    @Transactional
    public void eliminateMovie(Integer idMovie) throws ErrorServices{
        Optional<Movie> answer = movieRepository.findById(idMovie);
        if (answer.isPresent()){
            Movie movie = answer.get();
            movieRepository.delete(movie);
        }
        else {
            throw new ErrorServices("Requested movie not found.");
        }
    }

    @Transactional
    public Movie getById(Integer id){
        return movieRepository.findMovieById(id);
    }

    @Transactional
    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    @Transactional
    public Movie findByTitle(String title) throws ErrorServices {
        Optional<Movie> movie = Optional.ofNullable(movieRepository.findMovieByTitle(title));
        if (movie.isPresent()) {
            Movie aux = movie.get();
            return aux;
        } else {
            throw new ErrorServices("Not found the movie solicited.");
        }
    }

    @Transactional
    public Movie findByGender(String gender) throws ErrorServices {
        Optional<Movie> movie = Optional.ofNullable(movieRepository.findMovieByGender(gender));
        if (movie.isPresent()) {
            Movie aux = movie.get();
            return aux;
        } else {
            throw new ErrorServices("Not found the movie solicited.");
        }
    }
}
