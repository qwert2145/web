package com.womai.platform.admin.web.controller;

import com.womai.common.framework.web.url.WebUrl;
import com.womai.platform.admin.service.inte.*;
import com.womai.platform.admin.web.Util.PageUtil;
import com.womai.platform.admin.web.model.PageModel;
import com.womai.platform.api.model.*;
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
@RequestMapping("/keyword")
public class KeyWordController {
    @Autowired
    private KeywordAdminService keywordAdminService;
    @Autowired
    private TextTemplateAdminService textTemplateAdminService;
    @Autowired
    private NewsTemplateAdminService newsTemplateAdminService;
    @Autowired
    private AccountsAdminService accountsAdminService;
    @Autowired
    private ImageTemplateAdminService imageTemplateAdminService;
    @Autowired
    private  TuanAdminService tuanAdminService;

    @Autowired
    WebUrl webUrl;
    /*列表*/
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView Index(@RequestParam int pageNum) {
        PageModel pageModel = new PageModel();
        HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
        PageUtil.getParamMap(paramMap, pageNum);
        List<KeywordApi> keywordApis = keywordAdminService.selectListByPage(paramMap);
        ModelAndView modelAndView = new ModelAndView("keyword/index");
        modelAndView.addObject("keywordApis", keywordApis);
        int totalNum = keywordAdminService.getCount();
        PageUtil.getPageModel(pageModel,pageNum,totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/keyword/index.do"));
        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        modelAndView.addObject("accountId",0);
        return modelAndView;
    }

    /*新增*/
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("keyword/create");
        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        return modelAndView;
    }

    /*新增保存*/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(KeywordApi keywordApi) {
        keywordApi.setIsvoid(0);
        keywordApi.setCreatedAt(new Date());
        keywordAdminService.insertKeyword(keywordApi);
        return "redirect:/keyword/index.do?pageNum=1";
    }

