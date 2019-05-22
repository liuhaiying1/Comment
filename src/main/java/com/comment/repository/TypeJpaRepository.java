package com.comment.repository;

import com.comment.domain.Business;
import com.comment.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface TypeJpaRepository extends JpaSpecificationExecutor<Type>,JpaRepository<Type,Long> {
    List<Type> findAllById(Long id);
}