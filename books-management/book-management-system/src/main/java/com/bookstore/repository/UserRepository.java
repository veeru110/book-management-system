package com.bookstore.repository;

import com.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailAndIsDeletedFalseAndIsActiveTrue(String email);

    @Query(value = "select count(userId) from User where role!=com.bookstore.constants.UserRole.ADMIN and isActive=true and isDeleted=false")
    Integer countAllUsers();

}
