package by.danilov.controller;

import by.danilov.domain.Musician;
import by.danilov.domain.Performer;
import by.danilov.repository.MusiciansRepository;
import by.danilov.repository.PerformersRepository;
import by.danilov.response.CatalogSubmitRequest;
import by.danilov.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

   import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.danilov.constant.GeneralConstants.*;
import static by.danilov.response.ResponseBuilder.responseBadRequest;
import static by.danilov.response.ResponseBuilder.responseOk;

@RestController
@RequestMapping("/catalog")
public class CrudController {

    private MusiciansRepository musiciansRepository;
    private PerformersRepository performerRepository;

    public CrudController(MusiciansRepository musiciansRepository,
                          PerformersRepository performerRepository) {
        this.musiciansRepository = musiciansRepository;
        this.performerRepository = performerRepository;
    }


    @PostMapping("/performers/submit")
    public ResponseEntity<Response> submitPerformersCatalog(
            @RequestBody List<CatalogSubmitRequest<Performer>> request) {

        Map<String, List<Performer>> updates = getUpdates(request);

        try {
            performerRepository.saveAll(updates.get(MODIFIED));
            performerRepository.saveAll(updates.get(CREATED));
            performerRepository.deleteAll(updates.get(DELETED));
        } catch (Exception e) {
            return responseBadRequest(e.getMessage());
        }

        return responseOk("UPDATED");
    }



    @PostMapping("/musicians/submit")
    public ResponseEntity<Response> submitMusiciansCatalog(
            @RequestBody List<CatalogSubmitRequest<Musician>> request) {

        Map<String, List<Musician>> updates = getUpdates(request);

        try {
            musiciansRepository.saveAll(updates.get(MODIFIED));
            musiciansRepository.saveAll(updates.get(CREATED));
            musiciansRepository.deleteAll(updates.get(DELETED));
        } catch (Exception e) {
            return responseBadRequest(e.getMessage());
        }

        return responseOk("OK");
    }

    private <T> Map<String, List<T>> getUpdates(List<CatalogSubmitRequest<T>> request) {

        List<T> modified = request.stream()
                .filter(CatalogSubmitRequest::isModified)
                .map(CatalogSubmitRequest::getContent)
                .collect(Collectors.toList());

        List<T> deleted = request.stream()
                .filter(CatalogSubmitRequest::isDeleted)
                .map(CatalogSubmitRequest::getContent)
                .collect(Collectors.toList());

        List<T> created = request.stream()
                .filter(CatalogSubmitRequest::isCreated)
                .map(CatalogSubmitRequest::getContent)
                .collect(Collectors.toList());

        Map<String, List<T>> updates = new HashMap<>();
        updates.put(MODIFIED, modified);
        updates.put(DELETED, deleted);
        updates.put(CREATED, created);

        return updates;
    }
}
