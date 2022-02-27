package com.appDisney.application.Repository;

import com.appDisney.application.Entities.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {

    @Query(value = "SELECT c FROM Gender c WHERE c.id = :id", nativeQuery = true)
    public Gender findGenderById(@Param("id") Integer id);
}
