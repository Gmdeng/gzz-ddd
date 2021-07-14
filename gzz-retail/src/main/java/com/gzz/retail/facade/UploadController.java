package com.gzz.retail.facade;

import com.gzz.core.response.HttpResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传
 *
 */
@RestController
@RequestMapping("/v1/files")
public class UploadController {
    @Value("${gzz.upload.path}")
    private String uploadPath; //保存目录

    /**
     * 上传文件
     * @return
     */
    @RequestMapping("/doUploadFile")
    public HttpResult doUploadFile(@RequestParam Map<String, String> map, @RequestParam("file") MultipartFile file){
        try {
            String projectCode = map.get("projectCode");
            //生成uuid
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //得到上传时的文件名
            String filename = file.getOriginalFilename();
            //获取最后一个.的位置
            int lastIndexOf = filename.lastIndexOf(".");
            //获取文件的后缀名 .jpg
            String suffix = System.currentTimeMillis() + filename.substring(lastIndexOf);
            //上传目录地址
            //1.1保存到项目指定目录
            //String uploadDir="C:\\Users\\21112\\Documents\\我的资源\\idea\\shop\\src\\main\\resources\\static\\upload";
            String uploadDir = uploadPath + "/" + projectCode + "/";
            //1.2 上传到相对路径 request.getSession().getServletContext().getRealPath("/")+"upload/";
            //1.2 此路径为tomcat下，可以输出看一看

            //如果目录不存在，自动创建文件夹
            File dir=new File(uploadDir);
            if(!dir.exists()){
                dir.mkdir();
            }

            //保存文件对象 加上uuid是为了防止文件重名
            File serverFile=new File(uploadDir+uuid+"_"+suffix);
            file.transferTo(serverFile);
        } catch (Exception e) {
            //打印错误堆栈信息
            e.printStackTrace();
            return HttpResult.fail();
        }
        return HttpResult.success();
    }
}
