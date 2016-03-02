package com.womai.platform.admin.service.inte;


import com.womai.platform.api.model.ImageTemplateApi;

import java.util.HashMap;
import java.util.List;

public interface ImageTemplateAdminService {
	void deleteById(int id);
	int insertImageTemplate(ImageTemplateApi record);
	ImageTemplateApi selectById(int id);
	int updateImageTemplate(ImageTemplateApi record);
	List<ImageTemplateApi> selectListByPage(HashMap<String, Integer> offsetLimit);
	List<ImageTemplateApi> selectByAccountId(int accountId);
	int getCount();
	List<ImageTemplateApi> query(HashMap<String, Object> queryMap);
	int queryCount(HashMap<String, Object> queryMap);
}
