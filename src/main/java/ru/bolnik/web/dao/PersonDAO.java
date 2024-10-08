package ru.bolnik.web.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.bolnik.web.models.Book;
import ru.bolnik.web.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("select * from person where id=?", new Object[]{id},
               new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into Person(full_name, birth_year) values (?, ?)", person.getFullName(),
                person.getBirthYear());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("update Person set full_name=?, birth_year=? where id=?", updatedPerson.getFullName(),
                updatedPerson.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from Person where id=?", id);
    }

    //для валидации уникальности фио
    public Optional<Person> getPersonByFullName(String fullName) {
        return jdbcTemplate.query("select * from person where full_name=?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("select * from book where person_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
