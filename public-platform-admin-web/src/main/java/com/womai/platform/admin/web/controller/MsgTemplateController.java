package com.womai.platform.admin.web.controller;

import com.womai.common.framework.web.url.WebUrl;
import com.womai.platform.admin.service.inte.AccountsAdminService;
import com.womai.platform.admin.service.inte.MsgTemplateAdminService;
import com.womai.platform.admin.web.Util.PageUtil;
import com.womai.platform.admin.web.model.PageModel;
import com.womai.platform.api.model.AccountsApi;
import com.womai.platform.api.model.MsgTemplateApi;
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
 * Created by wlb on 2015/10/22.
 */
@Controller
@RequestMapping("/msgTemplate")
public class MsgTemplateController {
    @Autowired
    private MsgTemplateAdminService msgTemplateAdminService;
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

        List<MsgTemplateApi> msgTemplateApis = msgTemplateAdminService.selectListByPage(paramMap);
        ModelAndView modelAndView = new ModelAndView("msgtemplate/index");
        modelAndView.addObject("msgTemplateApis", msgTemplateApis);

        int totalNum = msgTemplateAdminService.getCount();
        PageUtil.getPageModel(pageModel, pageNum, totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/msgTemplate/index.do"));

        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        modelAndView.addObject("accountId", 0);
        modelAndView.addObject("msgTemplateType", "");
        return modelAndView;
    }

    /*新增*/
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("msgtemplate/create");
        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        return modelAndView;
    }

    /*新增保存*/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(MsgTemplateApi msgTemplateApi) {
        msgTemplateApi.setIsvoid(0);
        msgTemplateApi.setCreatedAt(new Date());
        msgTemplateAdminService.insertMsgTemplate(msgTemplateApi);
        return "redirect:/msgTemplate/index.do?pageNum=1";
    }

    /*修改*/
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int id,String accountName) {
        MsgTemplateApi msgTemplateApi = msgTemplateAdminService.selectById(id);
        ModelAndView modelAndView = new ModelAndView("msgtemplate/edit","msgTemplateApi",msgTemplateApi);
        modelAndView.addObject("accountName", accountName);
        return modelAndView;
    }

    /*保存修改*/
    @RequestMapping(value = "/editsave", method = RequestMethod.POST)
    public String editSave(MsgTemplateApi msgTemplateApi) {
        Date createdAt = msgTemplateAdminService.selectById(msgTemplateApi.getId()).getCreatedAt();
        msgTemplateApi.setCreatedAt(createdAt);
        msgTemplateAdminService.updateMsgTemplate(msgTemplateApi);
        return "redirect:/msgTemplate/index.do?pageNum=1";
    }
    /*状态修改*/
    @RequestMapping(value = "/editStatus", method = RequestMethod.GET)
    public String editStatus(@RequestParam int id) {
        MsgTemplateApi msgTemplateApi = msgTemplateAdminService.selectById(id);
        msgTemplateApi.setIsvoid(1);
        msgTemplateAdminService.updateMsgTemplate(msgTemplateApi);
        return "redirect:/msgTemplate/index.do?pageNum=1";
    }

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ModelAndView query(@RequestParam int pageNum,int accountId,String msgTemplateType,String title) {
        PageModel pageModel = new PageModel();
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        PageUtil.getObjectParamMap(paramMap, pageNum);
        paramMap.put("accountId", accountId);
        paramMap.put("msgTemplateType",msgTemplateType);
        paramMap.put("title", title);

        List<MsgTemplateApi> msgTemplateApis = msgTemplateAdminService.query(paramMap);
        ModelAndView modelAndView = new ModelAndView("msgtemplate/index");
        modelAndView.addObject("msgTemplateApis", msgTemplateApis);

        int totalNum = msgTemplateAdminService.queryCount(paramMap);
        PageUtil.getPageModel(pageModel, pageNum, totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/msgTemplate/query.do").addQueryData("accountId",accountId).addQueryData("msgTemplateType",msgTemplateType));

        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        modelAndView.addObject("accountId", accountId);
        modelAndView.addObject("msgTemplateType", msgTemplateType);
        modelAndView.addObject("title", title);
        return modelAndView;
    }
}
