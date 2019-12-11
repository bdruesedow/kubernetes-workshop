package de.imp.bdr.k8sdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.imp.bdr.k8sdemo.model.Greeting;
import de.imp.bdr.k8sdemo.repo.GreetingRepo;
import de.imp.bdr.k8sdemo.repo.RepoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private GreetingRepo repo;

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    public GreetingController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping("/greeting")
    public AtomicReference<ResponseEntity> greetingGet(@RequestParam(value="name", defaultValue="World") String name) {

        System.out.println("GET /greeting - parameter: name=" + name);

        String accept = request.getHeader("Accept");
        AtomicReference<ResponseEntity> response = new AtomicReference<ResponseEntity>();
        Greeting greeting = new Greeting(counter.incrementAndGet(), "Hello " + name + "!");

        try {
            repo.addGreeting(greeting);
            response.set(new ResponseEntity(greeting, HttpStatus.OK));
        } catch (RepoException e) {
            response.set(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));
        }

        return response;
    }

    @RequestMapping("/getGreetings")
    public AtomicReference<ResponseEntity> getGreetings() {

        String accept = request.getHeader("Accept");
        AtomicReference<ResponseEntity> response = new AtomicReference<ResponseEntity>();
        List<Greeting> greetings = new ArrayList<>();

        try {

            System.out.println("GET /getGreetings");
            greetings = repo.getAllGreetings();

            response.set(new ResponseEntity(greetings, HttpStatus.OK));

        } catch (RepoException e) {

            response.set(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));
        }

        return response;
    }

}
