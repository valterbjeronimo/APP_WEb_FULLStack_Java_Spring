package com.tokioFilme.service.implementation;

import com.tokioFilme.domain.Person;
import com.tokioFilme.domain.PersonTypeEnum;
import com.tokioFilme.repository.PersonRepository;
import com.tokioFilme.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

  private final PersonRepository personRepo;

  public PersonServiceImpl(PersonRepository personRepo) {
    this.personRepo = personRepo;
  }

  @Override
  public Person addPerson(Person person) {
    log.info("Adding Person: {}", person);
    return personRepo.save(person);
  }

  @Override
  public List<Person> getPeopleByType(PersonTypeEnum type) {
    log.info("Fetching Person of PersonTypeEnum {}", type);
    return personRepo.findByType(type);
  }
}
