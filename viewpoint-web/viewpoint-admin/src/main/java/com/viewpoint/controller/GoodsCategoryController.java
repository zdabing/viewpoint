package com.viewpoint.controller;

import com.viewpoint.dataobject.GoodsCategory;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.service.GoodsCategoryService;
import com.viewpoint.util.KeyUtil;
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
@RequestMapping("/goodsCategory")
public class GoodsCategoryController {

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @RequestMapping("/index")
    public String index(){
        return "goodsCategory/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "limit", defaultValue = "10") Integer size){
        Page<GoodsCategory> categoryPage = goodsCategoryService.findAll(PageRequest.of(page-1,size));
        return ResultVOUtil.success(categoryPage.getContent(),categoryPage.getTotalElements());
    }

    @RequestMapping("/add")
    public String add(Model model,@RequestParam(value = "goodsId",required = false) String goodsId){
        if(goodsId != null){
            GoodsCategory goodsCategory = goodsCategoryService.findById(goodsId);
            if(goodsCategory != null){
                model.addAttribute("goodsCategory",goodsCategory);
            }
        }
        return "goodsCategory/newsAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultVO save(GoodsCategory goodsCategory){
        if(StringUtils.isEmpty(goodsCategory.getGoodsId())){
            //说明是新增的
            goodsCategory.setGoodsId(KeyUtil.genUniqueKey());
            GoodsCategory goodsCategory1 = goodsCategoryService.save(goodsCategory);
            if(goodsCategory1 == null){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"添加失败请重新添加");
            }
        }else{
            //说明是修改的
            GoodsCategory goodsCategory1 = goodsCategoryService.findById(goodsCategory.getGoodsId());
            goodsCategory1.setGoodsContent(goodsCategory.getGoodsContent());
            goodsCategory1.setGoodsDesc(goodsCategory.getGoodsDesc());
            goodsCategory1.setGoodsName(goodsCategory.getGoodsName());
            goodsCategory1.setGoodsIcon(goodsCategory.getGoodsIcon());
            GoodsCategory goodsCategory2 = goodsCategoryService.save(goodsCategory1);
            if(goodsCategory2 == null){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"修改失败请重新修改");
            }
        }
        return ResultVOUtil.success();
    }

    @RequestMapping("/updateSale")
    @ResponseBody
    public ResultVO updateSale(String goodsId,Integer enabled){
        GoodsCategory goodsCategory = goodsCategoryService.updateSale(goodsId, enabled);
        if(goodsCategory != null){
            return  ResultVOUtil.success();
        }
        return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"发布失败");
    }

    @RequestMapping("/del")
    @ResponseBody
    public ResultVO del(String goodsId){
        if(goodsId == null){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"参数错误");
        }
        goodsCategoryService.delete(goodsId);
        return ResultVOUtil.success();
    }
}
