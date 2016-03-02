package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.QrcodeAdminService;
import com.womai.platform.api.model.QrcodeApi;
import com.womai.platform.api.service.QrcodeService;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wlb on 2015/11/26.
 */
public class QrcodeAdminServiceImpl implements QrcodeAdminService {
    private QrcodeService qrcodeSoaService;

    public QrcodeService getQrcodeSoaService() {
        return qrcodeSoaService;
    }

    public void setQrcodeSoaService(QrcodeService qrcodeSoaService) {
        this.qrcodeSoaService = qrcodeSoaService;
    }

    @Override
    public void deleteById(int id) {
        qrcodeSoaService.deleteById(id);
    }

    @Override
    public int insertQrcode(QrcodeApi record) {
        qrcodeSoaService.insertQrcode(record);
        return 0;
    }

    @Override
    public QrcodeApi selectById(int id) {
        return qrcodeSoaService.selectById(id);
    }

    @Override
    public int updateQrcode(QrcodeApi record) {
        qrcodeSoaService.updateQrcode(record);
        return 0;
    }

    @Override
    public List<QrcodeApi> selectListByPage(HashMap<String, Integer> offsetLimit) {
        return qrcodeSoaService.selectListByPage(offsetLimit);
    }

    @Override
    public int getCount() {
        return qrcodeSoaService.getCount();
    }

    @Override
    public List<QrcodeApi> query(HashMap<String, Object> queryMap) {
        return qrcodeSoaService.query(queryMap);
    }

    @Override
    public int queryCount(HashMap<String, Object> queryMap) {
        return qrcodeSoaService.queryCount(queryMap);
    }

    @Override
    public QrcodeApi selectByAccountId(int accountId) {
        return qrcodeSoaService.selectByAccountId(accountId);
    }
}
