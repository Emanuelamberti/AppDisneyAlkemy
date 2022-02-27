package com.appDisney.application.Repository;

import com.appDisney.application.Entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT c FROM Image c WHERE c.id = :id", nativeQuery = true)
    public Image findImageById(@Param("id") Integer id);
}
