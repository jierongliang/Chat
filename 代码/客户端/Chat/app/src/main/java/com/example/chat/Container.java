package com.example.chat;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.HashMap;

public class Container extends LitePalSupport {
    private HashMap< String, ArrayList<String>> data;

    public Container(HashMap<String, ArrayList<String>> data) {
        this.data = data;
    }

    public Container() {
    }

    public HashMap<String, ArrayList<String>> getData() {
        return data;
    }

    public void setData(HashMap<String, ArrayList<String>> data) {
        this.data = data;
    }
}
