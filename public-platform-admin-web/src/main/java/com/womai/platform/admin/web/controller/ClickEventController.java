package com.womai.platform.admin.web.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONObject;
import com.womai.common.framework.web.url.WebUrl;
import com.womai.common.utils.HttpUtils4;
import com.womai.common.utils.MultiThreadHttpClient4Manager;
import com.womai.platform.admin.service.impl.AccountsAdminServiceImpl;
import com.womai.platform.admin.service.inte.*;
import com.womai.platform.admin.web.Util.PageUtil;
import com.womai.platform.admin.web.model.ClickModel;
import com.womai.platform.admin.web.model.PageModel;
import com.womai.platform.admin.web.model.SubMenu;
import com.womai.platform.admin.web.model.ViewModel;
import com.womai.platform.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by wlb on 2015/10/22.
 */
@Controller
@RequestMapping("/clickEvent")
public class ClickEventController {
    @Autowired
    private ClickEventAdminService clickEventAdminService;
    @Autowired
    private AccountsAdminServiceImpl accountsAdminService;
    @Autowired
    private TextTemplateAdminService textTemplateAdminService;
    @Autowired
    private NewsTemplateAdminService newsTemplateAdminService;
    @Autowired
    private TokenAdminService tokenAdminService;
    @Autowired
    private MultiThreadHttpClient4Manager httpClient4Manager;
    @Autowired
    private WebUrl webUrl;

    @Autowired
    private ClickBindAdminService clickBindAdminService;
    @Autowired
    private MsgTypeBindAdminService msgTypeBindAdminService;
    /*列表*/
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView Index(@RequestParam int pageNum) {
        PageModel pageModel = new PageModel();
        HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
        PageUtil.getParamMap(paramMap, pageNum);
        List<MenuAccountApi> menuAccountApis = clickEventAdminService.selectListByPage(paramMap);
        setHasChild(menuAccountApis);
        ModelAndView modelAndView = new ModelAndView("clickevent/index");
        modelAndView.addObject("menuAccountApis", menuAccountApis);

        int totalNum = clickEventAdminService.getCount();
        PageUtil.getPageModel(pageModel, pageNum, totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/clickEvent/index.do"));

        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        List<AccountsApi> weixinAccount = new ArrayList<AccountsApi>();
        for(AccountsApi accountsApi : accountsApis){
            if(accountsApi.getAccountType() == 3){
                continue;
            }
            weixinAccount.add(accountsApi);
        }
        modelAndView.addObject("accountsApis", weixinAccount);
        return modelAndView;
    }

    /*新增*/
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam int accountId,@RequestParam int parentId,@RequestParam int accountMenu) {
        ModelAndView modelAndView = new ModelAndView("clickevent/create");
        List<AccountsApi> accountsApis = null;
        if(accountId == 0){
            accountsApis = accountsAdminService.selectList();
        }else{
            accountsApis = new ArrayList<AccountsApi>();
            AccountsApi accountsApi = accountsAdminService.selectById(accountId);
            accountsApis.add(accountsApi);
        }
        List<AccountsApi> weixinAccount = new ArrayList<AccountsApi>();
        for(AccountsApi accountsApi : accountsApis){
            if(accountsApi.getAccountType() == 3){
                continue;
            }
            weixinAccount.add(accountsApi);
        }
        modelAndView.addObject("accountsApis", weixinAccount);
        modelAndView.addObject("parentId", parentId);
        modelAndView.addObject("accountMenu", accountMenu);
        return modelAndView;
    }

