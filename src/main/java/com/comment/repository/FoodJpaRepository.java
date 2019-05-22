package com.comment.repository;

import com.comment.domain.Food;
import com.comment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FoodJpaRepository extends JpaSpecificationExecutor<Food>,JpaRepository<Food,Long> {
}