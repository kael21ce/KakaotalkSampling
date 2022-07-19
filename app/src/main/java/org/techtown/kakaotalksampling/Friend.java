package org.techtown.kakaotalksampling;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Friend {
    public Friend(String name, String stateMessage, String mobile, Bitmap profile) {
        this.name = name;
        this.stateMessage = stateMessage;
        this.mobile = mobile;
        this.profile = profile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Bitmap getProfile() {
        return profile;
    }

    public void setProfile(Bitmap profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStateMessage() {
        return stateMessage;
    }

    public void setStateMessage(String stateMessage) {
        this.stateMessage = stateMessage;
    }

    String name;
    String stateMessage;
    String mobile;
    Bitmap profile;
}
