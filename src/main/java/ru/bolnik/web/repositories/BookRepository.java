package ru.bolnik.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bolnik.web.models.Book;
import ru.bolnik.web.models.Person;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByPerson(Person person);

    List<Book> findByTitleStartingWith(String title);
}
