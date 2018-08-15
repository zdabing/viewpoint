package com.viewpoint.controller;

import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import com.viewpoint.util.FTPUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private FTPUtil ftpUtil;

    @Value("${ftp.fileUploadPath}")
    private String FILE_PATH;

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
    public ResultVO upload(MultipartFile file) {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取后缀
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String url = null;
        Map<String, String> map = new HashMap<>();
        try {
            // 上传获取文件地址
            url = ftpUtil.uploadLocalFile(file.getInputStream(),fileName);
            map.put("src", url);
        } catch (IOException e){
            return ResultVOUtil.error(1,e.getMessage());
        }
        if (StringUtils.isEmpty(url)) {
            return ResultVOUtil.error(1,"上传失败");
        }
        return ResultVOUtil.success(map);
    }

    /**
     *
     * 功能描述: 删除FTP文件
     *
     * @param fileUrl
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @PostMapping("/del")
    @ResponseBody
    public ResultVO del(@RequestParam(value = "fileUrl") String fileUrl) {
        String newUrl = fileUrl.replace(FILE_PATH, "");
        try {
            if(ftpUtil.deleteFile(newUrl)){
                return ResultVOUtil.success();
            }else {
                return ResultVOUtil.error(1,"删除失败");
            }
        } catch (Exception e){
            return ResultVOUtil.error(1,e.getMessage());
        }
    }

    /**
     * 文件下载
     *
     * @param fileUrl
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/download")
    public void downloader(@RequestParam(value = "fileUrl") String fileUrl,HttpServletRequest request , HttpServletResponse response) throws Exception{
        String newUrl = fileUrl.replace(FILE_PATH, "");
        String fileName = newUrl.substring(newUrl.lastIndexOf("/")+1);
        String ftpPath = newUrl.substring(0,newUrl.lastIndexOf("/")+1);
        // JDK 1.7 新增的方法
        try (InputStream inputStream = ftpUtil.getInputStreamByName(ftpPath,fileName);
             OutputStream outputStream = response.getOutputStream();){

            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename="+fileName);

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }
    }

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
                    String url = ftpUtil.uploadLocalFile(file.getInputStream(),file.getOriginalFilename());
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

    /**
     *
     * 功能描述: 多文件上传
     * @param files
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @PostMapping(value="/multipleSave")
    @ResponseBody
    public ResultVO multipleSave(@RequestParam("file") MultipartFile[] files){
        String fileName = null;
        Map<String, String> map = new HashMap<>();
        if (files != null && files.length >0) {
            for(int i =0 ;i< files.length; i++){
                try {
                    fileName = files[i].getOriginalFilename();
/*                    byte[] bytes = files[i].getBytes();
                    BufferedOutputStream buffStream =
                            new BufferedOutputStream(new FileOutputStream(new File("/tmp/" + fileName)));
                    buffStream.write(bytes);
                    buffStream.close();*/
                    // 上传FTP
                    String url = ftpUtil.uploadLocalFile(files[i].getInputStream(),fileName);
                    map.put("src", url);
                } catch (Exception e) {
                    return ResultVOUtil.error(1,"文件上传失败 " + fileName + ": " + e.getMessage());
                }
            }
            return ResultVOUtil.success(map);
        } else {
            return ResultVOUtil.error(1,"文件上传失败");
        }
    }

}
