package com.womai.platform.admin.service.inte;


import com.womai.platform.api.model.ClickBindApi;

/**
 * Created by wlb on 2015/10/21.
 */
public interface ClickBindAdminService {
    void deleteById(int id);
    int insertClickBind(ClickBindApi record);
    ClickBindApi selectById(int id);
    int updateClickBind(ClickBindApi record);

    ClickBindApi selectByEventId(int clickEventId);
}
