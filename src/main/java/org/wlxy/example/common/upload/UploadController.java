package org.wlxy.example.common.upload;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wlxy.example.common.MyRsp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("upload")//注意路径前面在这个
@CrossOrigin//支持跨域的注解  一般在所有接口上面写
public class UploadController {


    @Value("${app.imgPath}")
    private String imgPath;

    @PostMapping("/addImage")
    @ResponseBody
    public Object addImage(@RequestParam(name = "image_data", required = false) MultipartFile file) {
        String fileName = file.getOriginalFilename();//获取文件的名字
        //文件上传
        if (!file.isEmpty()) {
            try {
                //图片命名
//                String newCompanyImageName = "newPIC";
                String newCompanyImagepath = imgPath+file.getOriginalFilename();
                File newFile = new File(newCompanyImagepath);
                if (!newFile.exists()) {
                    newFile.createNewFile();
                }
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(newFile));//缓冲输出流
                out.write(file.getBytes());
                out.flush();//刷新
                out.close();//关闭
            } catch (IOException e) {
                e.printStackTrace();
                return MyRsp.error().msg("上传失败");
            }
        }
        return MyRsp.success(fileName).msg("上传成功");
    }
}
