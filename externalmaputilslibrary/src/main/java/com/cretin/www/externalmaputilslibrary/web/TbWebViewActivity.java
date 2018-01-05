package com.cretin.www.externalmaputilslibrary.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.cretin.www.externalmaputilslibrary.R;
import com.cretin.www.externalmaputilslibrary.utils.CustomProgressDialog;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;


/**
 * 贴吧通用WebView，支持设置cookie、自定义javascript interface
 * <p>
 * 文档： 工程根目录/doc/index.html
 *
 * @author zhaoxianlie
 */
@SuppressLint( "SetJavaScriptEnabled" )
public class TbWebViewActivity extends Activity {
    static public final String TAG_URL = "tag_url";
    static public final String TAG_TITLE = "tag_title";
    private TextView tvTitle;
    private TextView tvBack;
    private String title;

    private String mTitle;

    /**
     * 接受从外界动态绑定javascriptInterface，可以绑定多个
     */
    private static HashMap<String, JavascriptInterface> mJsInterfaces = null;
    private static boolean mEnableJsInterface = true;
    /**
     * 接受从外界动态设置cookie，可以设置多个域的cookie
     */
    private static HashMap<String, String> mCookieMap = null;
    private String mUrl = null;
    private WebView mWebView = null;

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            refresh();
        }
    };

    /**
     * 启动WebView，支持设置：cookie、js interface
     *
     * @param context
     * @param url               网址
     * @param cookieMap         自定义cookie，格式为HashMap<Domain,Cookie>
     * @param enableJsInterface 是否需要支持自定义的javascript interface
     * @param jsInterface       自定义的javascript interface
     */
    private static void startActivity(Context context, String title, String url, HashMap<String, String> cookieMap,
                                      boolean enableJsInterface, HashMap<String, JavascriptInterface> jsInterface) {
        Intent intent = new Intent(context, TbWebViewActivity.class);
        intent.putExtra(TAG_URL, url);
        intent.putExtra(TAG_TITLE, title);
        mCookieMap = cookieMap;
        mJsInterfaces = jsInterface;
        mEnableJsInterface = enableJsInterface;
        if ( (context instanceof Activity) == false ) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 启动WebView，支持设置：cookie、js interface
     *
     * @param context
     * @param url         网址
     * @param cookieMap   自定义cookie，格式为HashMap<Domain,Cookie>
     * @param jsInterface 自定义的javascript interface
     */
    public static void startActivity(Context context, String title, String url, HashMap<String, String> cookieMap,
                                     HashMap<String, JavascriptInterface> jsInterface) {
        startActivity(context, title, url, cookieMap, true, jsInterface);
    }

    /**
     * 启动一个不携带cookie、不支持javascript interface的WebView
     *
     * @param context
     * @param url
     */
    public static void startActivity(Context context, String title, String url) {
        startActivity(context, title, url, null, false, null);
    }

    /**
     * 启动一个不携带cookie、不支持javascript interface的WebView
     *
     * @param context
     * @param url
     */
    public static void startActivity(Context context, String url) {
        startActivity(context, null, url, null, false, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tb_webview_activity);

        initTitleView();

        // 数据初始化
        Intent intent = this.getIntent();
        mUrl = intent.getStringExtra(TAG_URL);
        mTitle = intent.getStringExtra(TAG_TITLE);

        if ( !TextUtils.isEmpty(mTitle) )
            tvTitle.setText(mTitle);

        if ( TextUtils.isEmpty(mUrl) ) {
            return;
        }
        // 同步cookie
        initCookie();

        initWebView();
        mHandler.postDelayed(mRunnable, 500);
    }


    private void initTitleView() {
        tvBack = ( TextView ) findViewById(R.id.tv_back);
        tvTitle = ( TextView ) findViewById(R.id.tv_title_info);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 同步cookie
     */
    private void initCookie() {
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        if ( mCookieMap != null && !mCookieMap.isEmpty() ) {
            cookieManager.setAcceptCookie(true);
            Iterator<String> it = mCookieMap.keySet().iterator();
            while ( it.hasNext() ) {
                String domain = it.next();
                cookieManager.setCookie(domain, mCookieMap.get(domain));
            }
        } else {
            cookieManager.removeAllCookie();
        }
        CookieSyncManager.getInstance().sync();
    }

    /**
     * 给WebView增加js interface，供FE调用
     */
    private void addJavascriptInterface() {
        if ( !mEnableJsInterface ) {
            return;
        }
        if ( mJsInterfaces == null ) {
            mJsInterfaces = new HashMap<String, JavascriptInterface>();
        }
        // 添加一个通用的js interface接口：TbJsBridge
        if ( !mJsInterfaces.containsKey("TbJsBridge") ) {
            mJsInterfaces.put("TbJsBridge", new JavascriptInterface() {

                @Override
                public Object createJsInterface(Activity activity) {
                    return new TbJsBridge(activity);
                }
            });
        }

        // 增加javascript接口的支持
        Iterator<String> it = mJsInterfaces.keySet().iterator();
        while ( it.hasNext() ) {
            String key = it.next();
            Object jsInterface = mJsInterfaces.get(key).createJsInterface(this);
            mWebView.addJavascriptInterface(jsInterface, key);
        }
    }

    /**
     * 初始化webview的相关参数
     *
     * @return
     */
    private void initWebView() {
        try {
            mWebView = ( WebView ) findViewById(R.id.webview_entity);
            // 启用js功能
            mWebView.getSettings().setJavaScriptEnabled(true);
            // 增加javascript接口的支持
            addJavascriptInterface();
            // 滚动条设置
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.getSettings().setDatabaseEnabled(true);
            if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT ) {
                mWebView.getSettings().setDatabasePath("/data/data/" + mWebView.getContext().getPackageName() + "/databases/");
            }
            mWebView.setHorizontalScrollBarEnabled(false);
            mWebView.setHorizontalScrollbarOverlay(false);
            mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
            // 必须要设置这个，要不然，webview加载页面以后，会被放大，这里的100表示页面按照原来尺寸的100%显示，不缩放
//			mWebView.setInitialScale(100);
            // 处理webview中的各种通知、请求事件等
            // 处理webview中的js对话框、网站图标、网站title、加载进度等
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    if ( newProgress == 100 ) {
                        stopDialog();
                    }
                }
            });
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if ( TextUtils.isEmpty(url) ) {
                        return false;
                    }
                    // 通用url跳转规则
                    if ( TbUrlBridge.overrideUrl(TbWebViewActivity.this, url) ) {
                        return true;
                    } else {
                        // 非通用url规则，则用当前webview直接打开
                        try {
                            if(url.startsWith("weixin://") //微信
                                    || url.startsWith("alipays://") //支付宝
                                    || url.startsWith("mailto://") //邮件
                                    || url.startsWith("tel://")//电话
                                    || url.startsWith("baidumap://")//大众点评
                                //其他自定义的scheme
                                    ) {
//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                                startActivity(intent);
                                return true;
                            }
                        } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                            return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                        }
                        mUrl = url;
                        refresh();
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if ( TextUtils.isEmpty(mTitle) ) {
                        tvTitle.setText(view.getTitle().toString());
                        mTitle = view.getTitle().toString();
                    }
                }
            });

            // 使webview支持后退
            mWebView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if ( mWebView.canGoBack() // webview当前可以返回
                            && keyEvent.getAction() == KeyEvent.ACTION_DOWN // 有按键行为
                            && keyCode == KeyEvent.KEYCODE_BACK ) { // 按下了后退键
                        mWebView.goBack(); // 后退
                        return true;
                    }
                    return false;
                }
            });
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( mWebView == null ) {
            return;
        }
        // 控制视频音频的，获取焦点播放，失去焦点停止
        mWebView.resumeTimers();
        callHiddenWebViewMethod("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ( mWebView == null ) {
            return;
        }
        mWebView.pauseTimers();
        callHiddenWebViewMethod("onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( mHandler != null ) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    /**
     * 调用WebView本身的一些方法，有视频音频播放的情况下，必须加这个
     *
     * @param name
     */
    private void callHiddenWebViewMethod(String name) {
        if ( mWebView != null ) {
            try {
                Method method = WebView.class.getMethod(name);
                method.invoke(mWebView);
            } catch ( Exception ex ) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 刷新WebView，重新加载数据
     */
    public void refresh() {
        if ( mWebView != null ) {
            showDialog("正在加载...");
            mWebView.loadUrl(mUrl);
        }
    }

    /**
     * 给WebView增加Javascript Interface的时候，在HashMap中加这个就行了，例子：
     * <p>
     * <pre>
     * 	HashMap<String, JavascriptInterface> jsInterface = new HashMap<String, JavascriptInterface>();
     * jsInterface.put("TbJsBridge", new JavascriptInterface() {
     *
     * @author zhaoxianlie
     * @Override public TbJsBridge createJsInterface(Activity activity) {
     * return new TbJsBridge(activity);
     * }
     * });
     * TbWebviewActivity.startActivity(AboutActivity.this,
     * "http://www.baidu.com", null, jsInterface);
     * </pre>
     */
    public interface JavascriptInterface {
        Object createJsInterface(Activity activity);
    }

    private CustomProgressDialog dialog;

    /**
     * 显示加载对话框
     *
     * @param msg
     */
    public void showDialog(String msg) {
        if ( dialog == null ) {
            dialog = CustomProgressDialog.createDialog(this);
        }
        if ( msg != null && !msg.equals("") ) {
            dialog.setMessage(msg);
        }
        dialog.show();
    }

    /**
     * 关闭对话框
     */
    public void stopDialog() {
        if ( dialog != null && dialog.isShowing() ) {
            dialog.dismiss();
        }
    }

}
