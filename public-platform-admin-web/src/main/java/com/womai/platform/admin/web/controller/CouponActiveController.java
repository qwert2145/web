package com.womai.platform.admin.web.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.womai.common.framework.web.url.WebUrl;
import com.womai.platform.admin.service.inte.AccountsAdminService;
import com.womai.platform.admin.service.inte.CouponActiveAdminService;
import com.womai.platform.admin.service.inte.CouponContentAdminService;
import com.womai.platform.admin.service.inte.CouponSendRecordAdminService;
import com.womai.platform.admin.web.Util.DateUtil;
import com.womai.platform.admin.web.Util.ExcelReader;
import com.womai.platform.admin.web.Util.PageUtil;
import com.womai.platform.admin.web.model.PageModel;
import com.womai.platform.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/23.
 */
@Controller
@RequestMapping("/couponActive")
public class CouponActiveController {
    @Autowired
    private CouponActiveAdminService couponActiveAdminService;
    @Autowired
    private AccountsAdminService accountsAdminService;
    @Autowired
    private WebUrl webUrl;
    @Autowired
    private CouponSendRecordAdminService couponSendRecordAdminService;
    @Autowired
    private CouponContentAdminService couponContentAdminService;
    /*列表*/
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index(@RequestParam int pageNum) {
        PageModel pageModel = new PageModel();
        HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
        PageUtil.getParamMap(paramMap, pageNum);
        List<CouponActiveApi> couponActiveApis = couponActiveAdminService.selectListByPage(paramMap);
        ModelAndView modelAndView = new ModelAndView("couponactive/index");
        modelAndView.addObject("couponActiveApis",couponActiveApis);
        int totalNum = couponActiveAdminService.getCount();
        PageUtil.getPageModel(pageModel,pageNum,totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/couponActive/index.do"));

        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        modelAndView.addObject("accountId", 0);
        return modelAndView;
    }

    /*新增*/
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("couponactive/create");
        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        return modelAndView;
    }

    /*新增保存*/
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(CouponActiveApi couponActiveApi,String beginDate,String endDate) {
        couponActiveApi.setIsvoid(0);
        couponActiveApi.setCouponLimitCount(couponActiveApi.getCouponTotal());
        couponActiveApi.setStartTime(DateUtil.getDate(beginDate));
        couponActiveApi.setEndTime(DateUtil.getDate(endDate));
        couponActiveApi.setCreatedAt(new Date());
        couponActiveAdminService.insertCouponActive(couponActiveApi);
        return "redirect:/couponActive/index.do?pageNum=1";
    }

