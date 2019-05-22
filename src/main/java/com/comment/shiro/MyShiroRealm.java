package com.comment.shiro;


import com.comment.domain.User;
import com.comment.repository.UserJpaRepository;
import com.comment.utils.MyDES;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //用户登录次数计数  redisKey 前缀
    private String SHIRO_LOGIN_COUNT = "shiro_login_count_";

    //用户登录是否被锁定    一小时 redisKey 前缀
    private String SHIRO_IS_LOCK = "shiro_is_lock_";
    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     * @param
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String name = token.getUsername();
        String password = String.valueOf(token.getPassword());
        //访问一次，计数一次
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue(); //操作字符串
        opsForValue.increment(SHIRO_LOGIN_COUNT+name, 1);
        //计数大于5时，设置用户被锁定一小时
        if(Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT+name))>=10){
            opsForValue.set(SHIRO_IS_LOCK+name, "LOCK");
            stringRedisTemplate.expire(SHIRO_IS_LOCK+name, 1, TimeUnit.HOURS);
        }
        if ("LOCK".equals(opsForValue.get(SHIRO_IS_LOCK+name))){
            throw new DisabledAccountException("由于密码输入错误次数大于10次，帐号已经禁止登录！");
        }
        String paw = password+name;
        String pawDES = MyDES.encryptBasedDes(paw); //密码加密
        User user = userJpaRepository.findByNameAndPassword(name,pawDES);
        if (null == user) {
            throw new AccountException("帐号或密码不正确！");
        }else{
            userJpaRepository.save(user);
            //清空登录计数
            opsForValue.set(SHIRO_LOGIN_COUNT+name, "0");
        }
        logger.info("身份认证成功，登录用户："+user.getId());
        User saveUser =new User();
        saveUser.setId(user.getId());
        saveUser.setChName(user.getChName());
        saveUser.setName(user.getName());
        saveUser.setGroupId(user.getGroupId());
        return new SimpleAuthenticationInfo(saveUser, password, getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        long userId = user.getId();
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();

        Set<String> roleSet = new HashSet<String>();
        roleSet.add("100002");
        info.setRoles(roleSet);
        Set<String> permissionSet = new HashSet<String>();
        permissionSet.add("权限删除");
        info.setStringPermissions(permissionSet);
        return info;
    }
}
