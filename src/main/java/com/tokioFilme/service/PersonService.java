package com.tokioFilme.service;

import com.tokioFilme.domain.Person;
import com.tokioFilme.domain.PersonTypeEnum;

import java.util.List;

public interface PersonService {

  Person addPerson(Person person);

  List<Person> getPeopleByType(PersonTypeEnum type);
}
