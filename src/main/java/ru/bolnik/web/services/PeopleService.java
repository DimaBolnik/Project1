package ru.bolnik.web.services;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bolnik.web.models.Book;
import ru.bolnik.web.models.Mood;
import ru.bolnik.web.models.Person;
import ru.bolnik.web.repositories.BookRepository;
import ru.bolnik.web.repositories.PeopleRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> byId = peopleRepository.findById(id);
        return byId.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        person.setCreatedAt(new Date());
        person.setMood(Mood.ANGRY);
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id,Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public Optional<Person> getPersonByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    public List<Book> getBooksByPersonId(int id) {
//        return peopleRepository.findById(id)
//                .map(Person::getBooks)
//                .orElse(Collections.emptyList());
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());

            //проверка просроченности книг
            person.get().getBooks().forEach(book -> {
                long diffInMillie = Math.abs(book.getTakeAt().getTime() - new Date().getTime());
                if (diffInMillie > 864000000) {
                    book.setExpires(true);
                }
            });
            return person.get().getBooks();
        }else {
            return Collections.emptyList();
        }
    }

    public void test() {
        System.out.println("Тестируем на баг!!!!!");
    }
}
