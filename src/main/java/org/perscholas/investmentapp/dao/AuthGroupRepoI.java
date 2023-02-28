package org.perscholas.investmentapp.dao;

import org.perscholas.investmentapp.models.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthGroupRepoI extends JpaRepository<AuthGroup,Integer> {
    List<AuthGroup> findByEmail(String email);
}
