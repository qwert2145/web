package com.womai.platform.admin.service.inte;


import com.womai.platform.api.model.MsgTypeBindApi;

public interface MsgTypeBindAdminService {
	void deleteById(int id);
	int insertMsgTypeBind(MsgTypeBindApi record);
	MsgTypeBindApi selectById(int id);
	int updateMsgTypeBind(MsgTypeBindApi record);
	MsgTypeBindApi selectByMsgType(Integer accountId,String msgType,int msgTypeId);
}
