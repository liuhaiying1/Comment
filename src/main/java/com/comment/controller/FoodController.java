package com.comment.controller;

import com.comment.core.ResponseBean;
import com.comment.core.UnicomResponseEnums;
import com.comment.domain.Business;
import com.comment.domain.Food;
import com.comment.domain.User;
import com.comment.repository.BusinessJpaRepository;
import com.comment.repository.FoodJpaRepository;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping(value = "/food")
public class FoodController {
    @Autowired
    private FoodJpaRepository foodJpaRepository;

    @Autowired
    private BusinessJpaRepository businessJpaRepository;

    /**
     * 商家登陆之后的菜肴管理页面
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/food/index");
        return modelAndView;
    }

    /**
     * 美食点评页面
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("/food/list");
        return modelAndView;
    }

    /**
     * 美食点评页面food/list 中点击商家就可以进入美食详情页面food/details
     * @return
     */
    @GetMapping("/details")
    public ModelAndView details() {
        ModelAndView modelAndView = new ModelAndView("/food/details");
        return modelAndView;
    }

    /**
     * 商家菜肴管理中的新增页面
     * @return
     */
    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView("/food/form");
        return modelAndView;
    }

    /**/
    @RequestMapping(value = "/edit")
    public ResponseBean<UnicomResponseEnums> edit(@RequestBody Food b) {
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();
        List<Business> businesses = businessJpaRepository.findAllByUserid(sessionUser.getId());
        if (businesses != null && businesses.size() > 0) {
            b.setBussinessid(businesses.get(0).getId());
            foodJpaRepository.save(b);
        } else {
            return ResponseBean.error("当前用户未验证商家信息！");
        }

        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
    }


    @RequestMapping(value = "/delete/{id}")
    public ResponseBean<UnicomResponseEnums> delete(@PathVariable long id) {
        foodJpaRepository.deleteById(id);
        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
    }

    @RequestMapping(value = "/")
    public Page<Food> gets(Pageable pageable, String search, int typeid) {
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();
        List<Business> businesses = businessJpaRepository.findAllByUserid(sessionUser.getId());
        if (businesses.size() == 0) {
            return null;
        }
        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        Pageable pp = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);
        Page<Food> bs = foodJpaRepository.findAll(new Specification<Food>() {
            @Override
            public Predicate toPredicate(Root<Food> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (search != "") {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("introduce"), "%" + search.trim() + "%"));
                }
                if (typeid != 0) {
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("typeid"), typeid));
                }
                Long bussid = businesses.get(0).getId();
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("bussinessid"), bussid));
                return predicate;
            }
        }, pp);

        return bs;
    }

    @RequestMapping(value = "/{id}")
    public Food get(@PathVariable Long id) {

        if (id == 0) {
            return new Food(id);
        }
        Food b = foodJpaRepository.findById(id).get();

        return b;
    }

    /**
     * 获取指定businessId的推荐菜单
     * @param pageable
     * @param bussinessid
     * @return
     */
    @RequestMapping(value = "/getRecommend")
    public Page<Food> getRecommend(Pageable pageable, int bussinessid) {
        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        Pageable pp = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);
        Page<Food> bs = foodJpaRepository.findAll(new Specification<Food>() {
            @Override
            public Predicate toPredicate(Root<Food> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("bussinessid"), bussinessid));
                return predicate;
            }
        }, pp);

        return bs;
    }
}
