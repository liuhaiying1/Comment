package com.comment.service;

import net.librec.recommender.item.RecommendedItem;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RecommendedService {
    List<RecommendedItem> getItemListFromText(String userId, String itemId) throws Exception;
}