    /*新增保存*/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(ClickEventApi clickEventApi,int accountMenu,MsgTypeBindApi msgTypeBindApi) {
        clickEventApi.setIsvoid(0);
        clickEventApi.setCreatedAt(new Date());
        int id = clickEventAdminService.insertClickEvent(clickEventApi);
        if("bind".equals(clickEventApi.getMsgType())){
            msgTypeBindApi.setCreatedAt(new Date());
            msgTypeBindApi.setIsvoid(0);
            msgTypeBindApi.setMsgTypeId(id);
            msgTypeBindApi.setMsgType("click");
            msgTypeBindAdminService.insertMsgTypeBind(msgTypeBindApi);
        }
        if(clickEventApi.getParentId() == 0){
            if(accountMenu == 0){
//                return "redirect:/clickEvent/index.do?pageNum=1";
                return "redirect:/tree/index.do";
            }else{
                return "redirect:/clickEvent/accountMenu.do?id="+clickEventApi.getAccountId();
            }

        }else{
            return "redirect:/clickEvent/subMenu.do?id=" + clickEventApi.getParentId()+"&accountid="+clickEventApi.getAccountId()
                    +"&accountMenu=" + accountMenu;
        }
    }

    /*修改*/
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int id,@RequestParam int accountMenu) {
        ClickEventApi clickEventApi = clickEventAdminService.selectById(id);
        ModelAndView modelAndView = new ModelAndView("clickevent/edit", "clickEventApi", clickEventApi);
        String msgType = clickEventApi.getMsgType();
        if("text".equals(msgType)){
            List<TextTemplateApi> textTemplateApis = textTemplateAdminService.selectByAccountId(clickEventApi.getAccountId());
            modelAndView.addObject("templateApis", textTemplateApis);
        }else if("multi_article".equals(msgType)){
            List<NewsTemplateApi> newsTemplateApis = newsTemplateAdminService.selectByType(msgType,clickEventApi.getAccountId());
            modelAndView.addObject("templateApis",newsTemplateApis);
        }else if("bind".equals(msgType)){
            List<TextTemplateApi> textTemplateApis = textTemplateAdminService.selectByAccountId(clickEventApi.getAccountId());
            List<NewsTemplateApi> newsTemplateApis = newsTemplateAdminService.selectByType("multi_article", clickEventApi.getAccountId());

            MsgTypeBindApi msgTypeBindApi =  msgTypeBindAdminService.selectByMsgType(clickEventApi.getAccountId(), "click", clickEventApi.getId());
            modelAndView.addObject("msgTypeBindApi",msgTypeBindApi);
            String bindTemplateType = msgTypeBindApi.getBindTemplateType();
            String unbindTemplateType = msgTypeBindApi.getUnbindTemplateType();
            String resetBindTemplateType = msgTypeBindApi.getResetBindTemplateType();
            if("multi_article".equals(bindTemplateType)) {
                modelAndView.addObject("bindTemplateApis",newsTemplateApis );
            }else {
                modelAndView.addObject("bindTemplateApis", textTemplateApis);
            }
            if("multi_article".equals(unbindTemplateType)) {
                modelAndView.addObject("unbindTemplateApis",newsTemplateApis );
            }else {
                modelAndView.addObject("unbindTemplateApis", textTemplateApis);
            }
            if("multi_article".equals(resetBindTemplateType)) {
                modelAndView.addObject("resetTemplateApis",newsTemplateApis );
            }else {
                modelAndView.addObject("resetTemplateApis", textTemplateApis);
            }
            msgTypeBindApi.getUnbindTemplateType();
            msgTypeBindApi.getResetBindTemplateType();
        }

        modelAndView.addObject("accountMenu", accountMenu);
        return modelAndView;
    }

    /*保存修改*/
    @RequestMapping(value = "/editsave", method = RequestMethod.POST)
    public String editSave(ClickEventApi clickEventApi,int accountMenu,MsgTypeBindApi msgTypeBindApi) {
        Date createdAt = clickEventAdminService.selectById(clickEventApi.getId()).getCreatedAt();
        clickEventApi.setCreatedAt(createdAt);
        clickEventAdminService.updateClickEvent(clickEventApi);
//        ClickBindApi tempBindApi = clickBindAdminService.selectByEventId(clickEventApi.getId());
        MsgTypeBindApi tempTypeBindApi = msgTypeBindAdminService.selectByMsgType(clickEventApi.getAccountId(), "click", clickEventApi.getId());
        if("bind".equals(clickEventApi.getMsgType())){
//            clickBindApi.setXmClickEventId(clickEventApi.getId());
            msgTypeBindApi.setMsgTypeId(clickEventApi.getId());
            if(tempTypeBindApi.getId() == null){
                msgTypeBindApi.setCreatedAt(new Date());
                msgTypeBindApi.setIsvoid(0);
                msgTypeBindApi.setMsgType("click");
                msgTypeBindAdminService.insertMsgTypeBind(msgTypeBindApi);
//                clickBindAdminService.insertClickBind(clickBindApi);
            } else {
                msgTypeBindApi.setId(tempTypeBindApi.getId());
//                clickBindApi.setId(tempBindApi.getId());
                msgTypeBindApi.setCreatedAt(tempTypeBindApi.getCreatedAt());
//                clickBindApi.setCreatedAt(tempBindApi.getCreatedAt());
//                clickBindAdminService.updateClickBind(clickBindApi);
                msgTypeBindApi.setMsgType("click");
                msgTypeBindAdminService.updateMsgTypeBind(msgTypeBindApi);
            }
        }else{
            if(tempTypeBindApi.getId() != null){
                msgTypeBindAdminService.deleteById(tempTypeBindApi.getId());
//                clickBindAdminService.deleteById(tempBindApi.getId());
            }
        }
        if(clickEventApi.getParentId() == 0){
            if(accountMenu == 0){
                return "redirect:/clickEvent/index.do?pageNum=1";
            }else{
                return "redirect:/clickEvent/accountMenu.do?id="+clickEventApi.getAccountId();
            }
        }else{
            return "redirect:/clickEvent/subMenu.do?id=" + clickEventApi.getParentId() + "&accountid="+clickEventApi.getAccountId()
                    + "&accountMenu=" + accountMenu;
        }
    }
    /*状态修改*/
    @RequestMapping(value = "/editStatus", method = RequestMethod.GET)
    public String editStatus(@RequestParam int id,@RequestParam int accountMenu) {
        ClickEventApi clickEventApi = clickEventAdminService.selectById(id);
        clickEventApi.setIsvoid(1);
        clickEventAdminService.updateClickEvent(clickEventApi);
        MsgTypeBindApi msgTypeBindApi = msgTypeBindAdminService.selectByMsgType(clickEventApi.getAccountId(), "click", clickEventApi.getId());
//        ClickBindApi clickBindApi = clickBindAdminService.selectByEventId(clickEventApi.getId());
        if(msgTypeBindApi.getId() != null){
//            clickBindAdminService.deleteById(clickBindApi.getId());
            msgTypeBindAdminService.deleteById(msgTypeBindApi.getId());
        }
        if(clickEventApi.getParentId() == 0){
            List<ClickEventApi> clickEventApis = clickEventAdminService.selectByParentId(clickEventApi.getId());
            for(ClickEventApi subClickApi : clickEventApis){
                subClickApi.setIsvoid(1);
                clickEventAdminService.updateClickEvent(subClickApi);
            }
            if(accountMenu == 0){
                return "redirect:/clickEvent/index.do?pageNum=1";
            }else{
                return "redirect:/clickEvent/accountMenu.do?id="+clickEventApi.getAccountId();
            }
        }else{
            return "redirect:/clickEvent/subMenu.do?id=" + clickEventApi.getParentId()+"&accountid="+clickEventApi.getAccountId()
                    + "&accountMenu=" + accountMenu;
        }
    }

