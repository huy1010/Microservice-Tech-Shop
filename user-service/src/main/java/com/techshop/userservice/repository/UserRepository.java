package com.techshop.userservice.repository;


import com.techshop.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.activeFlag <> 'D'")
    List<User> findUsers();

    @Query("SELECT u FROM User u WHERE u.activeFlag <> 'D'")
    List<User> findCustomers();

    int countByUsernameAndPassword(String username, String password);

    User getByUsername(String username);

    int countByUsername(String username);

    int countByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
