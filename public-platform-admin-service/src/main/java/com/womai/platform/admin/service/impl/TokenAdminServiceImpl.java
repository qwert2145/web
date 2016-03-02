package com.womai.platform.admin.service.impl;

import com.womai.platform.admin.service.inte.TokenAdminService;
import m.womai.backend.token.api.service.TokenService;

/**
 * Created by wlb on 2015/11/17.
 */
public class TokenAdminServiceImpl implements TokenAdminService {
    private TokenService tokenSoaService;

    public TokenService getTokenSoaService() {
        return tokenSoaService;
    }

    public void setTokenSoaService(TokenService tokenSoaService) {
        this.tokenSoaService = tokenSoaService;
    }

    @Override
    public String search(String appid) throws Exception {
        return tokenSoaService.searchToken(appid);
    }
}
