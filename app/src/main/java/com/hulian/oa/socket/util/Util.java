package com.hulian.oa.socket.util;

import android.content.Context;
import android.widget.Toast;

public class Util {
//    public static final String ws = "ws://192.168.0.129:8081/websocket/";//websocket测试地址
    public static final String ws = "ws://192.168.0.129:80/websocket/";//websocket测试地址

    public static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
}
