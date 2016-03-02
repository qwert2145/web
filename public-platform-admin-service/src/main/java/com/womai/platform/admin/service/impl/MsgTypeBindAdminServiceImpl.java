package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.MsgTypeBindAdminService;
import com.womai.platform.api.model.MsgTypeBindApi;
import com.womai.platform.api.service.MsgTypeBindService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wlb on 2016/1/14.
 */
public class MsgTypeBindAdminServiceImpl implements MsgTypeBindAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgTypeBindAdminServiceImpl.class);
    private MsgTypeBindService msgTypeBindSoaService;

    public MsgTypeBindService getMsgTypeBindSoaService() {
        return msgTypeBindSoaService;
    }

    public void setMsgTypeBindSoaService(MsgTypeBindService msgTypeBindSoaService) {
        this.msgTypeBindSoaService = msgTypeBindSoaService;
    }

    @Override
    public void deleteById(int id) {
        msgTypeBindSoaService.deleteById(id);
    }

    @Override
    public int insertMsgTypeBind(MsgTypeBindApi record) {
        msgTypeBindSoaService.insertMsgTypeBind(record);
        return 0;
    }

    @Override
    public MsgTypeBindApi selectById(int id) {
        return msgTypeBindSoaService.selectById(id);
    }

    @Override
    public int updateMsgTypeBind(MsgTypeBindApi record) {
        return msgTypeBindSoaService.updateMsgTypeBind(record);
    }

    @Override
    public MsgTypeBindApi selectByMsgType(Integer accountId, String msgType, int msgTypeId) {
        return msgTypeBindSoaService.selectByMsgType(accountId, msgType, msgTypeId);
    }
}
