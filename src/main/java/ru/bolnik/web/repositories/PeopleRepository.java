package ru.bolnik.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bolnik.web.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
