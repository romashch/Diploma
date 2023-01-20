package com.shchelokov.diploma.repos;

import com.shchelokov.diploma.entities.Description;
import org.springframework.data.repository.CrudRepository;

public interface DescRepo extends CrudRepository<Description, Long> {
}
