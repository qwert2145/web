package com.womai.platform.admin.web.controller;

import com.womai.common.framework.web.url.WebUrl;
import com.womai.platform.admin.service.inte.AccountsAdminService;
import com.womai.platform.admin.service.inte.TextTemplateAdminService;
import com.womai.platform.admin.web.Util.PageUtil;
import com.womai.platform.admin.web.model.PageModel;
import com.womai.platform.api.model.AccountsApi;
import com.womai.platform.api.model.TextTemplateApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/23.
 */
@Controller
@RequestMapping("/textTemplate")
public class TextTemplateController {
    @Autowired
    private TextTemplateAdminService textTemplateAdminService;
    @Autowired
    private AccountsAdminService accountsAdminService;
    @Autowired
    private WebUrl webUrl;
    /*列表*/
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView Index(@RequestParam int pageNum) {
        PageModel pageModel = new PageModel();
        HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
        PageUtil.getParamMap(paramMap, pageNum);
        List<TextTemplateApi> textTemplateApiList = textTemplateAdminService.selectListByPage(paramMap);
        ModelAndView modelAndView = new ModelAndView("texttemplate/index");
        modelAndView.addObject("textTemplateApiList",textTemplateApiList);

        int totalNum = textTemplateAdminService.getCount();
        PageUtil.getPageModel(pageModel,pageNum,totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/textTemplate/index.do"));

        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        modelAndView.addObject("accountId",0);
        return modelAndView;
    }

    /*新增*/
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("texttemplate/create");
        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        return modelAndView;
    }

    /*新增保存*/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(TextTemplateApi textTemplateApi) {
        textTemplateApi.setIsvoid(0);
        textTemplateApi.setCreatedAt(new Date());
        textTemplateAdminService.insertTextTemplate(textTemplateApi);
        return "redirect:/textTemplate/index.do?pageNum=1";
    }

    /*修改*/
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int id,String accountName) {
        ModelAndView modelAndView = new ModelAndView("texttemplate/edit");
        TextTemplateApi textTemplateApi = textTemplateAdminService.selectById(id);
        modelAndView.addObject("textTemplateApi", textTemplateApi);
        modelAndView.addObject("accountName", accountName);
        return modelAndView;
    }

    /*保存修改*/
    @RequestMapping(value = "/editsave", method = RequestMethod.POST)
    public String editSave(TextTemplateApi textTemplateApi) {
        Date createdAt =textTemplateAdminService.selectById(textTemplateApi.getId()).getCreatedAt();
        textTemplateApi.setCreatedAt(createdAt);
        textTemplateAdminService.updateTextTemplate(textTemplateApi);
        return "redirect:/textTemplate/index.do?pageNum=1";
    }

    /*状态修改*/
    @RequestMapping(value = "/editStatus", method = RequestMethod.GET)
    public String editStatus(@RequestParam int id) {
        TextTemplateApi textTemplateApi = textTemplateAdminService.selectById(id);
        textTemplateApi.setIsvoid(1);
        textTemplateAdminService.updateTextTemplate(textTemplateApi);
        return "redirect:/textTemplate/index.do?pageNum=1";
    }

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ModelAndView query(int pageNum,int accountId,String name) {
        PageModel pageModel = new PageModel();
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        PageUtil.getObjectParamMap(paramMap, pageNum);
        paramMap.put("accountId", accountId);
        paramMap.put("name", name);

        List<TextTemplateApi> textTemplateApiList = textTemplateAdminService.query(paramMap);
        ModelAndView modelAndView = new ModelAndView("texttemplate/index");
        modelAndView.addObject("textTemplateApiList",textTemplateApiList);

        int totalNum = textTemplateAdminService.queryCount(paramMap);
        PageUtil.getPageModel(pageModel,pageNum,totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/textTemplate/query.do").addQueryData("accountId",accountId).addQueryData("name",name));

        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        modelAndView.addObject("accountId",accountId);
        modelAndView.addObject("name", name);
        return modelAndView;
    }
}
