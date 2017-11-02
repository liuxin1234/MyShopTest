package com.example.administrator.myshoptest.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.myshoptest.R;
import com.example.administrator.myshoptest.bean.Wares;
import com.example.administrator.myshoptest.httpUtils.Contants;
import com.example.administrator.myshoptest.utils.CartProvider;
import com.example.administrator.myshoptest.utils.ToastUtils;
import com.example.administrator.myshoptest.widget.CnToolbar;
import com.liux.sharesdk.onekeyshare.OnekeyShare;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

/**
 * @author 75095
 */
public class WareDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ware_toolbar)
    CnToolbar wareToolbar;
    @BindView(R.id.webView)
    WebView webView;

    private Wares mWares;
    private WebAppInterface mWebAppInterface;

    private CartProvider mCartProvider;

    private SpotsDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_detail);
        ButterKnife.bind(this);

        Serializable serializable = getIntent().getSerializableExtra(Contants.WARE);
        if (serializable == null){
            this.finish();
        }
        mDialog = new SpotsDialog(this,"loading......");
        mDialog.show();
        mWares = (Wares) serializable;
        mCartProvider = new CartProvider(this);
        initToolbar();
        initWebView();
    }

    private void initToolbar(){
        wareToolbar.setNavigationOnClickListener(this);
        wareToolbar.setTitle("商品详情");
        wareToolbar.setRightButtonText("分享");
        wareToolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mWares.getName());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(mWares.getImgUrl());
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.cniao5.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(mWares.getName());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    @SuppressLint("AddJavascriptInterface")
    private void initWebView(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //如果这为true的话，这里页面是阻塞的，图片是加载不出来的，所以改成false
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);

        webView.loadUrl(Contants.API.WARES_DETAIL);
        mWebAppInterface = new WebAppInterface(this);
        webView.addJavascriptInterface(mWebAppInterface,"appInterface");
        webView.setWebViewClient(new WC());
    }


    @Override
    public void onClick(View v) {
        this.finish();
    }

    class WC extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //当页面加载完
            if (mDialog !=null && mDialog.isShowing()){
                mDialog.dismiss();
            }
            mWebAppInterface.showDetail();
        }
    }


    private class WebAppInterface{

        private Context mContext;

        public WebAppInterface(Context context){
            this.mContext = context;
        }

        @JavascriptInterface
        public void showDetail(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:showDetail("+mWares.getId()+")");
                }
            });
        }

        @JavascriptInterface
        public void buy(long id){
            mCartProvider.put(mWares);
            ToastUtils.show(mContext,"已添加到购物车");
        }

        @JavascriptInterface
        public void addFavorites(long id){

        }

    }

}
