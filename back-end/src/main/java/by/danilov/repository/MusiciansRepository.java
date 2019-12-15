package by.danilov.repository;

import by.danilov.domain.Musician;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "musicians", path = "musicians")
public interface MusiciansRepository extends PagingAndSortingRepository<Musician, Integer> {
}
