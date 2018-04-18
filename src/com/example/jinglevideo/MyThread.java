package com.example.jinglevideo;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MyThread implements Runnable {
    String filmName;
    Handler handler;
    int id;
    ProgressDialog progressDialog;

    public MyThread(String filmName, Handler handler,
                    ProgressDialog progressDialog, int id) {
        // TODO Auto-generated constructor stub
        this.filmName = filmName;
        this.handler = handler;
        this.progressDialog = progressDialog;
        this.id = id;

    }

    @Override
    public void run() {
        GetPage getPage = new GetPage(this.filmName);
        List<Map<String, String>> linkList = null;
        Message message = handler.obtainMessage();
        switch (this.id) {
            case R.id.list_view_iqiyi:
                try {
                    linkList = getPage.get_iqiyiLinks();

                    message.what = 1;
                    message.arg1 = id;
                    message.obj = linkList;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    // TODO: handle exception
                    Log.e("exception", "网络错误");
                    message.what = 2;
                    handler.sendMessage(message);
                }

                break;
            case R.id.list_view_youku:
                try {
                    linkList = getPage.get_youkuLinks();

                    message.what = 1;
                    message.arg1 = id;
                    message.obj = linkList;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    // TODO: handle exception
                    Log.e("exception", "IO错误");
                    message.what = 2;
                    handler.sendMessage(message);

                }

                break;
            case R.id.list_view_tencent:
                try {
                    linkList = getPage.get_tencentLinks();

                    message.what = 1;
                    message.arg1 = id;
                    message.obj = linkList;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    // TODO: handle exception
                    Log.e("exception", "IO异常");
                    message.what = 2;
                    handler.sendMessage(message);
                }

                break;
            case R.id.list_view_mangguo:
                try {
                    linkList = getPage.get_mangguoLinks();

                    message.what = 1;
                    message.arg1 = id;
                    message.obj = linkList;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    // TODO: handle exception
                    Log.e("exception", "IO异常");
                    message.what = 2;
                    handler.sendMessage(message);
                }
                break;

            case 0:
                try {
                    linkList = getPage.getLink();
                    message.what = 1;
                    message.obj = linkList;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.e("exception", "IO异常");
                    message.what = 2;
                    handler.sendMessage(message);
                }
                break;
            case R.id.list_view_leshi:
                try {
                    linkList = getPage.get_leshiLinks();

                    message.what = 1;
                    message.arg1 = id;
                    message.obj = linkList;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    // TODO: handle exception
                    Log.e("exception", "IO异常");
                    message.what = 2;
                    handler.sendMessage(message);
                }

            default:
                break;

        }
        progressDialog.dismiss();

    }

}
