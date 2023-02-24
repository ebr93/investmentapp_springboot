package org.perscholas.investmentapp.dao;

import org.perscholas.investmentapp.models.UserPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPositionRepoI extends JpaRepository<UserPosition,Integer> {

}
