package by.danilov.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "people", schema = "music_bands")
public class Musician {

    private static final String PERFORMERS = "performers";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_num")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "pseudonym")
    private String pseudonym;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    private Performer performer;

    @Transient
    private String linkedCatalog = PERFORMERS;
}
