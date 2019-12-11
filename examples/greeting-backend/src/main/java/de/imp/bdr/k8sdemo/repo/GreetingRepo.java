package de.imp.bdr.k8sdemo.repo;

import de.imp.bdr.k8sdemo.model.Greeting;

import java.util.List;

public interface GreetingRepo {

    void addGreeting(Greeting greeting) throws RepoException;
    public List<Greeting> getAllGreetings() throws RepoException;
    public Greeting getGreetingById(Long id) throws RepoException;

}
