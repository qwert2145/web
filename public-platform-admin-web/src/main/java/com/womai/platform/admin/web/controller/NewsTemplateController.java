package com.womai.platform.admin.web.controller;

import com.womai.common.framework.web.url.WebUrl;
import com.womai.platform.admin.service.inte.AccountsAdminService;
import com.womai.platform.admin.service.inte.NewsArticleTemplateAdminService;
import com.womai.platform.admin.service.inte.NewsTemplateAdminService;
import com.womai.platform.admin.web.Util.PageUtil;
import com.womai.platform.admin.web.model.PageModel;
import com.womai.platform.api.model.AccountsApi;
import com.womai.platform.api.model.NewsArticleTemplateApi;
import com.womai.platform.api.model.NewsTemplateApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wlb on 2015/10/22.
 */
@Controller
@RequestMapping("/newsTemplate")
public class NewsTemplateController {
    @Autowired
    private NewsTemplateAdminService newsTemplateAdminService;
    @Autowired
    private AccountsAdminService accountsAdminService;
    @Autowired
    private WebUrl webUrl;
    @Autowired
    private NewsArticleTemplateAdminService newsArticleTemplateAdminService;
    /*列表*/
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView Index(@RequestParam int pageNum) {
        PageModel pageModel = new PageModel();
        HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
        PageUtil.getParamMap(paramMap, pageNum);

        List<NewsTemplateApi> newsTemplateApis = newsTemplateAdminService.selectListByPage(paramMap);
        setHasChild(newsTemplateApis);
        ModelAndView modelAndView = new ModelAndView("newstemplate/index");
        modelAndView.addObject("newsTemplateApis", newsTemplateApis);
        int totalNum = newsTemplateAdminService.getCount();
        PageUtil.getPageModel(pageModel, pageNum, totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/newsTemplate/index.do"));
        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        modelAndView.addObject("accountId", 0);
        return modelAndView;
    }

    /*新增*/
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        ModelAndView modelAndView = new ModelAndView("newstemplate/create");
        modelAndView.addObject("accountsApis", accountsApis);
        return modelAndView;
    }

    /*新增保存*/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(NewsTemplateApi newsTemplateApi) {
        newsTemplateApi.setIsvoid(0);
        newsTemplateApi.setCreatedAt(new Date());
        newsTemplateApi.setArticleCount(0);
        newsTemplateAdminService.insertNewsTemplate(newsTemplateApi);
        return "redirect:/newsTemplate/index.do?pageNum=1";
    }

    /*修改*/
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int id) {
        NewsTemplateApi newsTemplateApi = newsTemplateAdminService.selectById(id);
        ModelAndView modelAndView = new ModelAndView("newstemplate/edit");
        modelAndView.addObject("newsTemplateApi", newsTemplateApi);
        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        Map map = articleType(newsTemplateApi.getAccountId());
        modelAndView.addObject("articleTypeMap", map);
        return modelAndView;
    }

    /*保存修改*/
    @RequestMapping(value = "/editsave", method = RequestMethod.POST)
    public String editSave(NewsTemplateApi newsTemplateApi) {
        Date createdAt = newsTemplateAdminService.selectById(newsTemplateApi.getId()).getCreatedAt();
        newsTemplateApi.setCreatedAt(createdAt);
        newsTemplateAdminService.updateNewsTemplate(newsTemplateApi);
        List<NewsArticleTemplateApi> newsArticleTemplateApis = newsArticleTemplateAdminService.selectByTemplateId(newsTemplateApi.getId());
        if(newsArticleTemplateApis.size() > 0){
            newsArticleTemplateAdminService.updateNewsArticleTemplate(newsArticleTemplateApis.get(0));
        }
        return "redirect:/newsTemplate/index.do?pageNum=1";
    }
    /*状态修改*/
    @RequestMapping(value = "/editStatus", method = RequestMethod.GET)
    public String editStatus(@RequestParam int id) {
        NewsTemplateApi newsTemplateApi = newsTemplateAdminService.selectById(id);
        newsTemplateApi.setIsvoid(1);
        newsTemplateAdminService.updateNewsTemplate(newsTemplateApi);
        List<NewsArticleTemplateApi> newsArticleTemplateApis = newsArticleTemplateAdminService.selectByTemplateId(id);
        for(NewsArticleTemplateApi newsArticleTemplateApi : newsArticleTemplateApis){
            newsArticleTemplateApi.setIsvoid(1);
            newsArticleTemplateAdminService.updateNewsArticleTemplate(newsArticleTemplateApi);
        }
        return "redirect:/newsTemplate/index.do?pageNum=1";
    }

    /*获取template*/
    @RequestMapping(value = "/getArticleType", method = RequestMethod.GET)
    @ResponseBody
    public String getArticleType(@RequestParam int templateId) {
        NewsTemplateApi newsTemplateApi = newsTemplateAdminService.selectById(templateId);
        return newsTemplateApi.getArticleType();
    }

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ModelAndView query(@RequestParam int pageNum,int accountId,String articleType,String name) {
        PageModel pageModel = new PageModel();
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        PageUtil.getObjectParamMap(paramMap, pageNum);
        paramMap.put("accountId", accountId);
        paramMap.put("articleType",articleType);
        paramMap.put("name", name);

        List<NewsTemplateApi> newsTemplateApis = newsTemplateAdminService.query(paramMap);
        ModelAndView modelAndView = new ModelAndView("newstemplate/index");
        modelAndView.addObject("newsTemplateApis", newsTemplateApis);
        setHasChild(newsTemplateApis);
        int totalNum = newsTemplateAdminService.queryCount(paramMap);
        PageUtil.getPageModel(pageModel, pageNum, totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/newsTemplate/query.do").addQueryData("accountId",accountId).addQueryData("name",name).addQueryData("articleType",articleType));

        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        modelAndView.addObject("accountId", accountId);
        modelAndView.addObject("articleType", articleType);
        modelAndView.addObject("name",name);
        Map map = null;
        if(accountId != 0){
            map = articleType(accountId);
        }else{
            map = new HashMap();
        }

        modelAndView.addObject("articleTypeMap", map);
        return modelAndView;
    }
    //根据accountid 生成articleType
    private Map articleType(int accountId){
        AccountsApi accountsApi = accountsAdminService.selectById(accountId);
        int accountType = accountsApi.getAccountType();
        Map<String, String> typeMap = new HashMap<String, String>();
        //accountType为3是小米账号,1或2位微信账号
        if(accountType == 3){
            typeMap.put("single_article_nomedia", "无图");
            typeMap.put("single_article", "单图");
        }else if(accountType == 1 || accountType == 2){
            typeMap.put("multi_article", "图文");
        }
        return typeMap;
    }

    //判断是否有子模板
    private void setHasChild(List<NewsTemplateApi> newsTemplateApis){
        for(NewsTemplateApi newsTemplateApi: newsTemplateApis){
            int totalNum = newsArticleTemplateAdminService.getCount(newsTemplateApi.getId());
            if (totalNum > 0) {
                newsTemplateApi.setHasChild(true);
            } else {
                newsTemplateApi.setHasChild(false);
            }
        }
    }
}
