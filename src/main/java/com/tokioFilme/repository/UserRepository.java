package com.tokioFilme.repository;

import com.tokioFilme.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Set<User> findByActive(boolean active);

  void deleteByUsername(String username);

}
