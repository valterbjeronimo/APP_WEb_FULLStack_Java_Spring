package com.tokioFilme.repository;

import com.tokioFilme.domain.Person;
import com.tokioFilme.domain.PersonTypeEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

  List<Person> findByType(PersonTypeEnum type);
  List<Person> findByNameAndSurnameAllIgnoreCase(String name, String surname);
}
