package org.perscholas.investmentapp.models;

import jakarta.persistence.*;
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
    int Id;
    @NonNull
    String street;
    @NonNull
    String state;
    @NonNull
    int zipcode;

    @Override
    public String toString() {
        return String.format("%s %s %s", street, state, zipcode);
    }
}
