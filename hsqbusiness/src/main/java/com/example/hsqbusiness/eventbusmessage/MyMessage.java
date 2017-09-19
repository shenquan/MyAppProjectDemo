package com.example.hsqbusiness.eventbusmessage;

/**
 * Created by sqhan on 2017/8/30.
 */

public class MyMessage {
    public String text;
    public int type = 0;

    public MyMessage(int type, String text) {
        this.type = type;
        this.text = text;
    }
}
