package com.viewpoint.controller;

import com.viewpoint.dataobject.Areas;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.form.AreasCoordinateForm;
import com.viewpoint.service.AreasService;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/areas")
public class AreasController {

    @Autowired
    private AreasService areasService;

    /**
     *跳到地图页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "intra/areas/aa";
    }

    /**
     *跳到列表页面
     * @return
     */
    @RequestMapping("/index")
    public String list(){
        return "intra/areas/list";
    }

    /**
     *跳到增加页面
     * @return
     */
    @RequestMapping("/add")
    public String add(@RequestParam(value = "areasCoordinateForm",required = false)AreasCoordinateForm areasCoordinateForm,@RequestParam(value = "areasId",required = false)Integer areasId, Model model){
        model.addAttribute("areasCoordinateForm",areasCoordinateForm);
        if(!StringUtils.isEmpty(areasId)){
            Areas areas = areasService.findByAreasId(areasId);
            model.addAttribute("areas",areas);
        }
        return  "intra/areas/newsAdd";
    }

    /**
     *给列表页赋值
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "limit", defaultValue = "10") Integer size){

        Page<Areas> areasPage = areasService.findAll(PageRequest.of(page - 1 ,size));
        return ResultVOUtil.success(areasPage.getContent(),areasPage.getTotalElements());
    }

    /**
     *保存或更改景点
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResultVO save(Areas areas){
        if(areas == null){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"参数不能为空");
        }
        areasService.save(areas);
        return ResultVOUtil.success();
    }

    /**
     *删除景点
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public ResultVO delete(Integer areasId){
        if(StringUtils.isEmpty(areasId)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"参数不能为空");
        }
        areasService.deleteByAreasId(areasId);
        return ResultVOUtil.success();
    }

}