//    /*子菜单*/
    @RequestMapping(value = "/subMenu", method = RequestMethod.GET)
    public ModelAndView subMenu(@RequestParam int id,@RequestParam int accountid,@RequestParam int accountMenu) {
        List<MenuAccountApi> menuAccountApis = clickEventAdminService.selectSubMenu(id);
        ModelAndView modelAndView = new ModelAndView("clickevent/subindex");
        modelAndView.addObject("menuAccountApis", menuAccountApis);
        modelAndView.addObject("parentId", id);
        modelAndView.addObject("accountid", accountid);
        modelAndView.addObject("accountMenu",accountMenu);
        return modelAndView;
    }
    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public String generateJson(@RequestParam int accountId) throws Exception {
        AccountsApi accountsApi = accountsAdminService.selectById(accountId);
        String token = tokenAdminService.search(accountsApi.getAppid());
//        System.out.println(token);
        List<MenuAccountApi> menuAccountApis = clickEventAdminService.selectAccountMenu(accountId);
        List<Object> button = new ArrayList<Object>();
        for(MenuAccountApi menuAccountApi : menuAccountApis){
            List<MenuAccountApi> subMenuAccountApis = clickEventAdminService.selectSubMenu(menuAccountApi.getId());
            if(subMenuAccountApis.size() == 0){
                generateModel(menuAccountApi,button);
            }else{
                List<Object> subModel = new ArrayList<Object>();
                SubMenu subMenu = new SubMenu();
                subMenu.setName(menuAccountApi.getName());
                subMenu.setSub_button(subModel);
                for(MenuAccountApi subMenuAccountApi : subMenuAccountApis){
                    generateModel(subMenuAccountApi,subModel);
                }
                button.add(subMenu);
            }
        }
        Map<String,List<Object>> map = new HashMap<String, List<Object>>();
        map.put("button", button);
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + token;
        String jsonStr = JSON.json(map);
//        System.out.println(jsonStr);
        String response = HttpUtils4.doPostBody(httpClient4Manager.getHttpClient(), url, jsonStr, null, "UTF-8");
        JSONObject jsonObject =(JSONObject) JSON.parse(response);
        if("ok".equals(jsonObject.get("errmsg"))){
            return "发布成功";
        }else{
            return "发布失败("+response+")";
        }
    }

    private void generateModel(MenuAccountApi menuAccountApi,List<Object> modelList){
        if("click".equals(menuAccountApi.getEvent())){
            ClickModel clickModel = new ClickModel();
            clickModel.setKey(menuAccountApi.getEventKey());
            clickModel.setName(menuAccountApi.getName());
            clickModel.setType("click");
            modelList.add(clickModel);
        }else{
            ViewModel viewModel = new ViewModel();
            viewModel.setName(menuAccountApi.getName());
            viewModel.setUrl(menuAccountApi.getViewUrl());
            viewModel.setType("view");
            modelList.add(viewModel);
        }
    }

//    /*获取account id对应的菜单*/
    @RequestMapping(value = "/accountMenu", method = RequestMethod.GET)
    public ModelAndView accountMenu(@RequestParam int id) {
        List<MenuAccountApi> menuAccountApis = clickEventAdminService.selectAccountMenu(id);
        setHasChild(menuAccountApis);
        ModelAndView modelAndView = new ModelAndView("clickevent/accountmenu");
        modelAndView.addObject("menuAccountApis", menuAccountApis);
        modelAndView.addObject("accountId", id);
        int accountType = accountsAdminService.selectById(id).getAccountType();
        modelAndView.addObject("accountType",accountType);
        return modelAndView;
    }

    //判断是否有子菜单
    private void setHasChild(List<MenuAccountApi> menuAccountApis){
        for(MenuAccountApi menuAccountApi: menuAccountApis){
            List<MenuAccountApi> menuAccountApis1 = clickEventAdminService.selectSubMenu(menuAccountApi.getId());
            if (menuAccountApis1.size() > 0) {
                menuAccountApi.setHasChild(true);
            } else {
                menuAccountApi.setHasChild(false);
            }
        }
    }

}
