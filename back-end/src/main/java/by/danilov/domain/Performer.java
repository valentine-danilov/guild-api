package by.danilov.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "performers", schema = "music_bands")
public class Performer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_num")
    private Integer id;

    @Column(name = "performer_name")
    private String name;

    @Column(name = "creation_year")
    private int creationYear;

    @Column(name = "number_of_members")
    private int numberOfMembers;

    @Column(name = "genre")
    private String genre;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "performer")
    private List<Musician> musicians;

    @Transient
    private String linkedCatalog = "musicians";

}
