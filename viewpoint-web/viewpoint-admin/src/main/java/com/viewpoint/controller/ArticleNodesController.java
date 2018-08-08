package com.viewpoint.controller;

import com.viewpoint.dataobject.ArticleNodes;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.form.ArticleNodesForm;
import com.viewpoint.service.ArticleNodesService;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/articleNodes")
public class ArticleNodesController {

    @Autowired
    private ArticleNodesService articleNodesService;

    @GetMapping("/index")
    public String list(){
        return "articleNodes/list";
    }


    @GetMapping("/list")
    @ResponseBody
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "limit", defaultValue = "10") Integer size){

        List<ArticleNodes> articleNodesList = articleNodesService.findListArticleNodes();
        return ResultVOUtil.success(articleNodesList);
    }

    @RequestMapping("/add")
    public String add(Model model,@RequestParam(required = false,value = "nodeId") Integer nodeId){
        if(!StringUtils.isEmpty(nodeId)){
            ArticleNodes articleNodes = articleNodesService.findByNodeId(nodeId);
            model.addAttribute("articleNodes",articleNodes);
        }

        List<ArticleNodes> articleNodesList = articleNodesService.findAllByFatherId();
        Iterator<ArticleNodes> iterator = articleNodesList.iterator();
        if(!StringUtils.isEmpty(nodeId)){
            ArticleNodes articleNodes = articleNodesService.findByNodeId(nodeId);
            if(articleNodes.getParentId() != 0 ){
                while(iterator.hasNext()){
                    ArticleNodes articleNodes3 = iterator.next();
                    if(articleNodes3.getHasChildren() == 0){
                        iterator.remove();
                    }
                }
                model.addAttribute("articleNodesList",articleNodesList);
                return "articleNodes/newsAdd";
            }
        }

        model.addAttribute("articleNodesList",articleNodesList);
        return "articleNodes/newsAdd";
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVO save(@Valid ArticleNodesForm articleNodesForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        ArticleNodes articleNodesInfo = new ArticleNodes();

        try {
            //说明是新增加的
            if(StringUtils.isEmpty(articleNodesForm.getNodeId())){
                //增加父节点
                if(articleNodesForm.getHasChildren() == 0){
                    BeanUtils.copyProperties(articleNodesForm, articleNodesInfo);
                    articleNodesInfo.setEnabled(0);
                    articleNodesInfo.setUpdateTime(LocalDateTime.now());
                    articleNodesService.save(articleNodesInfo);
                }else{
                    //增加子节点
                    BeanUtils.copyProperties(articleNodesForm, articleNodesInfo);
                    ArticleNodes articleNodes = articleNodesService.findByNodeId(articleNodesForm.getParentId());
                    articleNodes.setHasChildren(1);
                    articleNodesService.save(articleNodes);
                    articleNodesInfo.setEnabled(0);
                    articleNodesInfo.setUpdateTime(LocalDateTime.now());
                    articleNodesService.save(articleNodesInfo);
                }

            }else{
                //更改节点
                //更改父节点
                if(articleNodesForm.getParentId() == 0){
                    List<ArticleNodes> articleNodesList = articleNodesService.findChildrenId(articleNodesForm.getNodeId());
                    if(articleNodesList.isEmpty()){
                        //如果父节点下面没有子节点,
                        BeanUtils.copyProperties(articleNodesForm, articleNodesInfo);
                        articleNodesInfo.setEnabled(0);
                        articleNodesInfo.setUpdateTime(LocalDateTime.now());
                        articleNodesService.save(articleNodesInfo);
                    }else {
                        //如果父节点下面有子节点,需要更改childrenId
                        BeanUtils.copyProperties(articleNodesForm, articleNodesInfo);
                        articleNodesInfo.setHasChildren(1);
                        articleNodesInfo.setEnabled(0);
                        articleNodesInfo.setUpdateTime(LocalDateTime.now());
                        articleNodesService.save(articleNodesInfo);
                    }

                }else {
                    //更改子节点
                    BeanUtils.copyProperties(articleNodesForm, articleNodesInfo);
                    articleNodesInfo.setHasChildren(0);
                    articleNodesInfo.setEnabled(0);
                    articleNodesInfo.setUpdateTime(LocalDateTime.now());
                    articleNodesService.save(articleNodesInfo);
                }

            }
        }  catch (ViewpointException e) {
            return ResultVOUtil.error(1,e.getMessage());
        }
        return ResultVOUtil.success();
    }

    @RequestMapping("/updateSale")
    @ResponseBody
    public ResultVO updateSale(Integer nodeId,Integer enabled){
        try{
            ArticleNodes articleNodes = articleNodesService.findByNodeId(nodeId);
            if(articleNodes.getParentId() != 0){
                Integer nodeId1 = articleNodes.getParentId();
                ArticleNodes articleNodes1 = articleNodesService.findByNodeId(nodeId1);
                if(articleNodes1.getEnabled() == 0 && enabled == 1){
                    return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"请先上架父节点");
                }else{
                    articleNodesService.updateSale(nodeId,enabled);
                }
            }else {
                articleNodesService.updateSale(nodeId,enabled);
            }
        }catch (ViewpointException e){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),e.getMessage());
        }
        return ResultVOUtil.success();
    }

    /**
     * 删除操作
     * @param nodeId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResultVO delete(Integer nodeId){
        try{
            ArticleNodes articleNodes = articleNodesService.findByNodeId(nodeId);
            if(articleNodes.getParentId() == 0){
                List<ArticleNodes> articleNodesList = articleNodesService.findChildrenId(nodeId);
                for (ArticleNodes articleNodes1 : articleNodesList){
                    articleNodesService.delete(articleNodes1.getNodeId());
                }
            }
            articleNodesService.delete(nodeId);
        }catch (ViewpointException e){
           return ResultVOUtil.error(ResultEnum.ERROR.getCode(),e.getMessage());
        }
        return ResultVOUtil.success();
    }


}


