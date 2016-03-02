package com.womai.platform.admin.web.Util;

import com.womai.platform.admin.web.model.PageModel;

import java.util.HashMap;

/**
 * Created by wlb on 2015/11/3.
 */
public class PageUtil {

    public static void getPageModel(PageModel pageModel,int pageNum,int totalNum){
        int totalPage = totalNum / PageModel.pageSize + ((totalNum % PageModel.pageSize == 0) ? 0 : 1);
        pageModel.setIndex(pageNum);
        if(pageNum != 1){
            pageModel.setPreviousPage(pageModel.getIndex() - 1);
            pageModel.setPreviousPageAvailable(true);
        }else{
            pageModel.setPreviousPage(0);
            pageModel.setPreviousPageAvailable(false);
        }
        if(pageNum < totalPage){
            pageModel.setNextPageAvailable(true);
            pageModel.setNextPage(pageModel.getIndex() + 1);
        }else{
            pageModel.setNextPageAvailable(false);
        }
        pageModel.setTotalItem(totalNum);
        pageModel.setTotalPage(totalPage);
    }
    public static void getParamMap(HashMap<String, Integer> paramMap,int pageNum){
        paramMap.put("offset", (pageNum - 1) * PageModel.pageSize);
        paramMap.put("limit", PageModel.pageSize);
    }

    public static void getObjectParamMap(HashMap<String, Object> paramMap, int pageNum){
        paramMap.put("offset", (pageNum - 1) * PageModel.pageSize);
        paramMap.put("limit", PageModel.pageSize);
    }
}
