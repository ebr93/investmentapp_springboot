package org.perscholas.investmentapp.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.perscholas.investmentapp.models.Address;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    String firstName;
    String LastName;
    String email;
    Address address;
}
