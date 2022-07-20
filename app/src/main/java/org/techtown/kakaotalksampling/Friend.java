package org.techtown.kakaotalksampling;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Friend {
    public Friend(String name, String stateMessage, String mobile) {
        this.name = name;
        this.stateMessage = stateMessage;
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
}
