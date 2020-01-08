package de.imp.bdr.k8sdemo.repo;

import de.imp.bdr.k8sdemo.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GreetingDatabaseRepo implements GreetingRepo {

    private final GreetingJpaRepo jpaRepo;

    @Autowired
    public GreetingDatabaseRepo(GreetingJpaRepo jpaRepo) {

        this.jpaRepo = jpaRepo;
    }

    @Override
    public List<Greeting> getAllGreetings() throws RepoException {

        return jpaRepo.findAll();
    }

    @Override
    public Greeting getGreetingById(Long id) throws RepoException {

        return jpaRepo.getOne(id);
    }

    @Override
    public void addGreeting(Greeting greeting) {

        jpaRepo.save(greeting);
    }

}
