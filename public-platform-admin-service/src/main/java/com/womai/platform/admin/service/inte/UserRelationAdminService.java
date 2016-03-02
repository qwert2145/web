package com.womai.platform.admin.service.inte;


import com.womai.platform.api.model.UserRelationApi;

/**
 * Created by wlb on 2015/10/21.
 */
public interface UserRelationAdminService {
    void deleteById(int id);
    int insertUserRelation(UserRelationApi record);
    UserRelationApi selectById(int id);
    int updateUserRelation(UserRelationApi record);
}
