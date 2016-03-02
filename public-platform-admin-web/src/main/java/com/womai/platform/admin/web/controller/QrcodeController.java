package com.womai.platform.admin.web.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.womai.common.framework.web.url.WebUrl;
import com.womai.common.utils.HttpUtils4;
import com.womai.common.utils.MultiThreadHttpClient4Manager;
import com.womai.platform.admin.service.impl.AccountsAdminServiceImpl;
import com.womai.platform.admin.service.inte.NewsTemplateAdminService;
import com.womai.platform.admin.service.inte.QrcodeAdminService;
import com.womai.platform.admin.service.inte.TextTemplateAdminService;
import com.womai.platform.admin.service.inte.TokenAdminService;
import com.womai.platform.admin.web.Util.PageUtil;
import com.womai.platform.admin.web.model.PageModel;
import com.womai.platform.api.model.AccountsApi;
import com.womai.platform.api.model.NewsTemplateApi;
import com.womai.platform.api.model.QrcodeApi;
import com.womai.platform.api.model.TextTemplateApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by wlb on 2015/10/22.
 */
@Controller
@RequestMapping("/qrcode")
public class QrcodeController {
    @Autowired
    private QrcodeAdminService qrcodeAdminService;
    @Autowired
    private AccountsAdminServiceImpl accountsAdminService;
    @Autowired
    private TokenAdminService tokenAdminService;
    @Autowired
    private MultiThreadHttpClient4Manager httpClient4Manager;
    @Autowired
    private WebUrl webUrl;
    @Autowired
    private TextTemplateAdminService textTemplateAdminService;
    @Autowired
    private NewsTemplateAdminService newsTemplateAdminService;

    /*列表*/
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView Index(@RequestParam int pageNum) {
        PageModel pageModel = new PageModel();
        HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
        PageUtil.getParamMap(paramMap, pageNum);
        List<QrcodeApi> qrcodeApis = qrcodeAdminService.selectListByPage(paramMap);
        ModelAndView modelAndView = new ModelAndView("qrcode/index");
        modelAndView.addObject("qrcodeApis", qrcodeApis);

        int totalNum = qrcodeAdminService.getCount();
        PageUtil.getPageModel(pageModel, pageNum, totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/qrcode/index.do"));
        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        List<AccountsApi> weixinAccount = new ArrayList<AccountsApi>();
        for(AccountsApi accountsApi : accountsApis){
            if(accountsApi.getAccountType() == 3){
                continue;
            }
            weixinAccount.add(accountsApi);
        }
        modelAndView.addObject("accountsApis", weixinAccount);
        modelAndView.addObject("accountId", 0);
        return modelAndView;
    }

    /*新增*/
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("qrcode/create");
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

    /*新增保存*/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(QrcodeApi qrcodeApi) {
        qrcodeApi.setIsvoid(0);
        qrcodeApi.setCreatedAt(new Date());
        qrcodeApi.setActionName("QR_LIMIT_SCENE");
        QrcodeApi qrcodeApi1 = qrcodeAdminService.selectByAccountId(qrcodeApi.getAccountId());
        if(qrcodeApi1.getSceneValue() != null){
            int sceneValue = Integer.valueOf(qrcodeApi1.getSceneValue());
            qrcodeApi.setSceneValue(String.valueOf(sceneValue -1));
        }else{
            qrcodeApi.setSceneValue("100000");
        }
        qrcodeAdminService.insertQrcode(qrcodeApi);
        return "redirect:/qrcode/index.do?pageNum=1";
    }

    /*修改*/
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int id,String accountName) {
        ModelAndView modelAndView = new ModelAndView("qrcode/edit");
        QrcodeApi qrcodeApi = qrcodeAdminService.selectById(id);
        modelAndView.addObject("qrcodeApi", qrcodeApi);
        String msgType = qrcodeApi.getMsgType();
        if("text".equals(msgType)){
            List<TextTemplateApi> textTemplateApis = textTemplateAdminService.selectByAccountId(qrcodeApi.getAccountId());
            modelAndView.addObject("templateApis",textTemplateApis);
        }else{
            List<NewsTemplateApi> newsTemplateApis = newsTemplateAdminService.selectByType(msgType,qrcodeApi.getAccountId());
            modelAndView.addObject("templateApis",newsTemplateApis);
        }
        modelAndView.addObject("accountName", accountName);
        return modelAndView;
    }

