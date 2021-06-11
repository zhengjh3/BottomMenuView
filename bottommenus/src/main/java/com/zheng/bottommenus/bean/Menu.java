package com.zheng.bottommenus.bean;

/**
 * author: zhengjh
 * desc:菜单实体
 * date:2021/6/7
 */
public class Menu {
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单图标
     */
    private int icon;

    public Menu(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
