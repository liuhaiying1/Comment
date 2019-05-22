package com.comment.controller;

import com.comment.core.ResponseBean;
import com.comment.core.UnicomResponseEnums;
import com.comment.domain.Business;
import com.comment.domain.Collection;
import com.comment.domain.Evaluate;
import com.comment.domain.User;
import com.comment.repository.BusinessJpaRepository;
import com.comment.repository.CollectionJpaRepository;
import com.comment.repository.EvaluateJpaRepository;
import com.comment.service.EvaluateService;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/evaluate")
public class EvaluateController {
    @Autowired
    private EvaluateJpaRepository evaluateJpaRepository;
    @Autowired
    private BusinessJpaRepository businessJpaRepository;

    @Autowired
    private EvaluateService evaluateService;

    @Autowired
    private CollectionJpaRepository collectionJpaRepository;

    @Autowired
    private UserRecommendService userRecommendService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/evaluate/index");
        return modelAndView;
    }

    /**
     * 用户查看评论详情
     * @param status
     * @return
     */
    @GetMapping("/form/{status}")
    public ModelAndView form(@PathVariable int status) {
        ModelAndView modelAndView = new ModelAndView("/evaluate/form");
        modelAndView.addObject("status", status);
        return modelAndView;
    }

    /**
     * 跳转到商家登陆之后的商家回复评论页面
     * @return
     */
    @GetMapping("/bussiness")
    public ModelAndView bussiness() {
        ModelAndView modelAndView = new ModelAndView("/evaluate/bussiness");
        return modelAndView;
    }

    /**
     * list/detail（美食详情页面）中点击“写评论”跳转到写评论页面
     * @return
     */
    @GetMapping("/editInfo")
    public ModelAndView editInfo() {
        ModelAndView modelAndView = new ModelAndView("/evaluate/editInfo");
        return modelAndView;
    }

    /**
     * 跳转到管理员的评论管理页面
     * @return
     */
    @GetMapping("/admin")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView("/evaluate/admin");
        return modelAndView;
    }

    /**
     * 编辑评论
     * @param b
     * @return
     */
    @RequestMapping(value = "/edit")
    public ResponseBean<UnicomResponseEnums> edit(@RequestBody Evaluate b) {
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();
        b.setUserid(sessionUser.getId());
        b.setUsername(sessionUser.getName());
        b.setStart(0);
        b.setStatus(1);
        evaluateJpaRepository.save(b);
        evaluateService.Convergence(b);
        userRecommendService.correlations(b.getBussinessid(),6);
        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
    }

    /**
     * 点赞，value值加1
     * @param b
     * @return
     */
    @RequestMapping(value = "/star")
    public ResponseBean<UnicomResponseEnums> star(@RequestBody Evaluate b) {
        List<Evaluate> evs = evaluateJpaRepository.findAllById(b.getId());  //获取当前的一条评论
        Evaluate bb = evs.get(0);
        int ss = bb.getStart() + 1; //用户点赞了，赞数就加1
        bb.setStart(ss);
        evaluateJpaRepository.save(bb);
        userRecommendService.correlations(bb.getBussinessid(),3);
        return ResponseBean.success(Integer.toString(ss));
    }

    /**
     * 跳转到审核
     * @return
     */
    @GetMapping("/audit")
    public ModelAndView audit() {
        ModelAndView modelAndView = new ModelAndView("/evaluate/audit");
        return modelAndView;
    }


    @RequestMapping(value = "/back")
    public ResponseBean<UnicomResponseEnums> back(@RequestBody Evaluate b) {
        List<Evaluate> evs = evaluateJpaRepository.findAllById(b.getId());
        Evaluate bb = evs.get(0);
        bb.setResponse(b.getResponse());
        evaluateJpaRepository.save(bb);
        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
    }

    /**
     * 管理员审核评论
     * @param b
     * @return
     */
    @RequestMapping(value = "/audit")
    public ResponseBean<UnicomResponseEnums> audit(@RequestBody Evaluate b) {
        List<Evaluate> evs = evaluateJpaRepository.findAllById(b.getId());
        Evaluate bb = evs.get(0);
        bb.setStatus(b.getStatus());
        evaluateJpaRepository.save(bb);
        evaluateService.Convergence(b);     //每次给商家点赞，评论之后，都要进行求平均值，看商家表
        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
    }

    /**
     *用户删除指定的评论记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ResponseBean<UnicomResponseEnums> delete(@PathVariable long id) {
        Evaluate evaluate = evaluateJpaRepository.findAllById(id).get(0);
        evaluateJpaRepository.deleteById(id);
        evaluateService.Convergence(evaluate);   // 每次给商家点赞，评论之后，都要进行求平均值
        userRecommendService.correlations(evaluate.getBussinessid(),-6);   //重新计算相关性，即重新加权计算
        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
    }


    /**
     * 管理员获取所有评论信息
     * @param pageable
     * @param search
     * @return
     */
    @RequestMapping(value = "/getall")
    public Page<Evaluate> getall(Pageable pageable, String search) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pp = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);
        Page<Evaluate> bs = evaluateJpaRepository.findAll(new Specification<Evaluate>() {             //匿名类
            @Override
            public Predicate toPredicate(Root<Evaluate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (search != "") {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("message"), "%" + search.trim() + "%"));
                }
                return predicate;
            }
        }, pp);
        return bs;
    }

    @RequestMapping(value = "/getByCurrentUser")
    public Page<Evaluate> getByCurrentUser(Pageable pageable, String search) {
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pp = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);
        Page<Evaluate> bs = evaluateJpaRepository.findAll(new Specification<Evaluate>() {
            @Override
            public Predicate toPredicate(Root<Evaluate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (search != "") {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("message"), "%" + search.trim() + "%"));
                }
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("userid"), sessionUser.getId()));
                return predicate;
            }
        }, pp);

        return bs;
    }

    @RequestMapping(value = "/")
    public Page<Evaluate> gets(Pageable pageable, String search) {
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();
        List<Business> businesses = businessJpaRepository.findAllByUserid(sessionUser.getId());
        if (businesses.size() == 0) {
            return null;
        }
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pp = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);
        Page<Evaluate> bs = evaluateJpaRepository.findAll(new Specification<Evaluate>() {
            @Override
            public Predicate toPredicate(Root<Evaluate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (search != "") {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("message"), "%" + search.trim() + "%"));
                }
                Long bussid = businesses.get(0).getId();
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("bussinessid"), bussid));
                return predicate;
            }
        }, pp);

        return bs;
    }

    /**
     * 返回用户查看的评论的详细信息
     * @param id
     * @return  一条评论
     */
    @RequestMapping(value = "/{id}")
    public Evaluate get(@PathVariable Long id) {
        if (id == 0) {
            return new Evaluate(id);
        }
        Evaluate b = evaluateJpaRepository.findById(id).get();
        return b;
    }

    /**
     *  获取商家评论信息
     * @param pageable
     * @param bussinessId
     * @param search
     * @return
     */
    @RequestMapping(value = "/getsByBussinessId/{bussinessId}")
    public Page<Evaluate> getByCurrentUser(Pageable pageable, @PathVariable Long bussinessId, String search) {

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pp = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);
        Page<Evaluate> bs = evaluateJpaRepository.findAll(new Specification<Evaluate>() {
            @Override
            public Predicate toPredicate(Root<Evaluate> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (search != "") {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("message"), "%" + search.trim() + "%"));
                }
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("bussinessid"), bussinessId));
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("status"), 1));
                return predicate;
            }
        }, pp);
        return bs;
    }

    /**
     *  美食点评页面动态加载商家的点评信息
     * @param bussinessId   商家Id
     * @return
     */
    @RequestMapping(value = "/getEvaluateByBussinessId/{bussinessId}")
    public List<Collection>getEvaluateByBussinessId(@PathVariable Long bussinessId){
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();
        List<Collection> cos = collectionJpaRepository.findAllByBussinessidAndTypeAndUserid(bussinessId, 2,sessionUser.getId());

        List<Collection> ccc = cos.stream().filter(r -> !r.getEvaluateid().equals(0)).collect(Collectors.toList());  // lambda表达式
        return  ccc;
    }
}
