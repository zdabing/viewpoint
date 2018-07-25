package com.viewpoint.controller;

import com.viewpoint.utils.FtpUtil;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private FtpUtil ftpUtil;

    /**
     *
     * 功能描述: 图片上传返回地址
     *
     * @param file
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @ResponseBody
    @PostMapping("/image")
    public ResultVO upload(MultipartFile file) throws Exception{
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        Map<String, String> map = new HashMap<>();
        try {
            // 上传获取文件地址
            String url = ftpUtil.upload2url(file.getInputStream(),fileName);
            map.put("src", url);
        } catch (IOException e){
            return ResultVOUtil.error(1,e.getMessage());
        }
        return ResultVOUtil.success(map);
    }

    /*@GetMapping("/{id:\\d+}")
    public void downloader(@PathVariable String id, HttpServletRequest request , HttpServletResponse response) throws Exception{
        // JDK 1.7 新增的方法
        try (InputStream inputStream = new FileInputStream(new File(path,id+".txt"));
             OutputStream outputStream = response.getOutputStream();){

            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }
    }*/

    /**
     *
     * 功能描述: wangEditor富文本编辑器
     *
     * @param request
     * @param response
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @ResponseBody
    @RequestMapping("/wangEditor")
    public Map<String,Object> upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> result = new HashMap<>();
        if (!ServletFileUpload.isMultipartContent(request)) {
            result.put("errno",1);
            result.put("data",null);
            return result;
        } else {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMaps = multipartRequest.getFileMap();
            List<String> urlList = new ArrayList<>();
            for (MultipartFile file : fileMaps.values()) {
                try {
                    String url = ftpUtil.upload2url(file.getInputStream(),file.getOriginalFilename());
                    if (!StringUtils.isEmpty(url)){
                        urlList.add(url);
                    }else {
                        result.put("errno",1);
                        result.put("data",null);
                        return result;
                    }
                } catch (Exception e) {
                    result.put("errno",1);
                    result.put("data",null);
                    return result;
                }
            }
            result.put("errno",0);
            result.put("data", urlList);
        }
        return result;
    }
}
