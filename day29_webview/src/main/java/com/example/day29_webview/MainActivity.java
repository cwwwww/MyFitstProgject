package com.example.day29_webview;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends AppCompatActivity {

    //进度条对象
    ProgressBar pb;

    //网页控件对象
    WebView webView;

    //下拉刷新控件
    PtrFrameLayout refreash;

    //下拉控件下面的子控件，即线性布局
    LinearLayout line_layout;

    //定义一个网址集合
    String urls[] = {"http://www.baidu.com",
            "http://www.163.com",
            "http://www.sohu.com",
            "http://www.7k7k.com"};

    //加载网址的下标
    int page = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件对象
        setupViews();
        //设置网页
        setWebView();
        //设置下拉回调函数
        setupRefreash();

    }

    private void setupRefreash() {

        //因为是子控件的下拉刷新所以需要重写该方法

        refreash.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                Log.i("TAG", "下拉有没有！！！");
                page++;
                webView.loadUrl(urls[page % urls.length]);
                //结束刷新
                refreash.refreshComplete();
            }

            //线性布局可下拉
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                //返回下拉控件中的子控件可以下拉
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, line_layout, header);
            }
        });

    }

    private void setWebView() {
        //加载网页
        //设置网页
        webView.getSettings().setJavaScriptEnabled(true);
        //监听网页进度
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb.setProgress(newProgress);
            }
        });

        //对网页布局出现到完成进行监听
        webView.setWebViewClient(new WebViewClient() {
            //网页开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pb.setVisibility(View.VISIBLE);


            }

            //网页加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb.setVisibility(View.GONE);

            }
        });
        //加载网页地址
        webView.loadUrl(urls[0]);
    }

    private void setupViews() {
        webView = (WebView) findViewById(R.id.webview);
        pb = (ProgressBar) findViewById(R.id.pb);
        refreash = (PtrFrameLayout) findViewById(R.id.fragment_rotate_header_with_text_view_frame);
        line_layout = (LinearLayout) findViewById(R.id.line_layout);
    }
}
