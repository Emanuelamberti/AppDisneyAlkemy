package com.appDisney.application.Repository;

import com.appDisney.application.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer>{

    @Query(value = "SELECT * FROM User WHERE User.id = :id", nativeQuery = true)
    public Usuario findUserById(@Param("id") Integer id);

    @Query(value = "SELECT * FROM User WHERE User.userName = :userName", nativeQuery = true)
    public Usuario findUserByUserName(@Param("userName") String userName);

    @Query(value = "SELECT * FROM User WHERE User.email = :email", nativeQuery = true)
    public Usuario findUserByEmail(@Param("email") String email);
}
