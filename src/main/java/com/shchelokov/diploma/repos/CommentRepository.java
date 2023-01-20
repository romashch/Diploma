package com.shchelokov.diploma.repos;

import com.shchelokov.diploma.entities.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}
