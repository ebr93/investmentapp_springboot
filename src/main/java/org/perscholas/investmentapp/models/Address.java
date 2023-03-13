package org.perscholas.investmentapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "address")
@Slf4j
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    @NotBlank(message = "Please provide a street address")
    @Size(min = 5, max = 45,
            message = "Please provide two-letter abbreviation ")
    String street;
    @NonNull
    @NotBlank(message = "Please provide a valid state")
    @Size(min = 2, max = 2,
            message = "Please provide two-letter abbreviation ")
    String state;
    @NonNull
    @Min(value=00501, message = "Zipcode should not be less than 5 digits long")
    @Max(value=99999, message = "Zipcode should not be longer than 5 digits " +
            "long")
    int zipcode;

    @Override
    public String toString() {
        return String.format("%s, %s, %s", street, state, zipcode);
    }
}
