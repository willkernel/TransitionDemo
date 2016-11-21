package com.willkernel.app.transitiondemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends FragmentActivity implements Fragment1.OnInteractionListener, IWXAPIEventHandler {
    private String TAG = getClass().getSimpleName();
    private Fragment1 fragment1;
    private String FRAGMENT_TAG = "FragmentTag";
    private TextView tv;
    private IWXAPI msgApi;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.reconnection = true;
        try {
            mSocket = IO.socket("http://chat.socket.io");
            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
            mSocket.on("user joined", onUserJoined);
            mSocket.on("user left", onUserLeft);
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        msgApi = WXAPIFactory.createWXAPI(this, "wx70fb11d4639c5f47");
        msgApi.handleIntent(getIntent(), this);
        MyApp myApp = MyApp.getInstance();
        ((Button) findViewById(R.id.btn)).setText(getResources().getQuantityString(R.plurals.androidP, 2, 10));
        ClearEditText editText = (ClearEditText) findViewById(R.id.clearEt);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                        return true;
                    }
                }
                return false;
            }
        });
        editText.setSearchListener(new ClearEditText.SearchListener() {
            @Override
            public void onSearch() {
                Toast.makeText(MainActivity.this, "Awesome", Toast.LENGTH_SHORT).show();
            }
        });
        setEmojiToTextView();
        tv = (TextView) findViewById(R.id.tv);
        tv.setText(R.string.app_name);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment1 = new Fragment1();
        Bundle bundle = new Bundle();
        bundle.putInt("position", 1);
        fragment1.setArguments(bundle);
        fragmentTransaction.replace(R.id.fl_container, fragment1, FRAGMENT_TAG);
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack("fragment1");
        fragmentTransaction.commit();

        fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //微信跳转
//                Intent intent = new Intent();
//                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
//                intent.setAction(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setComponent(cmp);
//                intent.setData(Uri.parse("weixin://dl/business/?ticket=gh_0eda1bee80c5"));
//                intent.setData(Uri.parse("weixin://dl/business/?ticket=t04fd9d0d0e3c63c4c6d13bacac7e9de5#wechat_redirect"));
//                intent.setData(Uri.parse("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8b17740e4ea78bf5&redirect_uri=http%3A%2F%2Fm.hz41319.com%2Fwei%2Flogin.php&response_type=code&scope=snsapi_userinfo&state=&connect_redirect=1#wechat_redirect"));
//                intent.setData(Uri.parse("weixin://dl/business?appid=wx8b17740e4ea78bf5&redirect_uri=http%3A%2F%2Fm.hz41319.com%2Fwei%2Flogin.php#wechat_redirect"));
//                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://m.hz41319.com/wei/start.php"));
//                startActivityForResult(intent, 0);

//                JumpToBizProfile.Req req = new JumpToBizProfile.Req();
//                req.toUserName = getString(R.string.ypt_id);
//                req.profileType = JumpToBizProfile.JUMP_TO_NORMAL_BIZ_PROFILE;
//                req.extMsg = "http://m.hz41319.com/wei/start.php";
//                JumpToBizWebview.Req req=new JumpToBizWebview.Req();
//                req.toUserName=getString(R.string.ypt_id);
//                req.webType=0;
//                req.extMsg = "http://m.hz41319.com/wei/start.php";
//                msgApi.sendReq(req);
//                WXWebpageObject webpageObject = new WXWebpageObject("http://m.hz41319.com/wei/start.php");
//                WXMediaMessage msg = new WXMediaMessage(webpageObject);
//                msg.title = getString(R.string.app_name);
//                msg.description = "Jump";
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = buildTransaction("webpage");
//                req.message = msg;
//                req.scene = SendMessageToWX.Req.WXSceneSession; //分享朋友
//                // req.scene = SendMessageToWX.Req.WXSceneTimeline;  //分享朋友圈
//                msgApi.sendReq(req);
//                JumpToBizProfile.Req req = new JumpToBizProfile.Req();
//                req.toUserName = "gh_0eda1bee80c5";
//                req.profileType = JumpToBizProfile.JUMP_TO_NORMAL_BIZ_PROFILE;
//                req.extMsg = "extMsg";
//                msgApi.sendReq(req);
            }
        }, 5000);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    private void setEmojiToTextView() {
        int unicodeJoy = 0x1F602;
        String emojiString = getEmojiStringByUnicode(unicodeJoy);
        TextView emojiTv = (TextView) findViewById(R.id.emojiTv);
        emojiTv.setText("\u263a\ud83d\ude0a\ud83d\ude00\ud83d\ud83d\ude02");
//        emojiTv.setText(emojiString);
    }

    private String getEmojiStringByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    @Override
    protected void onRestart() {
        Log.e(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
        fragment1.setRetainInstance(true);
        startService(new Intent(this, PushService.class));
        startService(new Intent(this, ProtectService.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //REACT
        }
        if (newConfig.keyboardHidden == Configuration.KEYBOARDHIDDEN_NO) {
            //REACT
        }
    }

    public MainActivity() {
        super();
        Log.e(TAG, "MainActivity");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e(TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResumeFragments() {
        Log.e(TAG, "onResumeFragments");
        super.onResumeFragments();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLowMemory() {
        Log.e(TAG, "onLowMemory");
        super.onLowMemory();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.e(TAG, "onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    protected void onPostResume() {
        Log.e(TAG, "onPostResume");
        super.onPostResume();
    }

    private boolean isConnected;
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isConnected) {
                        mSocket.emit("add user", getString(R.string.app_name));
                        isConnected = true;
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isConnected = false;
                }
            });
        }
    };

    private Emitter.Listener onUserJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try {
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                    } catch (JSONException e) {
                        return;
                    }
                    Toast.makeText(MainActivity.this, "name=" + username + " num=" + numUsers, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try {
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                    } catch (JSONException e) {
                        return;
                    }
                    Toast.makeText(MainActivity.this, "name=" + username + " num=" + numUsers, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @Override
    public void onRefresh(int state) {
        Log.e(TAG, "onRefresh=" + state);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.e(TAG, "onReq baseReq=" + baseReq);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.e(TAG, "onResp baseResp=" + baseResp);
    }
}