    /*修改*/
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int id,String accountName) {
        ModelAndView modelAndView = new ModelAndView("keyword/edit");
        KeywordApi keywordApi = keywordAdminService.selectById(id);
        modelAndView.addObject("keywordApi",keywordApi);
        String msgType = keywordApi.getMsgType();
        if("text".equals(msgType)){
            List<TextTemplateApi> textTemplateApis = textTemplateAdminService.selectByAccountId(keywordApi.getAccountId());
            modelAndView.addObject("templateApis",textTemplateApis);
        }else if("single_article_nomedia".equals(msgType) || "single_article".equals(msgType) || "multi_article".equals(msgType)){
            List<NewsTemplateApi> newsTemplateApis = newsTemplateAdminService.selectByType(msgType,keywordApi.getAccountId());
            modelAndView.addObject("templateApis",newsTemplateApis);
        }else if("image".equals(msgType)){
            List<ImageTemplateApi> imageTemplateApis = imageTemplateAdminService.selectByAccountId(keywordApi.getAccountId());
            modelAndView.addObject("templateApis",imageTemplateApis);
        }
        modelAndView.addObject("accountName", accountName);

        Map<String, String> typeMap = null;
        if(keywordApi.getAccountId() != 0) {
            typeMap = getMsgTypeMap(keywordApi.getAccountId());
        }else {
            typeMap = new HashMap<String, String>();
        }
        modelAndView.addObject("msgTypeMap", typeMap);

        return modelAndView;
    }

    /*保存修改*/
    @RequestMapping(value = "/editsave", method = RequestMethod.POST)
    public String editSave(KeywordApi keywordApi) {
        Date createdAt = keywordAdminService.selectById(keywordApi.getId()).getCreatedAt();
        keywordApi.setCreatedAt(createdAt);
        keywordAdminService.updateKeyword(keywordApi);
        return "redirect:/keyword/index.do?pageNum=1";
    }
    /*状态修改*/
    @RequestMapping(value = "/editStatus", method = RequestMethod.GET)
    public String editStatus(@RequestParam int id) {
        KeywordApi keywordApi = keywordAdminService.selectById(id);
        keywordApi.setIsvoid(1);
        keywordAdminService.updateKeyword(keywordApi);
        return "redirect:/keyword/index.do?pageNum=1";
    }

    /*获取template*/
    @RequestMapping(value = "/template", method = RequestMethod.GET)
    @ResponseBody
    public String selectTemplate(@RequestParam String msgType,@RequestParam int accountId) {
        StringBuffer sb=new StringBuffer();
        if("text".equals(msgType) || "bind".equals(msgType)){
            List<TextTemplateApi> textTemplateApis = textTemplateAdminService.selectByAccountId(accountId);
            for(int i=0;i<textTemplateApis.size();i++){
                sb.append(textTemplateApis.get(i).getId()).append("=").append(textTemplateApis.get(i).getName());
                if(i!=textTemplateApis.size()-1){
                    sb.append(",");
                }
            }
        }else if("single_article_nomedia".equals(msgType) || "single_article".equals(msgType) || "multi_article".equals(msgType)){
            List<NewsTemplateApi> newsTemplateApis = newsTemplateAdminService.selectByType(msgType,accountId);
            for(int i=0;i<newsTemplateApis.size();i++){
                sb.append(newsTemplateApis.get(i).getId()).append("=").append(newsTemplateApis.get(i).getName());
                if(i!=newsTemplateApis.size()-1){
                    sb.append(",");
                }
            }
        }else if("image".equals(msgType)){
            List<ImageTemplateApi> imageTemplateApis = imageTemplateAdminService.selectByAccountId(accountId);
            for(int i=0;i<imageTemplateApis.size();i++){
                sb.append(imageTemplateApis.get(i).getId()).append("=").append(imageTemplateApis.get(i).getName());
                if(i!=imageTemplateApis.size()-1){
                    sb.append(",");
                }
            }
            sb.append("=");
        }else{
            sb.append("=");
        }
        return sb.toString();
    }
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ModelAndView query(@RequestParam int pageNum,int accountId,String msgType,String keyWord) {
        PageModel pageModel = new PageModel();
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        PageUtil.getObjectParamMap(paramMap, pageNum);
        paramMap.put("accountId", accountId);
        paramMap.put("msgType",msgType);
        paramMap.put("keyWord", keyWord);

        List<KeywordApi> keywordApis = keywordAdminService.query(paramMap);
        ModelAndView modelAndView = new ModelAndView("keyword/index");
        modelAndView.addObject("keywordApis", keywordApis);
        int totalNum = keywordAdminService.queryCount(paramMap);
        PageUtil.getPageModel(pageModel,pageNum,totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/keyword/query.do").addQueryData("accountId",accountId).addQueryData("msgType",msgType).addQueryData("keyWord",keyWord));

        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        modelAndView.addObject("accountId",accountId);
        modelAndView.addObject("msgType", msgType);
        modelAndView.addObject("keyWord", keyWord);

        Map<String, String> typeMap = null;
        if(accountId != 0) {
            typeMap = getMsgTypeMap(accountId);
        }else {
            typeMap = new HashMap<String, String>();
        }
        modelAndView.addObject("msgTypeMap", typeMap);
        return modelAndView;
    }

    /*获取template*/
    @RequestMapping(value = "/msgtype", method = RequestMethod.GET)
    @ResponseBody
    public String selectMsgType(@RequestParam int accountId,@RequestParam String name) {
        StringBuffer sb=new StringBuffer();
        AccountsApi accountsApi = accountsAdminService.selectById(accountId);
        int accountType = accountsApi.getAccountType();
        if(accountType == 3){
            sb.append("single_article_nomedia=无图,single_article=单图");
        }else if(accountType == 1 || accountType == 2){
            if("keyword".equals(name)){
                sb.append("text=文本,multi_article=图文,card=卡券,image=图片");
            }else if("newstemplate".equals(name)){
                sb.append("multi_article=图文");
            }

        }else {
            sb.append("=");
        }
        return sb.toString();
    }

    /*检查keyword是否重复*/
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public String checkKeyword(String keyword,int accountId) {
        KeywordApi keywordApi = keywordAdminService.getModelByKeyWord(keyword, accountId);
        if (keywordApi.getKeyWord() != null) {
            return "1";
        } else {
            return "0";
        }
    }

    public Map getMsgTypeMap(int accountId){
        Map<String, String> typeMap = new HashMap<String, String>();
        AccountsApi accountsApi = accountsAdminService.selectById(accountId);
        int accountType = accountsApi.getAccountType();
        //accountType为3是小米账号,1或2位微信账号
        if(accountType == 3){
            typeMap.put("single_article_nomedia", "无图");
            typeMap.put("single_article", "单图");
        }else if(accountType == 1 || accountType == 2){
            typeMap.put("multi_article", "图文");
            typeMap.put("text", "文本");
            typeMap.put("card", "卡券");
            typeMap.put("image", "图片");
        }
        return typeMap;
    }


    /*修改*/
    @RequestMapping(value = "/editTuan", method = RequestMethod.GET)
    public ModelAndView editTuan(@RequestParam String keyword,int accountid) {
        ModelAndView modelAndView = new ModelAndView("tuan/edit");
        TuanActiveApi tuanActiveApi =tuanAdminService.selectByKeyWord(accountid,keyword);
        modelAndView.addObject("tuanActiveApi",tuanActiveApi);
        return modelAndView;
    }

    /*保存修改*/
    @RequestMapping(value = "/editTuanSave", method = RequestMethod.POST)
    public String editTuanSave(TuanActiveApi tuanActiveApi) {
        tuanAdminService.update(tuanActiveApi);
        return "redirect:/keyword/index.do?pageNum=1";
    }
}
