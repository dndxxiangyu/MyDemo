package com.example.mydemo.webview;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.mydemo.R;

/**
 * 创建时间：2020-02-04
 * author: wuxiangyu.lc
 * describe:
 */
public class WebViewActivity extends AppCompatActivity {
    public static final String TAG = "WebViewActivity";
    private WebView webView;
    private Button btnCallJsLoadUrl;
    private Button btnCallEvaluateJavascript;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.web_view);
        //android调用JS代码的Button
        btnCallJsLoadUrl = findViewById(R.id.btnCallJsLoadUrl);
        btnCallEvaluateJavascript = findViewById(R.id.btnCallEvaluateJavascript);

        //防止外部浏览器调用此链接
        setWebViewClient();
        setWebChromeClient();

        WebSettings settings = webView.getSettings();
        //允许WebView使用JS
        settings.setJavaScriptEnabled(true);
        //支持通过JS打开新窗口(允许JS弹窗)
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new AndroidToJS(), "android");

        webView.loadUrl("file:///android_asset/javascript.html");
        btnCallJsLoadUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:callJS()");
            }
        });

        btnCallEvaluateJavascript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(TAG, "1-EvaluateJavascript: ");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.e(TAG, "2-EvaluateJavascript: " + value + "; Threadname: " + Thread.currentThread().getName());
                        }
                    });

                    Log.e(TAG, "3-EvaluateJavascript: ");
                }
            }
        });
    }

    private void setWebViewClient() {
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("js")) {
                    if (url.contains("webview")) {
                        Toast.makeText(getApplicationContext(), "heihei", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if ("js".equals(uri.getScheme())) {
                    if ("webview".equals(uri.getAuthority())) {
                        Toast.makeText(getApplicationContext(), "heihei", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }
    private void setWebChromeClient() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                /**
                 * url: 默认当前页面url
                 * message：alert的内容
                 */
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                /**
                 * url: 默认当前页面url
                 * message：alert的内容
                 */
                result.confirm(); //js端拿到的是true
                return true;
            }
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {

                /**
                 * url: 默认当前页面url
                 * message：弹出的标题
                 */
                Log.e(TAG, "onJsPrompt: " + url);
                //本身在return false时候由系统弹出手动输入的内容，现在主动输入。
                //或者不处理result，直接处理message：这里可以理解后端直接给url协议。
                result.confirm("return result");
                return true;
            }
        });
    }
}
