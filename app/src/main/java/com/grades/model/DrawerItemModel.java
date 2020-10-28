package com.grades.model;

import android.graphics.drawable.Drawable;

/**
 * Created by hupptechnologies on 19/1/17.
 */

public class DrawerItemModel {

    private String title;
    private Drawable icon;
    private String secondTexr;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getSecondTexr() {
        return secondTexr;
    }

    public void setSecondTexr(String secondTexr) {
        this.secondTexr = secondTexr;
    }
}
