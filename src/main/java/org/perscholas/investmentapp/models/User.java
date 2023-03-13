package org.perscholas.investmentapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
@Entity
@Table(name = "myusers")
@Slf4j
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;


    @Column(name = "first_name", length = 30)
    @NotBlank(message = "Please provide a first name.")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Please provide a first name of " +
            "length 2 to " +
            "30")

    String firstName;

    @Column(name = "last_name", length = 30)
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Please provide a first name of " +
            "length 2 to " +
            "30")
    @NotBlank(message = "Please provide a last name.")

    String lastName;

    @Column(name = "email")
    @NotBlank(message = "Please provide an email")
    @Email(message = "Provide a valid email address", regexp = ".+@.+\\..+")
    String email;

    @Column(name = "password", length = 100)
    String password;

    @OneToOne(fetch = FetchType.EAGER, cascade =  {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "address", referencedColumnName = "id")
    Address address;

    @OneToMany(fetch = FetchType.EAGER, cascade =  {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "myuser_portfolio",
            joinColumns = @JoinColumn(name = "myuser_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "possession_id", referencedColumnName = "id"))
    @ToString.Exclude
    List<Possession> userPossessions = new ArrayList<>();

    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = new BCryptPasswordEncoder(4).encode(password);
    }

    public void setPassword(String password) {

        this.password = new BCryptPasswordEncoder(4).encode(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, password);
    }

    public void addPossession(Possession possession) {
        userPossessions.add(possession);
        log.debug("add user position executed!");
    }

    public void removePossession(Possession possession) {
        userPossessions.remove(possession);
        log.debug("remove user position executed!");
    }
}