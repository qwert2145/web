package com.womai.platform.admin.web.controller;

import com.womai.common.framework.web.url.WebUrl;
import com.womai.platform.admin.service.inte.NewsArticleTemplateAdminService;
import com.womai.platform.admin.service.inte.NewsTemplateAdminService;
import com.womai.platform.admin.web.Util.PageUtil;
import com.womai.platform.admin.web.model.PageModel;
import com.womai.platform.api.model.NewsArticleTemplateApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/22.
 */
@Controller
@RequestMapping("/newsArticleTemplate")
public class NewsArticleTemplateController {
    @Autowired
    private NewsArticleTemplateAdminService newsArticleTemplateAdminService;
    @Autowired
    private NewsTemplateAdminService newsTemplateAdminService;
    @Autowired
    WebUrl webUrl;
    /*列表*/

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView Index(@RequestParam int pageNum,@RequestParam int newsTemplateId,@RequestParam String articleType) {
        PageModel pageModel = new PageModel();
        HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
        PageUtil.getParamMap(paramMap, pageNum);
        paramMap.put("newsTemplateId", newsTemplateId);
        List<NewsArticleTemplateApi> newsArticleTemplateApis = newsArticleTemplateAdminService.selectListByPage(paramMap);
        ModelAndView modelAndView = new ModelAndView("newsarticletemplate/index");
        modelAndView.addObject("newsArticleTemplateApis", newsArticleTemplateApis);
        int totalNum = newsArticleTemplateAdminService.getCount(newsTemplateId);
        PageUtil.getPageModel(pageModel,pageNum,totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("newsTemplateId", newsTemplateId);
        modelAndView.addObject("articleType", articleType);
        modelAndView.addObject("module", webUrl.getTarget("/newsArticleTemplate/index.do").addQueryData("newsTemplateId",newsTemplateId).addQueryData("articleType",articleType));
        return modelAndView;
    }
    /*新增*/
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam int newsTemplateId) {
        ModelAndView modelAndView = new ModelAndView("newsarticletemplate/create","newsTemplateId",newsTemplateId);
        return modelAndView;
    }


    /*新增保存*/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(NewsArticleTemplateApi newsArticleTemplateApi) {
        newsArticleTemplateApi.setIsvoid(0);
        newsArticleTemplateApi.setCreatedAt(new Date());
        newsArticleTemplateAdminService.insertNewsArticleTemplate(newsArticleTemplateApi);
        String articleType = newsTemplateAdminService.selectById(newsArticleTemplateApi.getNewsTemplateId()).getArticleType();
        return "redirect:/newsArticleTemplate/index.do?pageNum=1&newsTemplateId=" + newsArticleTemplateApi.getNewsTemplateId()
                + "&articleType="+articleType;
    }

    /*修改*/
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int id) {
        NewsArticleTemplateApi newsArticleTemplateApi = newsArticleTemplateAdminService.selectById(id);
        ModelAndView modelAndView = new ModelAndView("newsarticletemplate/edit");
        modelAndView.addObject("newsArticleTemplateApi",newsArticleTemplateApi);
        return modelAndView;
    }

    /*保存修改*/
    @RequestMapping(value = "/editsave", method = RequestMethod.POST)
    public String editSave(NewsArticleTemplateApi newsArticleTemplateApi,HttpServletRequest request) {
        Date createdAt = newsArticleTemplateAdminService.selectById(newsArticleTemplateApi.getId()).getCreatedAt();
        newsArticleTemplateApi.setCreatedAt(createdAt);
        newsArticleTemplateAdminService.updateNewsArticleTemplate(newsArticleTemplateApi);
        String articleType = newsTemplateAdminService.selectById(newsArticleTemplateApi.getNewsTemplateId()).getArticleType();
        return "redirect:/newsArticleTemplate/index.do?pageNum=1&newsTemplateId=" + newsArticleTemplateApi.getNewsTemplateId()
                + "&articleType=" + articleType;
    }
    /*状态修改*/
    @RequestMapping(value = "/editStatus", method = RequestMethod.GET)
    public String editStatus(@RequestParam int id) {
        NewsArticleTemplateApi newsArticleTemplateApi = newsArticleTemplateAdminService.selectById(id);
        newsArticleTemplateApi.setIsvoid(1);
        newsArticleTemplateAdminService.updateNewsArticleTemplate(newsArticleTemplateApi);
        String articleType = newsTemplateAdminService.selectById(newsArticleTemplateApi.getNewsTemplateId()).getArticleType();
        return "redirect:/newsArticleTemplate/index.do?pageNum=1&newsTemplateId=" + newsArticleTemplateApi.getNewsTemplateId()
                + "&articleType=" + articleType;
    }
}
