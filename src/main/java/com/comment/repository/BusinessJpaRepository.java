package com.comment.repository;

import com.comment.domain.Business;
import com.comment.domain.Type;
import com.comment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BusinessJpaRepository extends JpaSpecificationExecutor<Business>,JpaRepository<Business,Long> {
    List<Business> findByTitle(String title);

    List<Business> findAllById(Long id);

    List<Business>findAllByUserid(Long userid);

   @Query(value = "select * from business where id in (:spIds)", nativeQuery = true)
    List<Business> getBusinessInIds(@Param("spIds") List<Long> spIds);

}