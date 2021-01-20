package com.mobile.technologies.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends Serializable, ID> {

  Optional<T> findById(ID id);

  List<T> findAll();

  void save(T t);

  boolean existById(ID id);

}
