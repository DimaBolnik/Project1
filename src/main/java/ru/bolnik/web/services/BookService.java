package ru.bolnik.web.services;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bolnik.web.models.Book;
import ru.bolnik.web.models.Person;
import ru.bolnik.web.repositories.BookRepository;
import ru.bolnik.web.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> byId = bookRepository.findById(id);
        return byId.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Person> getBookOwner(int id) {
        Book book = bookRepository.findById(id).get();
        return Optional.ofNullable(book.getPerson());
//        Session session = sessionFactory.getCurrentSession();
//        Book book = session.get(Book.class, id);
//        return Optional.ofNullable(book.getPerson());
//        return jdbcTemplate.query("select person.* from book join person on book.person_id = person.id" +
//               " where book.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
//                .stream().findAny();
    }

    @Transactional
    public void release(int id) {
        Book book = bookRepository.findById(id).get();
        book.deleteUser();
    }

    @Transactional()
    public void assign(int id, Person person) {
        Book book = bookRepository.findById(id).get();
        book.setPerson(person);
    }
}
