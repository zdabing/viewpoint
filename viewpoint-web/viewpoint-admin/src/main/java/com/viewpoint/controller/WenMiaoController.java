package com.viewpoint.controller;

import com.viewpoint.dataobject.View;
import com.viewpoint.service.WenMiaoService;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wenmiao")
public class WenMiaoController {

    @Autowired
    private WenMiaoService wenMiaoService;

    @RequestMapping("/index/{viewCode}")
    public String index(Model model,@PathVariable String viewCode){
        View view = wenMiaoService.getById(viewCode);
        if(viewCode.equals("WMJS") ){
            if(view != null){
                model.addAttribute("view",view);
            }
            return "wenmiaoDesc/newsAdd";
        }
        if(viewCode.equals("KJWH")){
            if(view != null){
                model.addAttribute("view",view);
            }
            return "kejuDesc/newsAdd";
        }
        if(viewCode.equals("ZFGH")){
            if(view != null){
                model.addAttribute("view",view);
            }
            return "zhenfuDesc/newsAdd";
        }
        if(viewCode.equals("NFFZ")){
            if(view != null){
                model.addAttribute("view",view);
            }
            return "nanfangDesc/newsAdd";
        }
        if(viewCode.equals("WWJS")){
            if(view != null){
                model.addAttribute("view",view);
            }
            return "goodsCategory/descAdd";
        }
        return null;
    }

    @RequestMapping("/save/{viewCode}")
    @ResponseBody
    public ResultVO save(View view,@PathVariable String viewCode){
        View view1 = wenMiaoService.Save(view,viewCode);
        if(view1 == null) {
            return ResultVOUtil.error(1,"添加失败");
        }
        return ResultVOUtil.success();
    }
}
