package by.danilov.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseBuilder {
    private ResponseBuilder() {
    }

    public static ResponseEntity<Response> responseOk(String message) {
        Response response = new Response(HttpStatus.OK.value(), message);
        return ResponseEntity.ok(
                response
        );
    }

    public static ResponseEntity<Response> responseBadRequest(String message) {
        Response response = new Response(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.badRequest().body(
                response
        );
    }
}
