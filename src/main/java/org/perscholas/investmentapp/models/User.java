package org.perscholas.investmentapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")
@Slf4j
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @NonNull
    @Column(name = "first_name", length = 45)
    String firstName;

    @NonNull
    @Column(name = "last_name", length = 45)
    String lastName;

    @NonNull
    @Column(name = "email", length = 45)
    String email;

    @NonNull
    @Column(name = "password", length = 100)
    String password;

    @OneToOne(fetch = FetchType.EAGER, cascade =  {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "address", referencedColumnName = "id")
    Address address;

    @OneToMany(fetch = FetchType.EAGER, cascade =  {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "user_portfolio",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_stock_id", referencedColumnName = "id"))
    @ToString.Exclude
    List<Possession> userPossessions = new ArrayList<>();

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