package com.womai.platform.admin.web.model;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by wlb on 2015/10/30.
 */
public class PageModel {
    public static int pageSize = 0;
    static{
        Properties prop = new Properties();
        try {
            prop.load(PageModel.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
//            LOGGER.error(e.getMessage());
        }

        pageSize = Integer.parseInt(prop.getProperty("pageSize"));
    }
    private int totalItem;
    private int totalPage;
    private boolean previousPageAvailable;
    private int previousPage;
    private int index;
    private boolean nextPageAvailable;
    private int nextPage;

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isPreviousPageAvailable() {
        return previousPageAvailable;
    }

    public void setPreviousPageAvailable(boolean previousPageAvailable) {
        this.previousPageAvailable = previousPageAvailable;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isNextPageAvailable() {
        return nextPageAvailable;
    }

    public void setNextPageAvailable(boolean nextPageAvailable) {
        this.nextPageAvailable = nextPageAvailable;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
}