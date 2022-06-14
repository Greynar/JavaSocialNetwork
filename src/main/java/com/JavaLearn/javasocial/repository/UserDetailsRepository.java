package com.JavaLearn.javasocial.repository;

import com.JavaLearn.javasocial.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    @EntityGraph(attributePaths = { "subscriptions", "subscribers" })
    Optional<User> findById(String s);
}
