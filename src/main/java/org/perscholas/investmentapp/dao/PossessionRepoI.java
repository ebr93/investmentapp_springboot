package org.perscholas.investmentapp.dao;

import org.perscholas.investmentapp.models.Possession;
import org.perscholas.investmentapp.models.Stock;
import org.perscholas.investmentapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PossessionRepoI extends JpaRepository<Possession,Integer> {
    Optional<Possession> findByUserAndStock(User user, Stock stock);
}
