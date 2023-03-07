package org.perscholas.investmentapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "possessions")
@Slf4j
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Possession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @NonNull
    @Column(name = "shares")
    double shares;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "myuser_id")
    User user;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "investment_id")
    Stock stock;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Possession possession = (Possession) o;
        return Objects.equals(user, possession.user) && Objects.equals(stock, possession.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, stock);
    }
}