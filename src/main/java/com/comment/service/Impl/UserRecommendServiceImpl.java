package com.comment.service.Impl;

import com.comment.controller.FileController;
import com.comment.domain.User;
import com.comment.domain.UserRecommend;
import com.comment.repository.UserRecommendJpaRepository;
import com.comment.service.UserRecommendService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

@Service
public class UserRecommendServiceImpl implements UserRecommendService {
    @Autowired
    private UserRecommendJpaRepository userRecommendJpaRepository;
    @Value("${file.save.path}")
    private String filePath;
    private static final Logger logger = LoggerFactory.getLogger(UserRecommendServiceImpl.class);
    @Override
    public void correlations(Long busssinessId, int value) {
        User sessionUser = (User) SecurityUtils.getSubject().getPrincipal();   //获取当前用户信息

        Long userId = sessionUser.getId();

        List<UserRecommend> userRecommends = userRecommendJpaRepository.findAllByBussinessidAndUserid(busssinessId, userId);

        if (userRecommends.size() == 0) {
            UserRecommend ur = new UserRecommend(value, userId, busssinessId);
            userRecommendJpaRepository.save(ur);
        } else {
            UserRecommend uur = userRecommends.get(0);
            uur.setValue(uur.getValue() + value);     //加权
            userRecommendJpaRepository.save(uur);
        }

        try {
            write();
        }catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }

    public void write() throws Exception {
        File f = new File(filePath+"/recommend/ratings.txt");//stu.txt创建在和src同级目录下的“data”文件夹中（和src目录并列）
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        f.deleteOnExit();
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        OutputStreamWriter dos = new OutputStreamWriter(fos);

        List<UserRecommend> userRecommends = userRecommendJpaRepository.findAll();

        for (int i = 0; i < userRecommends.size(); i++) {
            UserRecommend ur = userRecommends.get(i);
            String ss = ur.getUserId() + " " + ur.getBussinessId() + " " + ur.getValue() + " " + String.valueOf(ur.getCreateTime().getTime())+"\n";
            dos.write(ss);
        }
        dos.flush();
        dos.close();
    }
}
