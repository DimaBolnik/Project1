package ru.bolnik.web.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Название книги не должно быть пустным")
    @Size(min = 2, max = 100, message = "Название книги должно быть в пределах от 2 до 100 символов")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Автор не должно быть пустным")
    @Size(min = 2, max = 100, message = "Имя автора должно быть в пределах от 2 до 100 символов")
    @Column(name = "author")
    private String author;


    @Min(value = 1500, message = "Год должен быть больше 1500г.")
    @Column(name = "year")
    private int year;

    @Column(name = "take_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takeAt;

    @Transient
    private boolean expires;

    @ManyToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void deleteUser() {
        this.person = null;
    }

    public Date getTakeAt() {
        return takeAt;
    }

    public void setTakeAt(Date takeAt) {
        this.takeAt = takeAt;
    }

    public boolean isExpires() {
        return expires;
    }

    public void setExpires(boolean expires) {
        this.expires = expires;
    }
}
