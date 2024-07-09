package com.ethan;

import com.ethan.mvc.MyMvc;

public class Main {

    public static void main(String[] args) {
        MyMvc.execution("", "");
        MyMvc.execution("test", "");
        MyMvc.execution("test", "hello");
    }
}
