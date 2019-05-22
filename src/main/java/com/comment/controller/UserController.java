package com.comment.controller;

import com.comment.core.ResponseBean;
import com.comment.core.UnicomResponseEnums;
import com.comment.domain.User;
import com.comment.dtos.resetPwdDto;
import com.comment.repository.UserJpaRepository;
import com.comment.utils.MyDES;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
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
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserJpaRepository userJpaRepository;

    /**
     * 跳转到用户管理页面
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("user/index");
        return modelAndView;
    }

    @GetMapping("/headIcon")
    public ModelAndView headIcon() {
        ModelAndView modelAndView = new ModelAndView("user/headIcon");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("/user/login");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("/user/register");
        return modelAndView;
    }

    /**
     * 跳转到编辑用户信息页面
     * @return
     */
    @GetMapping("/form")
    public ModelAndView form() {
        ModelAndView modelAndView = new ModelAndView("/user/form");
        return modelAndView;
    }

    /**
     * 到达修改密码页面/user/changePwd
     * @return
     */
    @GetMapping("/changePwd")
    public ModelAndView changePwd() {
        ModelAndView modelAndView = new ModelAndView("/user/changePwd");
        return modelAndView;
    }

    /**
     * 到达user/profile个人资料页面
     * @return
     */
    @GetMapping("/profile")
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView("/user/profile");
        return modelAndView;
    }

    /**
     * 到达/user/setProfile修改个人信息页面
     * @return
     */
    @GetMapping("/setProfile")
    public ModelAndView setProfile() {
        ModelAndView modelAndView = new ModelAndView("/user/setProfile");
        return modelAndView;
    }

    /**
     * 重置用户密码
     * @param user
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @PostMapping("/resetpwd")
    public ResponseBean ResetPassword(@RequestBody User user) throws InvocationTargetException, IllegalAccessException {
        List<User> users = userJpaRepository.findAllById(user.getId());
        User ss = users.get(0);
        ss.setPassword(MyDES.encryptBasedDes("1234" + ss.getName())); //对密码进行加密
        userJpaRepository.save(ss);
        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
    }

    /**
     * 用户登陆，将用户信息存入session,session的信息使用Redis存储
     * @param user
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ResponseBean<UnicomResponseEnums> Login(@RequestBody User user, HttpSession session) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword(), true); //把用户名和密码封装成UserPasswordToken对象
            org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();      //获取当前subject ，即当前“用户”
            subject.login(token);   //执行登陆
            return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
        } catch (Exception ex) {
            return new ResponseBean(false, "-1", ex.getMessage());
        }
    }

    /**
     * 退出登陆之后到达登陆页面首页
     * @return
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView logout() {
        try {
            SecurityUtils.getSubject().logout();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return new ModelAndView("redirect:/user/login");
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    public ResponseBean<UnicomResponseEnums> Register(@RequestBody User user) {
        List<User> users = userJpaRepository.findByName(user.getName());
        if (users != null && users.size() > 0) {
            return new ResponseBean(false, UnicomResponseEnums.REPEAT_LOGINNAME);  //显示用户名已存在
        }

        user.setPassword(MyDES.encryptBasedDes(user.getPassword() + user.getName())); //对密码进行加密
        user.setGroupId(1);
        userJpaRepository.save(user);

        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
    }


    /**
     * 管理员编辑用户信息并提交保存
     * @param user
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @RequestMapping(value = "/edit")
    public ResponseBean<UnicomResponseEnums> editUser(@RequestBody User user) throws InvocationTargetException, IllegalAccessException {
        List<User> users = userJpaRepository.findByName(user.getName());
        if (user.getId() == 0) {
            if (users != null && users.size() > 0) {
                return new ResponseBean(false, UnicomResponseEnums.REPEAT_LOGINNAME);
            }
        } else {
            if (users != null) {
                boolean isAny = users.stream().anyMatch(r -> r.getId() != user.getId());
                if (isAny) {
                    return new ResponseBean(false, UnicomResponseEnums.REPEAT_LOGINNAME);
                }
            }
        }
        if (user.getId() != 0) {
            user.setPassword(users.get(0).getPassword());
            user.setImg(users.get(0).getImg());
        } else {
            user.setPassword(MyDES.encryptBasedDes("1234" + user.getName()));
        }
        userJpaRepository.save(user);
        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
    }

    /**
     * 删除指定Id的用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public ResponseBean<UnicomResponseEnums> deleteUser(@PathVariable long id) {
        userJpaRepository.deleteById(id);
        return new ResponseBean(true, UnicomResponseEnums.SUCCESS_OPTION);
    }

    /**
     * 获取所有用户信息
     * @param pageable
     * @param search
     * @return
     */
    @RequestMapping(value = "/")
    public Page<User> getUsers(Pageable pageable, String search) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pp = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), sort);
        Page<User> users = userJpaRepository.findAll(new Specification<User>() {             //java允许传递匿名函数，实现查询过滤，该参数就是一个类，类中重载某个方法
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (search != "") {
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + search + "%"));  //where name like '%search字段 %'
                                                                                                                     //lambda表达式，本质上是LIKE % %
                }
                return predicate;
            }
        }, pp);

        users.forEach(u -> {
            u.setPassword("");
        });
        return users;
    }

    /**
     * 返回指定Id用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}")
    public User getUser(@PathVariable long id) {
        if (id == 0) {
            return new User(id);
        }
        User user = userJpaRepository.findById(id).get();
        user.setPassword("");
        return user;
    }

    /**
     * 获取当前登陆的用户信息并返回
     * @return
     */
    @RequestMapping(value = "/getUserInfo")
    public User getUserInfo() {
        Object oo = SecurityUtils.getSubject().getPrincipal(); //获取登陆的用户信息，根据用户groupId可以动态显示左侧目录
        User uu = (User) oo;
        User user = userJpaRepository.findById(uu.getId()).get();
        user.setPassword("");
        return user; //返回登陆的用户信息
    }

    /**
     * 修改当前用户的密码
     * @param resetPwdUserDto
     * @return
     */
    @RequestMapping(value = "/ResetPwdByCurrentUser")
    public ResponseBean<UnicomResponseEnums> ResetPwdByCurrentUser(@RequestBody resetPwdDto resetPwdUserDto) {
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();
        List<User> currentUser = userJpaRepository.findByName(sessionUser.getName());
        User ccUser = currentUser.size() > 0 ? currentUser.get(0) : null;
        if (ccUser == null) {
            return ResponseBean.error();
        }
        if (!resetPwdUserDto.getNewPwd().equals(resetPwdUserDto.getNewPwd2())) {
            return ResponseBean.error("二次密码不一致!");
        }
        String oldPwdDES = MyDES.encryptBasedDes(resetPwdUserDto.getOldPwd() + ccUser.getName());//对密码进行加密
        if (!ccUser.getPassword().equals(oldPwdDES)) {
            return ResponseBean.error("旧密码不正确!");
        }
        String pawDES = MyDES.encryptBasedDes(resetPwdUserDto.getNewPwd() + ccUser.getName());
        ccUser.setPassword(pawDES);
        userJpaRepository.save(ccUser);
        return ResponseBean.success();
    }

    /**
     *修改当前用户的头像
     * @param user
     * @return
     */
    @RequestMapping(value = "/changePicture")
    public ResponseBean<UnicomResponseEnums> changePicture(@RequestBody User user) {
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();
        List<User> uses = userJpaRepository.findAllById(sessionUser.getId());
        User ccUser = uses.get(0);
        ccUser.setImg(user.getImg());
        userJpaRepository.save(ccUser);
        return ResponseBean.success();
    }
}
