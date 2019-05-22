package com.comment.controller;

import com.comment.core.ResponseBean;
import com.comment.core.UnicomResponseEnums;
import com.comment.domain.Business;
import com.comment.domain.User;
import com.comment.repository.BusinessJpaRepository;
import com.comment.repository.UserJpaRepository;
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
@RequestMapping(value = "/bussiness")
public class BussinessController {

    @Autowired
    private BusinessJpaRepository businessJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserRecommendService userRecommendService;


    /**
     * 跳转到超级管理员商家管理页面
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/bussiness/index");
        return modelAndView;
    }


    /**
     * 跳转到 商家登陆之后商家信息管理页面
     * @return
     */
    @GetMapping("/editform")
    public ModelAndView editform() {
        ModelAndView modelAndView = new ModelAndView("/bussiness/editform");
        return modelAndView;
    }

    /**
     * user/profile.html中普通用户要认证商家后调用该方法跳转页面
     * 管理员编辑商家信息
     * @return
     */
    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView("/bussiness/form");
        return modelAndView;
    }

    /**
     *到达 超级管理员进行“商家管理”时，设置商家状态页面
     * @return
     */
    @GetMapping("/audit")
    public ModelAndView audit() {
        ModelAndView modelAndView = new ModelAndView("/bussiness/audit");
        return modelAndView;
    }

    /**
     * food/detail中点击“查看地图”，跳转到地图页面（需联网）
     * @return
     */
    @GetMapping("/map")
    public ModelAndView map() {
        ModelAndView modelAndView = new ModelAndView("/bussiness/map");
        return modelAndView;
    }



    @RequestMapping(value = "/auditStauts")
    public ResponseBean<UnicomResponseEnums> auditStauts(@RequestBody Business b) {
        List<Business> bbbss = businessJpaRepository.findAllById(b.getId());
        Business bb = bbbss.get(0);
        bb.setStatus(b.getStatus());
        businessJpaRepository.save(bb);

        if (b.getStatus() == 2) {
            List<User> us = userJpaRepository.findAllById(b.getUserid());
            User u = us.get(0);
            if (u.getGroupId() != 3) {
                u.setGroupId(2);
                userJpaRepository.save(u);
            }
        } else {
            List<User> us = userJpaRepository.findAllById(b.getUserid());
            User u = us.get(0);
            if (u.getGroupId() != 3) {
                u.setGroupId(1);
                userJpaRepository.save(u);
            }
        }
        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
    }



    @RequestMapping(value = "/edit")
    public ResponseBean<UnicomResponseEnums> edit(@RequestBody Business b) throws InvocationTargetException, IllegalAccessException {
        Object oo = SecurityUtils.getSubject().getPrincipal();   //获取对象
        User uu = (User) oo;
        List<Business> bs = businessJpaRepository.findByTitle(b.getTitle());
        if (b.getId() == 0) {
            if (bs != null && bs.size() > 0) {
                return new ResponseBean(false, UnicomResponseEnums.REPEAT_BUSSINESSNAME);   //已存在该店名
            }
            List<Business> userBussiness = businessJpaRepository.findAllByUserid(uu.getId());

            if (userBussiness != null && userBussiness.size() > 0) {
                if (userBussiness.get(0).getStatus() == 3) {
                    return ResponseBean.error("该商家的认证已被禁用");
                }
                return ResponseBean.error("该账号已认证过商家!");
            }

            b.setUserid(uu.getId());
            b.setName(uu.getName());
            b.setStatus(1);
        } else {
            if (bs != null) {
                boolean isAny = bs.stream().anyMatch(r -> !r.getId().equals(b.getId()));  //模糊查询
                if (isAny) {
                    return new ResponseBean(false, UnicomResponseEnums.REPEAT_BUSSINESSNAME);   //已存在该店名
                }
            }
            Business bbb = businessJpaRepository.findAllById(b.getId()).get(0);

            b.setUserid(bbb.getUserid());
            b.setName(bbb.getName());
        }

        businessJpaRepository.save(b);
        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);   //操作成功
    }

    @RequestMapping(value = "/delete/{id}")
    public ResponseBean<UnicomResponseEnums> delete(@PathVariable long id) {
        businessJpaRepository.deleteById(id);
        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);   //操作成功
    }


    /**
     *  商家管理，获得所有商家信息
     * @param pageable
     * @param search
     * @return
     */
    @RequestMapping(value = "/")
    public Page<Business> getBussiness(Pageable pageable, String search) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pp = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);
        Page<Business> bs = businessJpaRepository.findAll(new Specification<Business>() {
            @Override
            public Predicate toPredicate(Root<Business> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (search != "") {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("title"), "%" + search.trim() + "%"));   //添加筛选条件，模糊查询
                }
                return predicate;
            }
        }, pp);
        return bs;
    }

    /**
     * food/list美食点评页面，通过传参，进行商家筛选并显示给前台
     * @param pageable
     * @param search
     * @param city
     * @param address
     * @param orderby
     * @param direction
     * @return
     */
    @RequestMapping(value = "/passaudit")
    public Page<Business> getBussinesspassaudit(Pageable pageable, String search, String city, String address, String orderby, Sort.Direction direction) {
        Sort sort = null;
        if (orderby == "" || orderby == null || direction == null) {
            sort = new Sort(Sort.Direction.DESC, "id");     //默认降序排列
        } else {
            sort = new Sort(direction, orderby);
        }
        Pageable pp = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);   //SpringBoot中的简单分页查询
        Page<Business> bs = businessJpaRepository.findAll(new Specification<Business>() {
           //Predicate是一个函数式接口，可以被应用于lambda表达式和方法引用。
            @Override
            public Predicate toPredicate(Root<Business> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {   //java泛型参数
                Predicate predicate = criteriaBuilder.conjunction();
                if (search != "") {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("title"), "%" + search.trim() + "%"));
                }
                if (city != "") {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("city"), "%" + city.trim() + "%"));
                }
                if (address != "") {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("address"), "%" + address.trim() + "%"));
                }
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("status"), 2));
                return predicate;
            }
        }, pp);
        return bs;
    }

    /**
     * plugins/layui-extend/bodyTab.js中第114行调用该方法
     * business/map.html通过该方法获得商家的信息，获取商家的地点，调用第三方接口在地图中显示出来
     * @param id
     * @return  一条商家信息
     */
    @RequestMapping(value = "/{id}")
    public Business get(@PathVariable long id) {

        if (id == 0) {
            Object oo = SecurityUtils.getSubject().getPrincipal();
            User uu = (User) oo;
            List<Business> bss = businessJpaRepository.findAllByUserid(uu.getId());
            if (bss.size() > 0) {
                return bss.get(0);
            } else {
                return new Business(id);
            }
        }
        Business b = businessJpaRepository.findById(id).get();
        b.setPre(b.getPre() + 1);  //商家浏览量加1
        businessJpaRepository.save(b);

        userRecommendService.correlations(b.getId(),1);

        return b;
    }
}
