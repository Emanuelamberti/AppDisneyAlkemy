package com.appDisney.application.Repository;

import com.appDisney.application.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query(value = "SELECT * FROM Movie WHERE Movie.id = :id", nativeQuery = true)
    public Movie findMovieById(@Param("id") Integer id);

    @Query(value = "SELECT * FROM Movie WHERE Movie.title = :title", nativeQuery = true)
    public Movie findMovieByTitle(@Param("title") String title);

    @Query(value = "SELECT * FROM Movie WHERE Movie.gender = :gender", nativeQuery = true)
    public Movie findMovieByGender(@Param("gender") String gender);

}
