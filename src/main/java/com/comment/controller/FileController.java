package com.comment.controller;

import com.comment.core.ResponseBean;
import com.comment.core.UnicomResponseEnums;
import com.comment.domain.User;
import com.comment.repository.UserJpaRepository;
import com.comment.service.UserRecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/file")
public class FileController {
    @Autowired
    private UserJpaRepository userJpaRepository;
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Value("${file.save.path}")
    private String filePath;

    //测试文件上传
    @RequestMapping("/test")
    public ModelAndView test() {
        return new ModelAndView("/file/test");
    }

    //测试文件上传
    @RequestMapping("/test2")
    public ModelAndView test2() {
        return new ModelAndView("/file/test2");
    }

    //测试地图效果
    @RequestMapping("/map")
    public ModelAndView map() {
        return new ModelAndView("/file/map");
    }

    //文件上传相关代码
    @RequestMapping(value = "/upload")
    public ResponseBean<UnicomResponseEnums> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseBean.error("文件为空!");
        }
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + "/uploads/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseBean.success(fileName, "上传成功!");
    }

    /**
     * 根据请求下载指定文件
     * @param request
     * @param response
     * @param fileToken
     * @param newName
     * @return
     */
    @RequestMapping("/download")
    public ResponseBean<UnicomResponseEnums> downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "fileToken") String fileToken, @RequestParam(value = "newName") String newName) {
        if (fileToken != null) {
            String realPath = filePath + "/uploads";
            File file = new File(realPath, fileToken);
            if (file.exists()) {
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;fileName=" + newName);//
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            //   e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            //e.printStackTrace();
                        }
                    }
                }
            }
        }
        return ResponseBean.error("文件为空");
    }

    /**
     * 获取指定Id的用户头像
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping("/getCurrentProfile/{id}")
    public void getCurrentProfile(HttpServletRequest request, HttpServletResponse response, @PathVariable long id) {
        List<User> currentUser = userJpaRepository.findAllById(id);
        User ccUser = currentUser.size() > 0 ? currentUser.get(0) : null;
        String fileName = ccUser.getImg();
        if (fileName == null) {
            fileName = "avatar5.png";
        }
        this.downloadFile(request, response, fileName, fileName);
    }


    private int count = 0;

    @RequestMapping(value = "/write")
    public void write() throws Exception {

        File f = new File("E:\\code\\gitee\\Comment\\data\\movielens\\1.txt");//stu.txt创建在和src同级目录下的“data”文件夹中（和src目录并列）
        f.deleteOnExit();
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        OutputStreamWriter dos = new OutputStreamWriter(fos);
        this.count++;
        dos.write("Rose 80 80 80\n");

        dos.flush();
        dos.close();

    }

    @Autowired
    private UserRecommendService userRecommendService;


    @RequestMapping(value = "/testwrite")
    public void testwrite() throws Exception {
        userRecommendService.write();
    }

}
