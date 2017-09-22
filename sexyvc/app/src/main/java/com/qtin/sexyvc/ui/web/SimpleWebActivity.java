package com.qtin.sexyvc.ui.web;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qtin.sexyvc.R;
import com.qtin.sexyvc.common.AppComponent;
import com.qtin.sexyvc.common.MyBaseActivity;
import com.qtin.sexyvc.utils.ConstantUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.apache.http.HttpStatus;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by ls on 17/4/26.
 */
public class SimpleWebActivity extends MyBaseActivity {

    @BindView(R.id.ivRight)
    ImageView ivRight;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;
    @BindView(R.id.webViewContainer)
    FrameLayout webViewContainer;
    private String url;
    private String title;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if(webViewContainer!=null&&webView!=null){
                webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                webView.clearHistory();
                webViewContainer.removeView(webView);
                webView.destroy();
                webView=null;
            }
            shareListener=null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isContaineFragment() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.web_activity;
    }

    @Override
    protected void initData() {
        tvTitle.setText("");
        //ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.icon_nav_share);
        url = getIntent().getExtras().getString(ConstantUtil.INTENT_URL);

        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        //不使用缓存:
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        //复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //设定加载开始的操作
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //设定加载结束的操作
                ivRight.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                //设定加载资源的操作
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                ivRight.setVisibility(View.GONE);
                switch (errorCode){
                    case HttpStatus.SC_NOT_FOUND:
                        errorLayout.setVisibility(View.VISIBLE);
                        break;
                    default:
                        errorLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();    //表示等待证书响应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                SimpleWebActivity.this.title=title;
                if(tvTitle!=null){
                    tvTitle.setText(title);
                }
            }
        });
        webView.loadUrl(url);
    }

    @OnClick({R.id.ivLeft, R.id.ivErrorStatus,R.id.ivRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivRight:
                final UMWeb web = new UMWeb(url);
                web.setTitle(title);
                web.setDescription("点击查看");
                web.setThumb(new UMImage(this, R.drawable.share_logo));  //缩略图
                showShareDialog(new onShareClick() {
                    @Override
                    public void onClickShare(int platForm) {
                        dismissShareDialog();
                        switch (platForm) {
                            case ConstantUtil.SHARE_WECHAT:
                                new ShareAction(SimpleWebActivity.this)
                                        .withMedia(web)
                                        .setPlatform(SHARE_MEDIA.WEIXIN)
                                        .setCallback(shareListener).share();
                                break;
                            case ConstantUtil.SHARE_WX_CIRCLE:
                                new ShareAction(SimpleWebActivity.this)
                                        .withMedia(web)
                                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                        .setCallback(shareListener).share();
                                break;
                            case ConstantUtil.SHARE_QQ:
                                new ShareAction(SimpleWebActivity.this)
                                        .withMedia(web)
                                        .setPlatform(SHARE_MEDIA.QQ)
                                        .setCallback(shareListener).share();
                                break;
                            case ConstantUtil.SHARE_SINA:
                                new ShareAction(SimpleWebActivity.this)
                                        .withMedia(web)
                                        .setPlatform(SHARE_MEDIA.SINA)
                                        .setCallback(shareListener).share();
                                break;
                        }
                    }
                });

                break;
            case R.id.ivLeft:
                finish();
                if(webView.canGoBack()){
                    webView.goBack();
                }else{
                    finish();
                }
                break;
            case R.id.ivErrorStatus:

                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };
}
