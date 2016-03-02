package com.womai.platform.admin.web.controller;

import com.womai.common.framework.web.url.WebUrl;
import com.womai.platform.admin.service.inte.AccountsAdminService;
import com.womai.platform.admin.web.Util.PageUtil;
import com.womai.platform.admin.web.model.PageModel;
import com.womai.platform.api.model.AccountsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/22.
 */
@Controller
@RequestMapping("/accounts")
public class AccountsController {
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
        List<AccountsApi> accountsApis = accountsAdminService.selectListByPage(paramMap);
        ModelAndView modelAndView = new ModelAndView("accounts/index");
        modelAndView.addObject("accountsApis", accountsApis);

        int totalNum = accountsAdminService.getCount();
        PageUtil.getPageModel(pageModel,pageNum,totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/accounts/index.do"));
        modelAndView.addObject("type", 0);
        return modelAndView;
    }

    /*新增*/
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "accounts/create";
    }

    /*新增保存*/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(AccountsApi accountsApi) {
        accountsApi.setIsvoid(0);
        accountsApi.setCreatedAt(new Date());
        accountsAdminService.insertAccounts(accountsApi);
        return "redirect:/accounts/index.do?pageNum=1";
    }

    /*修改*/
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int id) {
        AccountsApi accountsApi = accountsAdminService.selectById(id);
        ModelAndView modelAndView = new ModelAndView("accounts/edit","accountsApi",accountsApi);
        return modelAndView;
    }

    /*保存修改*/
    @RequestMapping(value = "/editsave", method = RequestMethod.POST)
    public String editSave(AccountsApi accountsApi) {
        Date createdAt = accountsAdminService.selectById(accountsApi.getId()).getCreatedAt();
        accountsApi.setCreatedAt(createdAt);
        accountsAdminService.updateAccounts(accountsApi);
        return "redirect:/accounts/index.do?pageNum=1";
    }
    /*状态修改*/
    @RequestMapping(value = "/editStatus", method = RequestMethod.GET)
    public String editStatus(@RequestParam int id) {
        AccountsApi accountsApi = accountsAdminService.selectById(id);
        accountsApi.setIsvoid(1);
        accountsAdminService.updateAccounts(accountsApi);
        return "redirect:/accounts/index.do?pageNum=1";
    }
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ModelAndView query(int pageNum,int type,String name) {
        PageModel pageModel = new PageModel();
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        PageUtil.getObjectParamMap(paramMap, pageNum);
        paramMap.put("accountType", type);
        paramMap.put("accountName", name);

        List<AccountsApi> accountsApis = accountsAdminService.query(paramMap);
        ModelAndView modelAndView = new ModelAndView("accounts/index");
        modelAndView.addObject("accountsApis", accountsApis);

        int totalNum = accountsAdminService.queryCount(paramMap);
        PageUtil.getPageModel(pageModel, pageNum, totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/accounts/query.do").addQueryData("type",type).addQueryData("name",name));

        modelAndView.addObject("type",type);
        modelAndView.addObject("name", name);
        return modelAndView;
    }
    //检查appid是否重复
    @RequestMapping(value = "/appid",method = RequestMethod.POST)
    @ResponseBody
    public String selectByAppid(String appid) throws IOException {
        AccountsApi accountsApi = accountsAdminService.selectByAppid(appid);
        if(accountsApi.getAppid() != null ){
            return "1";
        }else{
            return "0";
        }
    }
}
