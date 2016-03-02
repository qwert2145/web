package com.womai.platform.admin.web.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.womai.platform.admin.service.inte.AccountsAdminService;
import com.womai.platform.admin.service.inte.ClickEventAdminService;
import com.womai.platform.admin.web.model.TreeModel;
import com.womai.platform.api.model.MenuAccountApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlb on 2015/10/22.
 */
@Controller
@RequestMapping("/tree")
public class TreeController {
    @Autowired
    private ClickEventAdminService clickEventAdminService;
    @Autowired
    private AccountsAdminService accountsAdminService;

    /*列表*/
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView Index() {
        ModelAndView modelAndView = new ModelAndView("clickevent/tree");
        return modelAndView;
    }
    //获取accontId和accountName
    @RequestMapping(value = "/getAccount", method = RequestMethod.POST)
    @ResponseBody
    public String getAccounts() throws IOException {
        List<MenuAccountApi> accountMenuApis = clickEventAdminService.getAccount();

        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for(MenuAccountApi accountMenuApi :accountMenuApis){
            TreeModel accountModel = new TreeModel();
            //用户菜单
            accountModel.setText(accountMenuApi.getAccountName());
            List<String> accountTags = new ArrayList<String>();
            accountTags.add("<a href=\"/clickEvent/json.do?accountId="+accountMenuApi.getAccountId()+ "\"><font color=\"121212\">发布</font></a>");
            accountModel.setTags(accountTags);
            accountModel.setHref("/clickEvent/accountMenu.do?id=" + accountMenuApi.getAccountId());
            //父菜单
            List<TreeModel> menuNodes = new ArrayList<TreeModel>();
            //父菜单
            List<MenuAccountApi> menuApis = accountMenu(accountMenuApi.getAccountId());
            if(menuApis.size() != 0){
                accountModel.setNodes(menuNodes);
                for(MenuAccountApi menuApi : menuApis){
                    TreeModel menuModel = new TreeModel();
                    menuModel.setText(menuApi.getName());
//                    List<String> menuTags = new ArrayList<String>();
//                    menuTags.add(menuApi.getId() + "");
//                    menuModel.setTags(menuTags);
//                    menuModel.setHref("");
                    menuNodes.add(menuModel);
                    //子菜单
                    List<TreeModel> subMenuNodes = new ArrayList<TreeModel>();
                    //子菜单
                    List<MenuAccountApi> subMenuApis = clickEventAdminService.selectSubMenu(menuApi.getId());
                    if(subMenuApis.size() != 0){
                        menuModel.setNodes(subMenuNodes);
                        for(MenuAccountApi subMenu :subMenuApis){
                            TreeModel subMenuModel = new TreeModel();
                            subMenuModel.setText(subMenu.getName());
//                            List<String> subMenuTags = new ArrayList<String>();
//                            subMenuTags.add(subMenu.getId() + "");
//                            subMenuModel.setTags(subMenuTags);
                            subMenuNodes.add(subMenuModel);
                        }
                    }
                }
            }
            treeModels.add(accountModel);
        }
//        String json = JSON.json(treeModels);
//        System.out.println(json);
        return JSON.json(treeModels);
    }

    public List<MenuAccountApi> accountMenu(int id) {
        List<MenuAccountApi> menuAccountApis = clickEventAdminService.selectAccountMenu(id);
        return menuAccountApis;
    }

}
