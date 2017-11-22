package com.sleep.sunshine;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sleep.sunshine.activity.JsbTestActivity;
import com.sleep.sunshine.activity.SecondActivity;
import com.sleep.sunshine.activity.TestTouchDispatchActivity;
import com.sleep.sunshine.base.BaseActivity;
import com.sleep.sunshine.eventbus.MessageEvent;
import com.sleep.sunshine.presenter.MainPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.data;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends BaseActivity<MainPresenter> implements MainPresenter.MainView {



    @BindView(R.id.etId)
    EditText mEtId;
    @BindView(R.id.etName)
    EditText mEtName;
    @BindView(R.id.tvQuery)
    TextView mTvQuery;
    @BindView(R.id.btnAdd)
    Button mBtnAdd;
    @BindView(R.id.btnDelete)
    Button mBtnDelete;
    @BindView(R.id.btnQuery)
    Button mBtnQuery;
    @BindView(R.id.btnJump)
    Button mBtnJump;


    @Override
    protected void initPresenter() {
        presenter = new MainPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
//        EventBus.getDefault().register(this);
        presenter.initDbHelp(this);
        String str = "1.新注册用户赠送518趣币活动延长至8月31日；\n2.南海渔王火爆上线；\n3.首页界面优化。";
        mTvQuery.setText(str);
    }


    @OnClick({R.id.btnAdd, R.id.btnDelete, R.id.btnQuery,R.id.btnJump})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                String id = mEtId.getText().toString();
                String name = mEtName.getText().toString();
                presenter.dbAdd(this,id,name);
                break;
            case R.id.btnDelete:
                presenter.dbDelete(this,mEtId.getText().toString());
                break;
            case R.id.btnQuery:
                presenter.dbQuery(this,mEtId.getText().toString());
                break;
            case R.id.btnJump:
                startActivity(new Intent(this, SecondActivity.class));
                break;
        }
    }


    @Subscribe
    public void onEvent(MessageEvent event){
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onMessageEvent: " + event.message);
    }

    @Subscribe
    public void handleSomethingElse(MessageEvent event){
        Toast.makeText(this, event.message+ "dafafaf", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onMessageEvent   2222: " + event.message);
    }

    @Override
    public void clearData() {
        mEtId.setText("");
        mEtName.setText("");
    }

    @Override
    public void fillQueryData(String data) {
        mTvQuery.setText(data);
    }

    private static final String TAG = "MainActivity";

    @Override
    protected void onResume() {
        super.onResume();
//        Log.i(TAG, "onResume: ");
//        Log.i(TAG, "onResume2: "+isAppOnForeground(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.i(TAG, "onPause: ");
//        Log.i(TAG, "onPause2: "+isAppOnForeground(this));
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
//        Log.i(TAG, "onStop: ");
//        Log.i(TAG, "onStop2: "+isAppOnForeground(this));
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public static boolean isAppOnForeground(Context context) {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
}
