package com.womai.platform.admin.web.controller;

import com.womai.common.utils.HttpUtils4;
import com.womai.common.utils.MultiThreadHttpClient4Manager;
import com.womai.platform.admin.service.impl.AccountsAdminServiceImpl;
import com.womai.platform.admin.service.inte.TokenAdminService;
import com.womai.platform.api.model.AccountsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wlb on 2015/10/22.
 */
@Controller
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private MultiThreadHttpClient4Manager httpClient4Manager;
    @Autowired
    private AccountsAdminServiceImpl accountsAdminService;
    @Autowired
    private TokenAdminService tokenAdminService;

    @RequestMapping(value = "/createGroup",method = RequestMethod.GET)
    @ResponseBody
    public String createGroup(String jsonStr) throws Exception {
        AccountsApi accountsApi = accountsAdminService.selectById(1);
        String token = tokenAdminService.search(accountsApi.getAppid());
        String url = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=" + token;
//        String jsonStr = "{\"group\":{\"name\":\""+ "testGroup" +"\"}}";
        String response = HttpUtils4.doPostBody(httpClient4Manager.getHttpClient(), url, jsonStr, null, "UTF-8");
        return  response;
    }

    @RequestMapping(value = "/index",method = RequestMethod.POST)
    @ResponseBody
    public String index() throws Exception {
        AccountsApi accountsApi = accountsAdminService.selectById(1);
        String token = tokenAdminService.search(accountsApi.getAppid());
        String url = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=" + token;
        String response = HttpUtils4.doGet(url, null, httpClient4Manager.getHttpClient());
        return  response;
    }

    @RequestMapping(value = "/updateGroup",method = RequestMethod.GET)
    @ResponseBody
    public String updateGroup(String jsonStr) throws Exception {
        AccountsApi accountsApi = accountsAdminService.selectById(1);
        String token = tokenAdminService.search(accountsApi.getAppid());
        String url = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=" + token;
//        {"group":{"id":108,"name":"test2_modify2"}}
        String response = HttpUtils4.doPostBody(httpClient4Manager.getHttpClient(), url, jsonStr, null, "UTF-8");
        return  response;
    }

    @RequestMapping(value = "/deleteGroup",method = RequestMethod.GET)
    @ResponseBody
    public String deleteGroup(String jsonStr) throws Exception {
        AccountsApi accountsApi = accountsAdminService.selectById(1);
        String token = tokenAdminService.search(accountsApi.getAppid());
        String url = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=" + token;
//        {"group":{"id":108}}
        String response = HttpUtils4.doPostBody(httpClient4Manager.getHttpClient(), url, jsonStr, null, "UTF-8");
        return  response;
    }

    @RequestMapping(value = "/setUserGroup",method = RequestMethod.GET)
    @ResponseBody
    public String setUserGroup(String jsonStr) throws Exception {
        AccountsApi accountsApi = accountsAdminService.selectById(1);
        String token = tokenAdminService.search(accountsApi.getAppid());
        String url = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=" + token;
        //{"openid":"oDF3iYx0ro3_7jD4HFRDfrjdCM58","to_groupid":108}
        String response = HttpUtils4.doPostBody(httpClient4Manager.getHttpClient(), url, jsonStr, null, "UTF-8");
        return  response;
    }

    @RequestMapping(value = "/batchSetUserGroup",method = RequestMethod.GET)
    @ResponseBody
    public String batchSetUserGroup(String jsonStr) throws Exception {
        AccountsApi accountsApi = accountsAdminService.selectById(1);
        String token = tokenAdminService.search(accountsApi.getAppid());
        String url = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=" + token;
//        {"openid_list":["oDF3iYx0ro3_7jD4HFRDfrjdCM58","oDF3iY9FGSSRHom3B-0w5j4jlEyY"],"to_groupid":108}
        String response = HttpUtils4.doPostBody(httpClient4Manager.getHttpClient(), url, jsonStr, null, "UTF-8");
        return  response;
    }

}
