package com.tokioFilme.repository;

import com.tokioFilme.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {

  Role findByNameIgnoreCase(String name);

  List<Role> findAll();
}
