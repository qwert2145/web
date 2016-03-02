package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.ClickEventAdminService;
import com.womai.platform.api.model.ClickEventApi;
import com.womai.platform.api.model.MenuAccountApi;
import com.womai.platform.api.service.ClickEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public class ClickEventAdminServiceImpl implements ClickEventAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClickEventAdminServiceImpl.class);
    private ClickEventService clickEventSoaService;

    public ClickEventService getClickEventSoaService() {
        return clickEventSoaService;
    }

    public void setClickEventSoaService(ClickEventService clickEventSoaService) {
        this.clickEventSoaService = clickEventSoaService;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public int insertClickEvent(ClickEventApi record) {
        return clickEventSoaService.insertClickEvent(record);
    }

    @Override
    public ClickEventApi selectById(int id) {
        return clickEventSoaService.selectById(id);
    }

    @Override
    public int updateClickEvent(ClickEventApi record) {
        clickEventSoaService.updateClickEvent(record);
        return 0;
    }

    @Override
    public List<MenuAccountApi> selectListByPage(HashMap<String, Integer> offsetLimit) {
        return clickEventSoaService.selectListByPage(offsetLimit);
    }

    @Override
    public int getCount() {
        return clickEventSoaService.getCount();
    }

    @Override
    public List<MenuAccountApi> selectSubMenu(int parentId) {
        return clickEventSoaService.selectSubMenu(parentId);
    }

    @Override
    public int getAccountCount(int accountId) {
        return clickEventSoaService.getAccountCount(accountId);
    }

    @Override
    public List<MenuAccountApi> selectAccountMenu(Integer accountId) {
        return clickEventSoaService.selectAccountMenu(accountId);
    }

    @Override
    public List<ClickEventApi> selectByParentId(int parentId) {
        return clickEventSoaService.selectByParentId(parentId);
    }

    @Override
    public List<MenuAccountApi> getAccount() {
        return clickEventSoaService.getAccount();
    }
}
