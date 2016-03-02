package com.womai.platform.admin.web.model;

import java.util.List;

/**
 * Created by wlb on 2015/11/17.
 */
public class SubMenu {
    String name;
    List<Object> sub_button;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<Object> sub_button) {
        this.sub_button = sub_button;
    }
}
