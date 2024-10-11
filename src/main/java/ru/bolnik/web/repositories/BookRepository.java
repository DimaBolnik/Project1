package ru.bolnik.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bolnik.web.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
