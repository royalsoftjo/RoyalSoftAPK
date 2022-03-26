package com.example.royalsoftapk;

import android.webkit.WebView;
import android.webkit.WebViewClient;

class HelloWebViewClient extends WebViewClient {

    public  static String urlstring;
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);

        return true;
    }
}

class JIFace {
    public void print(String data) {
        data =""+data+"";
        System.out.println(data);
        //DO the stuff
    }
}