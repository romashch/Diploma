package com.shchelokov.diploma.repos;

import com.shchelokov.diploma.entities.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
