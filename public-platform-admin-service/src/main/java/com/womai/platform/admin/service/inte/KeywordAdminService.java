package com.womai.platform.admin.service.inte;


import com.womai.platform.api.model.KeywordApi;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public interface KeywordAdminService {
    void deleteById(int id);
    int insertKeyword(KeywordApi record);
    KeywordApi selectById(int id);
    int updateKeyword(KeywordApi record);
    List<KeywordApi> selectList();
    List<KeywordApi> selectListByPage(HashMap<String, Integer> offsetLimit);
    int getCount();

    List<KeywordApi> query(HashMap<String, Object> queryMap);
    int queryCount(HashMap<String, Object> queryMap);
    KeywordApi getModelByKeyWord(String keyWord,int accountId);
}
