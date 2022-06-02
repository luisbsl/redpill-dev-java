package br.com.redpillrh.casedevjava.repository;

import br.com.redpillrh.casedevjava.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
    Optional<Person> findByIdentifier(String integer);
}
