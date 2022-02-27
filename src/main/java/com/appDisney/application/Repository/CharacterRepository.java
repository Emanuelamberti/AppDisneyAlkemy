package com.appDisney.application.Repository;

import com.appDisney.application.Entities.Personaje;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Personaje, Integer>{

    //@Query("SELECT c FROM Character c WHERE c.id = :id")
    //public Optional<Personaje> findOptionalCharacterById(@Param("id") Integer id);

    @Query(value = "SELECT c FROM personaje c WHERE c.name = :name", nativeQuery = true)
    public Personaje findCharacterByName(@Param("name") String name);

    @Query(value = "SELECT c FROM personaje c WHERE c.age = :age ", nativeQuery = true)
    public List<Personaje> findCharactersByAge(@Param("age") Integer age);

    @Query(value = "SELECT c FROM personaje c WHERE c.weight = :weight", nativeQuery = true)
    public Personaje findCharacterByWeight(@Param("weight") Double weight);

    @Query(value = "SELECT c FROM personaje c WHERE c.movies.title = :movieTitle", nativeQuery = true)
    public List<Personaje> findCharacterByMovie(@Param("movieTitle") String movieTitle);

    //@Query("SELECT * FROM Character")
    //public List<Personaje> listAllCharacters(@Param("name") String name);

}
