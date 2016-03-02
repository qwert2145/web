package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.AccountsAdminService;
import com.womai.platform.api.model.AccountsApi;
import com.womai.platform.api.service.AccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public class AccountsAdminServiceImpl implements AccountsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsAdminServiceImpl.class);
    private AccountsService accountsSoaService;

    public AccountsService getAccountsSoaService() {
        return accountsSoaService;
    }

    public void setAccountsSoaService(AccountsService accountsSoaService) {
        this.accountsSoaService = accountsSoaService;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public int insertAccounts(AccountsApi record) {
        accountsSoaService.insertAccounts(record);
        return 0;
    }

    @Override
    public AccountsApi selectById(int id) {
        return accountsSoaService.selectById(id);
    }

    @Override
    public int updateAccounts(AccountsApi record) {
        accountsSoaService.updateAccounts(record);
        return 0;
    }

    @Override
    public List<AccountsApi> selectList() {
        return accountsSoaService.selectList();
    }

    @Override
    public List<Integer> selectIds() {
        return accountsSoaService.selectIds();
    }

    @Override
    public List<AccountsApi> selectListByPage(HashMap<String, Integer> offsetLimit) {
        return accountsSoaService.selectListByPage(offsetLimit);
    }

    @Override
    public int getCount() {
        return accountsSoaService.getCount();
    }

    @Override
    public List<AccountsApi> query(HashMap<String, Object> queryMap) {
        return accountsSoaService.query(queryMap);
    }

    @Override
    public int queryCount(HashMap<String, Object> queryMap) {
        return accountsSoaService.queryCount(queryMap);
    }

    @Override
    public AccountsApi selectByAppid(String appid) {
        return accountsSoaService.selectByAppid(appid);
    }
}
