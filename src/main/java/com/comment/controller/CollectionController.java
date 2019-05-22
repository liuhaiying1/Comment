package com.comment.controller;

import com.comment.core.ResponseBean;
import com.comment.core.UnicomResponseEnums;
import com.comment.domain.Collection;
import com.comment.domain.Evaluate;
import com.comment.domain.User;
import com.comment.repository.CollectionJpaRepository;
import com.comment.repository.EvaluateJpaRepository;
import com.comment.service.UserRecommendService;
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
@RequestMapping(value = "/collection")
public class CollectionController {
    @Autowired
    private CollectionJpaRepository collectionJpaRepository;

    @Autowired
    private EvaluateJpaRepository evaluateJpaRepository;
    @Autowired
    private UserRecommendService userRecommendService;

    /**
     * 点击我的全部点评收藏以及全部商家收藏
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/collection/index");
        return modelAndView;
    }


    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView("/collection/form");
        return modelAndView;
    }

    /**
     * food/detail页面中收藏商家
     * @param b
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "/edit")
    public ResponseBean<UnicomResponseEnums> edit(@RequestBody Collection b) throws InvocationTargetException, IllegalAccessException {
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();  //获得当前登陆用户信息
        Long userid = sessionUser.getId();   //获得用户Id
        if (b.getEvaluateid() == 0) {
            List<Collection> ccs = collectionJpaRepository.findAllByBussinessidAndTypeAndUserid(b.getBussinessid(), b.getType(), sessionUser.getId());
            if (ccs.size() > 0) {
                collectionJpaRepository.delete(ccs.get(0));
                userRecommendService.correlations(ccs.get(0).getBussinessid(),-10);  //重新设置用户与商家的相关性
                return ResponseBean.success("取消收藏成功");
            }
        } else {
            List<Collection> css = collectionJpaRepository.findAllByBussinessidAndTypeAndEvaluateidAndUserid(b.getBussinessid(), b.getType(), b.getEvaluateid(), sessionUser.getId());
            if (css.size() > 0) {
                collectionJpaRepository.delete(css.get(0));
                userRecommendService.correlations(css.get(0).getBussinessid(),-10);
                return ResponseBean.success("取消收藏成功");
            }
            List<Evaluate> evs = evaluateJpaRepository.findAllById(b.getEvaluateid());
            b.setMessage(evs.get(0).getMessage());
        }

        b.setUserid(userid);
        collectionJpaRepository.save(b);
        userRecommendService.correlations(b.getBussinessid(),10);
        return ResponseBean.success("收藏成功");
    }


    /**
     * 删除用户指定的收藏商家
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ResponseBean<UnicomResponseEnums> delete(@PathVariable long id) {
        collectionJpaRepository.deleteById(id);
        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
    }


    /**
     *  用户查询“商家收藏”时调用该方法，返回给前台分页显示的所有收藏的商家信息
     * @param pageable
     * @param search    search指前端传过来的商家名，实现按商家名查询商家收藏信息
     * @param type
     * @return
     */
    @RequestMapping(value = "/")
    public Page<Collection> gets(Pageable pageable, String search, int type) {    //pageable为spring data库中定义的接口，所有分页信息相关的类
        Sort sort = new Sort(Sort.Direction.DESC, "id");       //  默认id降序显示当前用户的所有商家收藏信息
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();
        Pageable pp = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);
        Page<Collection> bs = collectionJpaRepository.findAll(new Specification<Collection>() {               //java匿名函数
            @Override
            public Predicate toPredicate(Root<Collection> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {   //CriteriaQuery动态查询
                Predicate predicate = criteriaBuilder.conjunction();
                if (search != "") {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("bussinessName"), "%" + search.trim() + "%"));  //增加筛选条件，进行模糊查询
                }
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("type"), type));
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("userid"), sessionUser.getId()));
                return predicate;
            }
        }, pp);

        return bs;
    }

    @RequestMapping(value = "/{id}")
    public Collection get(@PathVariable Long id) {
        if (id == 0) {
            return new Collection(id);
        }
        Collection b = collectionJpaRepository.findById(id).get();
        return b;
    }

    /**
     * 通过商家Id获取商家信息
     * @param bussinessId
     * @return
     */
    @RequestMapping(value = "/getByBussinessId/{bussinessId}")
    public Collection getByBussinessId(@PathVariable Long bussinessId) {
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();
        List<Collection> bs = collectionJpaRepository.findAllByBussinessidAndTypeAndUserid(bussinessId, 1, sessionUser.getId());
        if (bs.size() > 0) {
            return bs.get(0);
        }
        return new Collection(0);
    }
}