    /*保存修改*/
    @RequestMapping(value = "/editsave", method = RequestMethod.POST)
    public String editSave(QrcodeApi qrcodeApi) {
        Date createdAt = qrcodeAdminService.selectById(qrcodeApi.getId()).getCreatedAt();
        qrcodeApi.setCreatedAt(createdAt);
        qrcodeAdminService.updateQrcode(qrcodeApi);
        return "redirect:/qrcode/index.do?pageNum=1";
    }
    /*状态修改*/
    @RequestMapping(value = "/editStatus", method = RequestMethod.GET)
    public String editStatus(@RequestParam int id) {
        QrcodeApi qrcodeApi = qrcodeAdminService.selectById(id);
        qrcodeApi.setIsvoid(1);
        qrcodeAdminService.updateQrcode(qrcodeApi);
        return "redirect:/qrcode/index.do?pageNum=1";
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    public void generateJson(@RequestParam int id,HttpServletResponse servletResponse) throws Exception {
        QrcodeApi qrcodeApi = qrcodeAdminService.selectById(id);
        AccountsApi accountsApi = accountsAdminService.selectById(qrcodeApi.getAccountId());
        String token = tokenAdminService.search(accountsApi.getAppid());
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
        String jsonStr = "";
        if("QR_LIMIT_SCENE".equals(qrcodeApi.getActionName())){
            jsonStr = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\":" + qrcodeApi.getSceneValue() +"}}}";
        }else if("QR_LIMIT_STR_SCENE".equals(qrcodeApi.getActionName())){
            jsonStr = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+qrcodeApi.getSceneValue()+"\"}}}";
        }else{
            jsonStr = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\":"+qrcodeApi.getSceneValue()+"}}}";
        }
//        System.out.println("jsonStr:" + jsonStr);
        String response = HttpUtils4.doPostBody(httpClient4Manager.getHttpClient(), url, jsonStr, null, "UTF-8");
//        System.out.println("response:" + response);
        Map jsonMap = JSON.parse(response, Map.class);
        String ticket =(String)jsonMap.get("ticket");
        String ticketUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;
        byte[] imgBytes = HttpUtils4.doGetByte(ticketUrl, null, httpClient4Manager.getHttpClient());
        servletResponse.setContentType("image/jpg"); //设置返回的文件类型
        OutputStream os = servletResponse.getOutputStream();
        os.write(imgBytes);
        os.flush();
        os.close();
    }
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ModelAndView query(@RequestParam int pageNum, int accountId,String codeName,String codeType,String sceneValue) {
        PageModel pageModel = new PageModel();
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        PageUtil.getObjectParamMap(paramMap, pageNum);
        paramMap.put("accountId", accountId);
        paramMap.put("codeName", codeName);
        paramMap.put("codeType", codeType);
        paramMap.put("sceneValue", sceneValue);
        List<QrcodeApi> qrcodeApis = qrcodeAdminService.query(paramMap);
        ModelAndView modelAndView = new ModelAndView("qrcode/index");
        modelAndView.addObject("qrcodeApis", qrcodeApis);

        int totalNum = qrcodeAdminService.queryCount(paramMap);
        PageUtil.getPageModel(pageModel, pageNum, totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/qrcode/query.do").addQueryData("accountId",accountId).addQueryData("codeName",codeName).addQueryData("codeType",codeType).addQueryData("sceneValue",sceneValue));
        List<AccountsApi> accountsApis = accountsAdminService.selectList();

        List<AccountsApi> weixinAccount = new ArrayList<AccountsApi>();
        for(AccountsApi accountsApi : accountsApis){
            if(accountsApi.getAccountType() == 3){
                continue;
            }
            weixinAccount.add(accountsApi);
        }
        modelAndView.addObject("accountsApis", weixinAccount);

        modelAndView.addObject("accountId", accountId);
        modelAndView.addObject("codeName", codeName);
        modelAndView.addObject("codeType", codeType);
        modelAndView.addObject("sceneValue", sceneValue);
        return modelAndView;
    }
}
