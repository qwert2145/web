package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.ClickBindAdminService;
import com.womai.platform.api.model.ClickBindApi;
import com.womai.platform.api.service.ClickBindService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wlb on 2015/10/21.
 */
public class ClickBindAdminServiceImpl implements ClickBindAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClickBindAdminServiceImpl.class);
    private ClickBindService clickBindSoaService;

    public ClickBindService getClickBindSoaService() {
        return clickBindSoaService;
    }

    public void setClickBindSoaService(ClickBindService clickBindSoaService) {
        this.clickBindSoaService = clickBindSoaService;
    }

    @Override
    public void deleteById(int id) {
        clickBindSoaService.deleteById(id);
    }

    @Override
    public int insertClickBind(ClickBindApi record) {
        clickBindSoaService.insertClickBind(record);
        return 0;
    }

    @Override
    public ClickBindApi selectById(int id) {
        return null;
    }

    @Override
    public int updateClickBind(ClickBindApi record) {
        clickBindSoaService.updateClickBind(record);
        return 0;
    }

    @Override
    public ClickBindApi selectByEventId(int clickEventId) {
        return clickBindSoaService.selectByEventId(clickEventId);
    }
}
