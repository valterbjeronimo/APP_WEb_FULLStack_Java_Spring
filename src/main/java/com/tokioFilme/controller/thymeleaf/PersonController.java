package com.tokioFilme.controller.thymeleaf;

import com.tokioFilme.domain.Person;
import com.tokioFilme.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Slf4j
public class PersonController {

  private final PersonService service;

  public PersonController(PersonService service) {
    this.service = service;
  }

  @RequestMapping(path = "/person/add", method = RequestMethod.GET)
  public String addPerson(@RequestParam(name = "person", required = false) String message,
                          Model model) {
    model.addAttribute("person", new Person());
    return "new-person";
  }
  @RequestMapping(path = "/person/add", method = RequestMethod.POST)
  public String addPerson(@ModelAttribute @Valid Person person,
                          BindingResult result,
                          Model model,
                          Principal principal)
  {
    if (result.hasErrors()) {
      log.error("Creation of Person {} failed because: {}", person, result.getAllErrors().toArray());
      return "new-person";
    }
    else {
      log.info("User {} added Person {}", principal.getName(), person);
      service.addPerson(person);
      return "redirect:/person/add?person=created";
    }
  }
}
