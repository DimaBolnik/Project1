package ru.bolnik.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bolnik.web.models.Book;
import ru.bolnik.web.models.Person;
import ru.bolnik.web.repositories.BookRepository;

import java.util.Date;
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

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(Sort.by("year"));
        }else {
            return bookRepository.findAll();
        }
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(PageRequest.of(page,
                    booksPerPage, Sort.by("year"))).getContent();
        }else {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    public Book findOne(int id) {
        Optional<Book> byId = bookRepository.findById(id);
        return byId.orElse(null);
    }

    public List<Book> searchByTitle(String query) {
        return bookRepository.findByTitleStartingWith(query);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book book = bookRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setPerson(book.getPerson());
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Person getBookOwner(int id) {

        return bookRepository.findById(id).map(Book::getPerson).orElse(null);
    }

    @Transactional
    public void release(int id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setPerson(null);
            book.setTakeAt(null);
        });
    }

    @Transactional()
    public void assign(int id, Person person) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setPerson(person);
            book.setTakeAt(new Date());
        });
    }

    public List<Book> getBooksByPerson(Person person) {
        return bookRepository.findByPerson(person);
    }

    public void test() {
        System.out.println("Тестируем на баг!!!!!");
    }
}
