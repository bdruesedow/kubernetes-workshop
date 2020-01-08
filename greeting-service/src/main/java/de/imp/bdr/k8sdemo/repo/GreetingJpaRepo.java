package de.imp.bdr.k8sdemo.repo;

import de.imp.bdr.k8sdemo.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreetingJpaRepo extends JpaRepository<Greeting, Long> {

}
