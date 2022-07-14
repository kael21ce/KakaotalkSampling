package org.techtown.kakaotalksampling;

public class Friend {
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

    public Friend(String name, String stateMessage) {
        this.name = name;
        this.stateMessage = stateMessage;
    }

    String name;
    String stateMessage;
}
