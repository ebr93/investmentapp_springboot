package org.perscholas.investmentapp.dao;

import org.perscholas.investmentapp.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepoI extends JpaRepository<Address, Integer> {
    Optional<Address> findAddressByStreetAndZipcodeAllIgnoreCase(String street, int zip);
}
