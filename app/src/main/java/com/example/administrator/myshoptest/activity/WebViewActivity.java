package com.example.administrator.myshoptest.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myshoptest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 75095
 */
public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.button)
    Button button;

    private WebAppInterface mWebAppInterface;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        webView.loadUrl("file:///android_asset/index.html");
        webView.getSettings().setJavaScriptEnabled(true);
        mWebAppInterface = new WebAppInterface(this);
        webView.addJavascriptInterface(mWebAppInterface, "app");
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        mWebAppInterface.showName("菜鸟窝");
    }

    private class WebAppInterface {
        private Context mContext;

        public WebAppInterface(Context context) {
            this.mContext = context;
        }

        /**
         * 注意这里必须要@JavascriptInterface
         */
        @JavascriptInterface
        public void sayHello(String name) {
            //这里是JS调用android 的接口
            Toast.makeText(mContext, "name=" + name, Toast.LENGTH_SHORT).show();
        }

        public void showName(final String name) {
            //这里是android调用JS 的接口
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:showName('" + name + "')");
                }
            });
        }

    }
}
