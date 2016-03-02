package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.TuanAdminService;
import com.womai.platform.api.model.TuanActiveApi;
import com.womai.platform.api.service.TuanActiveService;

/**
 * Created by xiaowu on 2016/1/29.
 */
public class TuanAdminServiceImpl implements TuanAdminService {

    private com.womai.platform.api.service.TuanActiveService tuanActiveSoaService;

    public TuanActiveService getTuanActiveSoaService() {
        return tuanActiveSoaService;
    }

    public void setTuanActiveSoaService(TuanActiveService tuanActiveSoaService) {
        this.tuanActiveSoaService = tuanActiveSoaService;
    }


    @Override
    public boolean update(TuanActiveApi tuanActiveApi) {
        return tuanActiveSoaService.update(tuanActiveApi);
    }

    @Override
    public TuanActiveApi selectByKeyWord(Integer accountId, String keyWord) {
        return tuanActiveSoaService.selectByKeyWord(accountId, keyWord);
    }
}
