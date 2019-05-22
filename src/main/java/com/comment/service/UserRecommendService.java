package com.comment.service;

import com.comment.domain.Evaluate;
import org.springframework.stereotype.Service;

@Service
public interface UserRecommendService {
    void correlations(Long busssinessId, int value);
    void write() throws Exception;
}