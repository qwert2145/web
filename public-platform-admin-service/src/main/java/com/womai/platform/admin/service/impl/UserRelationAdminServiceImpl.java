package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.UserRelationAdminService;
import com.womai.platform.api.model.UserRelationApi;
import com.womai.platform.api.service.UserRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wlb on 2015/10/21.
 */
public class UserRelationAdminServiceImpl implements UserRelationAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRelationAdminServiceImpl.class);
    private UserRelationService userRelationSoaService;

    public UserRelationService getUserRelationSoaService() {
        return userRelationSoaService;
    }

    public void setUserRelationSoaService(UserRelationService userRelationSoaService) {
        this.userRelationSoaService = userRelationSoaService;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public int insertUserRelation(UserRelationApi record) {
        return 0;
    }

    @Override
    public UserRelationApi selectById(int id) {
        return null;
    }

    @Override
    public int updateUserRelation(UserRelationApi record) {
        return 0;
    }
}
