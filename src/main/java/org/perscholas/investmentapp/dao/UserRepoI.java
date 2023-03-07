package org.perscholas.investmentapp.dao;

import org.perscholas.investmentapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepoI extends JpaRepository<User,Integer> {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAllIgnoreCase(String email);
}
