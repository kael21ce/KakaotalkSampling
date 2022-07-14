package org.techtown.kakaotalksampling;

public class Chat {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Chat(String name, String last_message, String date) {
        this.name = name;
        this.last_message = last_message;
        this.date = date;
    }

    String name;
    String last_message;
    String date;
}
