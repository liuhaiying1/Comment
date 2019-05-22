package com.comment.repository;

import com.comment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserJpaRepository extends JpaSpecificationExecutor<User>,JpaRepository<User,Long> {
    User findByNameAndPassword(String name,String password);
    List<User> findByName(String name);
    List<User> findAllById(Long id);
}