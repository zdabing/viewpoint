package com.viewpoint.controller;

import com.viewpoint.dataobject.Article;
import com.viewpoint.dataobject.ArticleNodes;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.form.ArticleForm;
import com.viewpoint.service.ArticleNodesService;
import com.viewpoint.service.ArticleService;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleNodesService articleNodesService;

    @GetMapping("/index")
    public String list(){
        return "intra/article/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "limit", defaultValue = "10") Integer size,
                              Model model){
        ResultVO resultVO =  articleService.findAllArticle(PageRequest.of(0,10));
        Page<Article> articlePage = (Page<Article>)resultVO.getData();
        return ResultVOUtil.success(articlePage.getContent(),articlePage.getTotalElements());
    }

    @GetMapping("/add")
    public String add(@RequestParam(value = "articleId",required = false)Integer articleId,Model model){
        if(!StringUtils.isEmpty(articleId)){
            Article article = articleService.findArticleById(articleId.toString());
            model.addAttribute("article",article);
        }
        List<ArticleNodes> nodes = new ArrayList<ArticleNodes>();
        List<ArticleNodes> articleNodesList = articleNodesService.findAllByFatherId();
        for (ArticleNodes articleNodes : articleNodesList) {
            if(articleNodes.getHasChildren() == 0){
                nodes.add(articleNodes);
            }else{
                nodes.add(articleNodes);
                List<ArticleNodes> articleChildrenNodesList = articleNodesService.findChildrenId(articleNodes.getNodeId());
                for(ArticleNodes articleChildrenNodes : articleChildrenNodesList){
                    articleChildrenNodes.setNodeName( "&nbsp;&nbsp;&nbsp;&nbsp;|__"+articleChildrenNodes.getNodeName());
                    nodes.add(articleChildrenNodes);
                }
            }
        }
        model.addAttribute("nodes",nodes);
        return "intra/article/newsAdd";
    }


    @PostMapping("/save")
    @ResponseBody
    public ResultVO save(@Valid ArticleForm articleForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        Article articleInfo = new Article();
        try {
            //如果ArticleId为空, 说明是新增
            if(!StringUtils.isEmpty(articleForm.getArticleId())){
                articleInfo = articleService.findArticleById(articleForm.getArticleId().toString());
                articleInfo.setUpdateTime(LocalDateTime.now());
            }else {
                articleInfo.setCreateTime(LocalDateTime.now());
                articleInfo.setUpdateTime(LocalDateTime.now());
                articleInfo.setEnabled(0);
            }
            BeanUtils.copyProperties(articleForm, articleInfo);
            articleService.addArticle(articleInfo);
        }  catch (ViewpointException e) {
            return ResultVOUtil.error(1,e.getMessage());
        }
        return ResultVOUtil.success();
    }

    @PostMapping("/childrenArticleNode")
    @ResponseBody
    public ResultVO getChildrenArticleNode(Integer nodeId,Model model){
        if(nodeId == null){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"参数错误");
        }
        ArticleNodes articleNodes = articleNodesService.findByNodeId(nodeId);
        if(articleNodes.getHasChildren() == 0){
            return ResultVOUtil.success(articleNodes);
        }else{
            List<ArticleNodes> articleNodesList = articleNodesService.findChildrenId(nodeId);
            model.addAttribute("childrenNodeList",articleNodesList);

            return ResultVOUtil.success(articleNodesList);
        }

    }

    @RequestMapping("/updateSale")
    @ResponseBody
    public ResultVO updateSale(Integer articleId,Integer enabled){
        try {
            articleService.updateSale(articleId,enabled);
        } catch (ViewpointException e){
            return ResultVOUtil.error(1,e.getMessage());
        }
        return ResultVOUtil.success();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResultVO delete(Integer articleId){
        try {
            articleService.deleteArticle(articleId.toString());
        }catch (ViewpointException e){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),e.getMessage());
        }
        return ResultVOUtil.success();
    }


}
