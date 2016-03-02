package com.womai.platform.admin.web.model;

import java.util.List;

/**
 * Created by wlb on 2016/1/12.
 */
public class TreeModel {
    String text;
    String href;
    List<String> tags;
    List<TreeModel> nodes;

    public List<TreeModel> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeModel> nodes) {
        this.nodes = nodes;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
