package com.comment.repository;

import com.comment.domain.Evaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface EvaluateJpaRepository extends JpaSpecificationExecutor<Evaluate>,JpaRepository<Evaluate,Long> {
        List<Evaluate>findAllById(Long id);
        List<Evaluate>findAllByBussinessid(Long bussinessId);
}