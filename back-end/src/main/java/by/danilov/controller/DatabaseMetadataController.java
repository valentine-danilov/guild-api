package by.danilov.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/metadata", method = RequestMethod.GET)
public class DatabaseMetadataController {

    @GetMapping
    public ResponseEntity getMetadata() {

        throw new RuntimeException();

    }

}