    /*修改*/
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int id,@RequestParam String accountName) {
        ModelAndView modelAndView = new ModelAndView("couponactive/edit");
        CouponActiveApi couponActiveApi = couponActiveAdminService.selectById(id);
        modelAndView.addObject("couponActiveApi", couponActiveApi);
        modelAndView.addObject("accountName", accountName);
        return modelAndView;
    }

    /*保存修改*/
    @RequestMapping(value = "/editsave", method = RequestMethod.POST)
    public String editSave(CouponActiveApi couponActiveApi,String beginDate,String endDate) {
        Date createdAt =couponActiveAdminService.selectById(couponActiveApi.getId()).getCreatedAt();
        couponActiveApi.setCreatedAt(createdAt);
        couponActiveApi.setStartTime(DateUtil.getDate(beginDate));
        couponActiveApi.setEndTime(DateUtil.getDate(endDate));
        couponActiveAdminService.updateCouponActive(couponActiveApi);
        return "redirect:/couponActive/index.do?pageNum=1";
    }

    /*状态修改*/
    @RequestMapping(value = "/editStatus", method = RequestMethod.GET)
    public String editStatus(@RequestParam int id) {
        CouponActiveApi couponActiveApi = couponActiveAdminService.selectById(id);
        couponActiveApi.setIsvoid(1);
        couponActiveAdminService.updateCouponActive(couponActiveApi);
        return "redirect:/couponActive/index.do?pageNum=1";
    }

    /*获取active id*/
    @RequestMapping(value = "/activeId", method = RequestMethod.GET)
    @ResponseBody
    public String selectActiveid(@RequestParam int accountId) {
        StringBuffer sb=new StringBuffer();
        List<CouponActiveApi> couponActiveApis = couponActiveAdminService.selectByAccountId(accountId);
        for(int i=0;i<couponActiveApis.size();i++){
            sb.append(couponActiveApis.get(i).getId()).append("=").append(couponActiveApis.get(i).getName());
            if(i!=couponActiveApis.size()-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }
    /*获取open id*/
    @RequestMapping(value = "/openId", method = RequestMethod.GET)
    @ResponseBody
    public String selectOpenId(@RequestParam int couponActiveId) {
        StringBuffer sb=new StringBuffer();
        List<CouponSendRecordApi> couponSendRecordApis = couponSendRecordAdminService.selectByActiveId(couponActiveId);
        for(int i=0;i<couponSendRecordApis.size();i++){
            sb.append(couponSendRecordApis.get(i).getOpenid());
            if(i!=couponSendRecordApis.size()-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    @RequestMapping(value = "/sendRecordIndex",method = RequestMethod.POST)
    @ResponseBody
    public String sendIndex(String openId,int activeId) throws IOException {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("couponActiveId", activeId);
        paramMap.put("openid", openId);
        List<CouponSendRecordApi> couponSendRecordApis = couponSendRecordAdminService.selectListByPage(paramMap);
        JSON.json(couponSendRecordApis);
        return JSON.json(couponSendRecordApis);
    }
    @RequestMapping(value = "/showIndex", method = RequestMethod.GET)
    public ModelAndView showIndex() {
        ModelAndView modelAndView = new ModelAndView("couponactive/sendRecordIndex");
        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        return modelAndView;
    }

    @RequestMapping(value = "/uploadIndex", method = RequestMethod.GET)
    public ModelAndView upload() {
        ModelAndView modelAndView = new ModelAndView("couponactive/upload");
        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        modelAndView.addObject("size", 0);
        return modelAndView;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadExcel(@RequestParam("excelfile") MultipartFile file,@RequestParam int activeId) throws IOException {
        List<CouponContentApi> list  = ExcelReader.readExcelContent(activeId, file.getInputStream(), file.getOriginalFilename());
        couponContentAdminService.insertBatch(list);
        ModelAndView modelAndView = upload();
        modelAndView.addObject("size",list.size());
        return modelAndView;
    }

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ModelAndView query(@RequestParam int pageNum,int accountId,String name,String keyWord) {
        PageModel pageModel = new PageModel();
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        PageUtil.getObjectParamMap(paramMap, pageNum);
        paramMap.put("accountId", accountId);
        paramMap.put("name", name);
        paramMap.put("keyWord", keyWord);

        List<CouponActiveApi> couponActiveApis = couponActiveAdminService.query(paramMap);
        ModelAndView modelAndView = new ModelAndView("couponactive/index");
        modelAndView.addObject("couponActiveApis",couponActiveApis);
        int totalNum = couponActiveAdminService.queryCount(paramMap);
        PageUtil.getPageModel(pageModel,pageNum,totalNum);
        modelAndView.addObject("pageModel", pageModel);
        modelAndView.addObject("module", webUrl.getTarget("/couponActive/query.do").addQueryData("accountId",accountId).addQueryData("name",name).addQueryData("keyWord",keyWord));

        List<AccountsApi> accountsApis = accountsAdminService.selectList();
        modelAndView.addObject("accountsApis", accountsApis);
        modelAndView.addObject("accountId", accountId);
        modelAndView.addObject("name", name);
        modelAndView.addObject("keyWord", keyWord);
        return modelAndView;
    }


    /*检查keyword是否重复*/
    @RequestMapping(value = "/checkKeyword", method = RequestMethod.POST)
    @ResponseBody
    public String checkKeyword(String keyword,int accountId) {
        List<CouponActiveApi> couponActiveApis  = couponActiveAdminService.selectByKeyWord(keyword, accountId);
        if (couponActiveApis.size() > 0) {
            return "1";
        } else {
            return "0";
        }
    }

    /*检查scenceId是否重复*/
    @RequestMapping(value = "/checkScenceId", method = RequestMethod.POST)
    @ResponseBody
    public String checkScenceId(String scenceId,int accountId) {
        List<CouponActiveApi> couponActiveApis = couponActiveAdminService.selectByScenceId(scenceId, accountId);
        if (couponActiveApis.size() > 0) {
            return "1";
        } else {
            return "0";
        }
    }
}
