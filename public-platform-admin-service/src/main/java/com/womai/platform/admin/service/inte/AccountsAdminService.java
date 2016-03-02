package com.womai.platform.admin.service.inte;


import com.womai.platform.api.model.AccountsApi;

import java.util.HashMap;
import java.util.List;

public interface AccountsAdminService {
	void deleteById(int id);
	int insertAccounts(AccountsApi record);
	AccountsApi selectById(int id);
	int updateAccounts(AccountsApi record);
	List<AccountsApi> selectList();
	List<Integer> selectIds();
	List<AccountsApi> selectListByPage(HashMap<String,Integer> offsetLimit);

	int getCount();

	List<AccountsApi> query(HashMap<String,Object> queryMap);

	int queryCount(HashMap<String,Object> queryMap);

	AccountsApi selectByAppid(String appid);
}
