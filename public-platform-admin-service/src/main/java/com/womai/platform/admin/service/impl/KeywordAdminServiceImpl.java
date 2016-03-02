package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.KeywordAdminService;
import com.womai.platform.api.model.KeywordApi;
import com.womai.platform.api.service.KeywordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public class KeywordAdminServiceImpl implements KeywordAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeywordAdminServiceImpl.class);
    private KeywordService keywordSoaService;

    public KeywordService getKeywordSoaService() {
        return keywordSoaService;
    }

    public void setKeywordSoaService(KeywordService keywordSoaService) {
        this.keywordSoaService = keywordSoaService;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public int insertKeyword(KeywordApi record) {
        keywordSoaService.insertKeyword(record);
        return 0;
    }

    @Override
    public KeywordApi selectById(int id) {
        return keywordSoaService.selectById(id);
    }

    @Override
    public int updateKeyword(KeywordApi record) {
        keywordSoaService.updateKeyword(record);
        return 0;
    }

    @Override
    public List<KeywordApi> selectList() {
        return keywordSoaService.selectList();
    }

    @Override
    public List<KeywordApi> selectListByPage(HashMap<String, Integer> offsetLimit) {
        return keywordSoaService.selectListByPage(offsetLimit);
    }

    @Override
    public int getCount() {
        return keywordSoaService.getCount();
    }

    @Override
    public List<KeywordApi> query(HashMap<String, Object> queryMap) {
        return keywordSoaService.query(queryMap);
    }

    @Override
    public int queryCount(HashMap<String, Object> queryMap) {
        return keywordSoaService.queryCount(queryMap);
    }

    @Override
    public KeywordApi getModelByKeyWord(String keyWord, int accountId) {
        return keywordSoaService.getModelByKeyWord(keyWord, accountId);
    }
}
