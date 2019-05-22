package com.comment.repository;

import com.comment.domain.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface CollectionJpaRepository extends JpaSpecificationExecutor<Collection>,JpaRepository<Collection,Long> {
        List<Collection>findAllByBussinessidAndTypeAndUserid(Long bussinessId,int type,Long userid);
        List<Collection>findAllByBussinessidAndTypeAndEvaluateidAndUserid(Long bussinessId,int type,Long evaluateid,Long userid);
}