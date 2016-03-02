package com.womai.platform.admin.service.inte;

import com.womai.platform.api.model.TuanActiveApi;

/**
 * Created by xiaowu on 2016/1/29.
 * 团活动信息修改
 */
public interface TuanAdminService {
    boolean update(TuanActiveApi tuanActiveApi);
    TuanActiveApi selectByKeyWord(Integer accountId,String keyWord);
}
