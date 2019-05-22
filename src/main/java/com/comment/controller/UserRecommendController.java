package com.comment.controller;

import com.comment.domain.Business;
import com.comment.domain.Food;
import com.comment.domain.User;
import com.comment.repository.BusinessJpaRepository;
import com.comment.repository.UserRecommendJpaRepository;
import com.comment.service.Impl.RecommendedServiceImpl;
import com.comment.service.RecommendedService;
import net.librec.recommender.item.RecommendedItem;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.expression.Ids;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/userRecommend")
public class UserRecommendController {

    @Autowired
    private UserRecommendJpaRepository userRecommendJpaRepository;


    @Autowired
    private RecommendedService recommendedService;
    @Autowired
    private BusinessJpaRepository businessJpaRepository;

    /**
     * 点击左侧“美食推荐”，返回给前台用户的推荐美食页面
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("userRecommend/index");
        return modelAndView;
    }

    /**
     * 根据用户推荐感兴趣的商品
     *
     * @param response
     */
    @RequestMapping(value = "/userBased")
    public List<RecommendedItem> userBasedRecommender(HttpServletResponse response) {
        List<RecommendedItem> recommendedItemList1 = null;
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = sessionUser.getId();
        try {
            recommendedItemList1 = recommendedService.getItemListFromText(String.valueOf(userId), null);  //在recommendService中实现具体推荐
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recommendedItemList1;
    }


    /**
     * 获取该用户的推荐列表，把推荐的商家列表作为返回值
     * @return
     */
    @RequestMapping(value = "/getBussiness")
    public List<Business> GetBussiness() {
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = sessionUser.getId();
        try {
            List<RecommendedItem> recommendedItemList1 = recommendedService.getItemListFromText(String.valueOf(userId), null); // 调用推荐算法
            List<Long> Ids = new ArrayList<Long>() {};
            for (int i = 0; i < recommendedItemList1.size(); i++) {
                Ids.add(Long.parseLong(recommendedItemList1.get(i).getItemId()));  //获取所有推荐的商家的Id
            }
            return businessJpaRepository.getBusinessInIds(Ids);  //根据商家Id获取对应商家，返回给前台
        } catch (Exception ex) {
            return new ArrayList<Business>();
        }
    }


}
