package org.perscholas.investmentapp.dao;

import org.perscholas.investmentapp.models.UserPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPositionRepoI extends JpaRepository<UserPosition,Integer> {
    List<UserPosition> findByUser(int userID);
}
