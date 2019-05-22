package com.comment.repository;

import com.comment.domain.User;
import com.comment.domain.UserRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRecommendJpaRepository extends JpaSpecificationExecutor<UserRecommend>,JpaRepository<UserRecommend,Long> {
    List<UserRecommend>findAllByBussinessidAndUserid(Long bussinessId,Long userid);
}