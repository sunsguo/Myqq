package com.example.gy.myqq;

/**
 * Created by gy on 2017/1/18.
 */

public class ChatMsgEntity {
    private String name;
    private String text;

    private boolean isConMeg = true;

    public ChatMsgEntity(){}

    public ChatMsgEntity(String name, String text, boolean isConMeg){
        super();
        this.name = name;
        this.isConMeg = isConMeg;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMsgType(boolean isConMeg){
        this.isConMeg = isConMeg;
    }

    public boolean getMsgType(){
        return isConMeg;
    }
}
