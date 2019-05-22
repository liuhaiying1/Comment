package com.comment.controller;

import com.comment.core.ResponseBean;
import com.comment.core.UnicomResponseEnums;
import com.comment.domain.Business;
import com.comment.domain.Type;
import com.comment.domain.User;
import com.comment.repository.BusinessJpaRepository;
import com.comment.repository.TypeJpaRepository;
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
@RequestMapping(value = "/type")
public class TypeController {

    @Autowired
    private TypeJpaRepository typeJpaRepository;

    /**
     * 超级管理员类别管理页面
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView=new ModelAndView("/type/index");
        return modelAndView;
    }

    /**
     * 超级管理员类别管理中的编辑页面
     * @return
     */
    @GetMapping("/form")
    public ModelAndView form(){
        ModelAndView modelAndView=new ModelAndView("/type/form");
        return modelAndView;
    }

    /**
     *type/index.html中超级管理员进行类别管理编辑类别名称时调用该方法
     * @param b
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "/edit")
    public ResponseBean<UnicomResponseEnums> edit(@RequestBody Type b) throws InvocationTargetException, IllegalAccessException {
        typeJpaRepository.save(b);
        return new ResponseBean(true,UnicomResponseEnums.SUCCESS_OPTION);
    }

    /**
     * type/index.html中超级管理员进行类别管理删除类别名称时调用该方法
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ResponseBean<UnicomResponseEnums> delete(@PathVariable long id)
    {
        typeJpaRepository.deleteById(id);
        return new ResponseBean(true,UnicomResponseEnums.SUCCESS_OPTION);
    }

    /**
     *  返回所有类别
     * @param pageable
     * @param search   类别名，若没有填写类别名，则显示所有类别
     * @return
     */
    @RequestMapping(value = "/")
    public Page<Type> gets(Pageable pageable, String search)
    {
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pp =  PageRequest.of(pageable.getPageNumber()-1,pageable.getPageSize(),sort); //Pageable 为Spring Data库中定义的一个接口
        Page<Type> bs=typeJpaRepository.findAll(new Specification<Type>() {
            @Override
            public Predicate toPredicate(Root<Type> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate=criteriaBuilder.conjunction();
                if(search!=""){
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%"+search.trim()+"%"));  //模糊查询
                }
                return predicate;
            }
        },pp);

        return  bs;
    }

    @RequestMapping(value = "/{id}")
    public Type get(@PathVariable Long id)
    {

        if(id==0){
            return new Type(id);
        }
        Type b = typeJpaRepository.findById(id).get();

        return b;
    }
}
