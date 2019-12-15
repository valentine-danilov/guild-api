package by.danilov.domain;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "withId", types = {Musician.class})
public interface MusicianProjection {

    String getId();

    String getFirstName();

    String getLastName();

    String getBirthDate();

    String getPseudonym();

    Performer getPerformer();

}
