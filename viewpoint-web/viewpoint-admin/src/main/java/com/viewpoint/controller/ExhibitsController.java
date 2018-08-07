package com.viewpoint.controller;

import com.google.zxing.WriterException;
import com.viewpoint.dataobject.Areas;
import com.viewpoint.dataobject.ExhibitsInfo;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.form.ExhibitsForm;
import com.viewpoint.service.AreasService;
import com.viewpoint.service.ExhibitsService;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import com.viewpoint.util.FTPUtil;
import com.viewpoint.util.KeyUtil;
import com.viewpoint.util.QRCodeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 展品管理
 */
@Controller
@RequestMapping("/exhibit")
public class ExhibitsController {

    @Autowired
    private ExhibitsService exhibitsService;

    @Autowired
    private AreasService areasService;

    @Autowired
    private FTPUtil ftpUtil;

    @GetMapping("/index")
    public String index(){
        return "intra/exhibit/list";
    }

    @GetMapping("/item/{parentId}")
    public String item(@PathVariable(value = "parentId") String parentId , Model model){
        model.addAttribute("parentId",parentId);
        return "intra/exhibit/item";
    }

    @GetMapping("/add")
    public String add(@RequestParam(value = "exhibitsId",required = false) String exhibitsId,
                      @RequestParam(value = "parentId",required = false) String parentId,
                      Model model){
        if(!StringUtils.isEmpty(exhibitsId)){
            ExhibitsInfo exhibitsInfo = exhibitsService.findOne(exhibitsId);
            model.addAttribute("exhibitsInfo",exhibitsInfo);
        }
        model.addAttribute("parentId",parentId);
        List<Areas> areasList = areasService.findAll();
        model.addAttribute("areasList",areasList);
        return "intra/exhibit/add";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "limit", defaultValue = "10") Integer size,
                         @RequestParam(value = "parentId",required = false) String parentId){
        Page<ExhibitsInfo> exhibitsInfoPage = exhibitsService.findByParentId(parentId,PageRequest.of(page-1,size));
        List<ExhibitsInfo> exhibitsInfoList = exhibitsInfoPage.getContent();
        // parentId 为空证明是主展品 查询主展品所在的景点
        if (StringUtils.isEmpty(parentId)){
            exhibitsInfoList.stream().forEach(e -> {
                Areas areas = areasService.findByAreasId(Integer.valueOf(e.getAreasId()));
                e.setAreasId(areas.getAreasName());
            });
        }
        return ResultVOUtil.success(exhibitsInfoList,exhibitsInfoPage.getTotalElements());
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVO add(@Valid ExhibitsForm exhibitsForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        ExhibitsInfo exhibitsInfo = new ExhibitsInfo();
        String exhibitsId = KeyUtil.genUniqueKey();
        // 主展品生成
        if (StringUtils.isEmpty(exhibitsForm.getParentId())){
            exhibitsForm.setParentId(null);
            // 生成二维码返回BufferedImage 对象
            try {
                BufferedImage bufferedImage = QRCodeUtils.toBufferedImage("https://www.jianshu.com/p/05e9ee773898");
                //转换成类型
                //BufferedImage 转 InputStream
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageOutputStream imageOutput = ImageIO.createImageOutputStream(byteArrayOutputStream);
                ImageIO.write(bufferedImage, "png", imageOutput);
                InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                String urlLink = ftpUtil.uploadLocalFile(inputStream,exhibitsId+".png");
                exhibitsInfo.setExhibitsLink(urlLink);
            } catch (WriterException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //如果productId为空, 说明是新增
            if(!StringUtils.isEmpty(exhibitsForm.getExhibitsId())){
                exhibitsInfo = exhibitsService.findOne(exhibitsForm.getExhibitsId());
            }else {
                exhibitsForm.setExhibitsId(exhibitsId);
            }
            BeanUtils.copyProperties(exhibitsForm, exhibitsInfo);
            exhibitsService.save(exhibitsInfo);
        }  catch (ViewpointException e) {
            return ResultVOUtil.error(1,e.getMessage());
        }
        return ResultVOUtil.success();
    }

    @ResponseBody
    @PostMapping("/updateSale")
    public ResultVO updateSale(String exhibitsId,Integer exhibitsStatus){
        try {
            exhibitsService.updateSale(exhibitsId,exhibitsStatus);
        } catch (ViewpointException e){
            return ResultVOUtil.error(1,e.getMessage());
        }
        return ResultVOUtil.success();
    }

    @PostMapping("/del")
    @ResponseBody
    public ResultVO del(String exhibitsId){
        try {
            exhibitsService.delBatch(exhibitsId);
        } catch (ViewpointException e){
            return ResultVOUtil.error(1,e.getMessage());
        }
        return ResultVOUtil.success();
    }
}
