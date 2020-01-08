package de.imp.bdr.helloworld;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GreetingController {


    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    public GreetingController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }


    @RequestMapping("/hello")
    public AtomicReference<ResponseEntity> hello() {

        String accept = request.getHeader("Accept");
        AtomicReference<ResponseEntity> response = new AtomicReference<ResponseEntity>();

        try {

            System.out.println("GET /hello");

            response.set(new ResponseEntity("Hello World!", HttpStatus.OK));

        } catch (Exception e) {

            response.set(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));
        }

        return response;
    }

    @RequestMapping("/health")
    public AtomicReference<ResponseEntity> health() {

        String accept = request.getHeader("Accept");
        AtomicReference<ResponseEntity> response = new AtomicReference<ResponseEntity>();

        try {

            System.out.println("GET /health");

            response.set(new ResponseEntity(HttpStatus.OK));

        } catch (Exception e) {

            response.set(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));
        }

        return response;
    }


}
