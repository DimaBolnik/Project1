package ru.bolnik.web.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.bolnik.web.models.Book;
import ru.bolnik.web.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Person", Person.class).list();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        person.setFullName(updatedPerson.getFullName());
        person.setBirthYear(updatedPerson.getBirthYear());
        person.setEmail(updatedPerson.getEmail());
        session.saveOrUpdate(person);
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        session.remove(person);
    }

    //для валидации уникальности фио
    @Transactional(readOnly = true)
    public Optional<Person> getPersonByFullName(String fullName) {
        Session session = sessionFactory.getCurrentSession();
        Optional<Person> fullName1 = session.createQuery("from Person where fullName = :fullName", Person.class)
                .setParameter("fullName", fullName).uniqueResultOptional();
        return fullName1;
    }
//        return jdbcTemplate.query("select * from person where full_name=?", new Object[]{fullName},
//                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();


    @Transactional(readOnly = true)
    public List<Book> getBooksByPersonId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        return person.getBooks();
//        return jdbcTemplate.query("select * from book where person_id = ?", new Object[]{id},
//                new BeanPropertyRowMapper<>(Book.class));
    }
}
