package ru.bolnik.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bolnik.web.dao.PersonDAO;
import ru.bolnik.web.models.Person;
import ru.bolnik.web.services.BookService;
import ru.bolnik.web.services.PeopleService;
import ru.bolnik.web.util.PersonalValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    //
//    private final PersonDAO personDao;
    private final PeopleService peopleService;
    private final PersonalValidator personalValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, BookService bookService, PersonDAO personDAO, PersonalValidator personalValidator) {
//        this.personDao = personDao;
        this.peopleService = peopleService;
        this.personalValidator = personalValidator;
    }


    @GetMapping()
    public String index(Model model) {
        //получим всех людей из DAO
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        //получим одного человека по его id
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.getBooksByPersonId(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personalValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

}