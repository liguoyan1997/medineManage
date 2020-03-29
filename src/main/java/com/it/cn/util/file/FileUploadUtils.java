package com.it.cn.util.file;

import com.alibaba.fastjson.JSONArray;
import com.it.cn.config.UploadProperties;
import com.it.cn.entity.Upload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/upload")
public class FileUploadUtils {

    @Resource
    private UploadProperties uploadProperties;

    /**
     * 文档的上传
     */
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ResponseBody
    public Object file(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException, InterruptedException {
        Upload upload = new Upload();
        if (!file.isEmpty()){
            //得到文件的名称
            String fileName = file.getOriginalFilename();
            //文件的路径
            String pathname = "";
            //获得文件的类型  根据文件类型创建文件夹
            File directory = new File("");// 参数为空
            //获取上传的项目本地文件路劲
            String courseFile = directory.getCanonicalPath();
            String fileType = file.getContentType().split("/")[0];
            fileType = "image,audio,video".contains(fileType) ? fileType : "file";
            //获得完整的文件夹
            Date dt = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            String descDirNames = "/"+"userfiles" + "/" + fileType + "/" + df.format(dt);
            //在项目中存放文件
            File tmpfile = new File(courseFile+"/src/main/webapp/static/uploadFile/"+fileName);
            Thread.sleep(5000);
            file.transferTo(tmpfile.getAbsoluteFile());
            Thread.sleep(5000);
            upload.setFileType(fileType);
            upload.setFileName(fileName);
            upload.setCode(0);
            upload.setMsg("文件上传成功了");
            upload.setFilePath("/uploadFile/"+ fileName);
            upload.setSize(file.getSize());
            return JSONArray.toJSON(upload);
        }else {
            upload.setCode(1);
            upload.setMsg("文件出现问题，请检查文件后发送");
            return JSONArray.toJSON(upload);
        }
    }
}
