package com.sleep.sunshine.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.widget.Button;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.sleep.sunshine.R;

import java.net.URLDecoder;
import java.util.Map;

public class JsbTestActivity extends AppCompatActivity implements View.OnClickListener {


    private final String TAG = "MainActivity";

    /**
     * H5 apiList
     */
    private String apilist = "back,charge,record,login,initIBeacon,openurl,getLocation,getBalance,getUserOpenId,redirect,getClientInfo";

    BridgeWebView webView;

    Button button;

    int RESULT_CODE = 0;

    ValueCallback<Uri> mUploadMessage;

    static class Location {
        String address;
    }

    static class User {
        String name;
        Location location;
        String testStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsb_test);

        webView = (BridgeWebView) findViewById(R.id.webView);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(this);

        webView.setDefaultHandler(new DefaultHandler());

        webView.setWebChromeClient(new WebChromeClient() {

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                pickFile();
            }
        });

        webView.loadUrl("file:///android_asset/demo.html");
//        webView.loadUrl("https://www.funhainan.net:444/ZsqImage/shoppingMall/main.html");
//        webView.loadUrl("weixin://dl/business/?ticket=td74b07057ef3af715bc2267c49fad8ef#wechat_redirect");
//        webView.loadUrl("https://www.funhainan.net:444/ZsqImage/shoppingMall/chongzhi/choose.html");

        webView.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
            }

        });

        webView.registerHandler("verifyLBGamePage", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                function.onCallBack(apilist);
            }
        });

        webView.registerHandler("openScheme", new BridgeHandler() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void handler(String data, CallBackFunction function) {
                try {
                    Gson gson = new Gson();
                    Map<String, String> map = gson.fromJson(data, Map.class);
                    Uri uri = Uri.parse(map.get("url"));
                    String json_params = uri.getQueryParameter("json_params");
                    json_params = URLDecoder.decode(json_params, "utf-8");
                    Map<String, String> parmsMap = gson.fromJson(json_params, Map.class);
                    Log.i(TAG,"handler = submitFromWeb, data from web = " + data + "&&&&&&& json_params = " + json_params);
                    String str = "";
                    function.onCallBack(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        User user = new User();
        Location location = new Location();
        location.address = "SDU";
        user.location = location;
        user.name = "大头鬼";

        webView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });

        webView.send("hello");

    }

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage){
                return;
            }
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (button.equals(v)) {
            webView.callHandler("functionInJs", "data from Java", new CallBackFunction() {

                @Override
                public void onCallBack(String data) {
                    // TODO Auto-generated method stub
                    Log.i(TAG, "reponse data from js " + data);
                }

            });
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.stopLoading();
            webView.resumeTimers();
            webView.destroy();
        }
    }
}
