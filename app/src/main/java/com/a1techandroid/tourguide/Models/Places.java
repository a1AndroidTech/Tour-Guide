package com.a1techandroid.tourguide.Models;

import android.graphics.drawable.Drawable;

public class Places {

    Drawable drawable;
    String name;
    String loc;
    public Places(Drawable drawable, String name, String loc) {
        this.drawable = drawable;
        this.name = name;
        this.loc = loc;
    }


    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
