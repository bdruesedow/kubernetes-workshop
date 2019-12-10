package de.imp.bdr.k8sdemo.Controller;

import de.imp.bdr.k8sdemo.Model.Greeting;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {

        System.out.println("GET /greeting - parameter: name=" + name);

        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
