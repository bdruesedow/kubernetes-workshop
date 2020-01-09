package de.imp.bdr.greetingservice.repo;

import de.imp.bdr.greetingservice.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingJpaRepo extends JpaRepository<Greeting, Long> {

}
