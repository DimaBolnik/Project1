package ru.bolnik.web.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.bolnik.web.models.Person;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PersonDAO {

    private final EntityManager em;

    @Autowired
    public PersonDAO(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void testNPlus1() {
        Session session = em.unwrap(Session.class);

        // N+1 запрос
//        List<Person> persons = session.createQuery("from Person", Person.class).list();
//
//        for (Person person : persons) {
//            System.out.println(person.getFullName() + ", " + person.getBooks());
//        }
        //--------
        Set<Person> persons = new HashSet<Person>(session.createQuery("from Person p left join fetch p.books").getResultList());
        for (Person person : persons) {
            System.out.println(person.getFullName() + ", " + person.getBooks());
        }
    }

}
