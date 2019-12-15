package by.danilov.domain;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "withId", types = {Performer.class})
public interface PerformerProjection {

    String getId();

    String getName();

    String getCreationYear();

    String getNumberOfMembers();

    String getGenre();

    String getCity();

    String getCountry();

    List<Musician> getMusicians();
}
