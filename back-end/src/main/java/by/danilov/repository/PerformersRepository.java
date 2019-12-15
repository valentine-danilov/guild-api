package by.danilov.repository;

import by.danilov.domain.Performer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "performers", path = "performers")
public interface PerformersRepository extends PagingAndSortingRepository<Performer, Integer> {

}
