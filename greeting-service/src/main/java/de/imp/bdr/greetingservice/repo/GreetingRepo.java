package de.imp.bdr.greetingservice.repo;

import de.imp.bdr.greetingservice.model.Greeting;

import java.util.List;

public interface GreetingRepo {

    void addGreeting(Greeting greeting) throws RepoException;
    public List<Greeting> getAllGreetings() throws RepoException;
    public Greeting getGreetingById(Long id) throws RepoException;

}
