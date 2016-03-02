package com.womai.platform.admin.service.inte;


import com.womai.platform.api.model.ClickEventApi;
import com.womai.platform.api.model.MenuAccountApi;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/10/21.
 */
public interface ClickEventAdminService {
    void deleteById(int id);
    int insertClickEvent(ClickEventApi record);
    ClickEventApi selectById(int id);
    int updateClickEvent(ClickEventApi record);
    List<MenuAccountApi> selectListByPage(HashMap<String,Integer> offsetLimit);

    int getCount();

    List<MenuAccountApi> selectSubMenu(int parentId);

    int getAccountCount(int accountId);
    List<MenuAccountApi> selectAccountMenu(Integer accountId);

    List<ClickEventApi> selectByParentId(int parentId);

    List<MenuAccountApi> getAccount();
}
