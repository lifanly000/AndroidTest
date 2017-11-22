package com.sleep.sunshine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sleep.sunshine.MainActivity;
import com.sleep.sunshine.R;
import com.sleep.sunshine.base.BaseActivity;
import com.sleep.sunshine.eventbus.MessageEvent;
import com.sleep.sunshine.http.model.Author;
import com.sleep.sunshine.presenter.SecondPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends BaseActivity implements SecondPresenter.SecondView{


    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.btn2)
    Button mBtn2;
    @BindView(R.id.text)
    TextView mText;

    @Override
    protected void initPresenter() {
        presenter = new SecondPresenter(this,this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_second;
    }

    @Override
    protected void initViews() {
        presenter.getData();
    }

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Log.i(TAG, "onStart: ");
    }
    

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Subscribe
    public void onEvent(MessageEvent event){
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onMessageEvent second: " + event.message);
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
//                startActivity(new Intent(this, MainActivity.class));
                EventBus.getDefault().post(new MessageEvent("我是中国人"));
                finish();
                Log.i(TAG, "onViewClicked: ");
                break;
            case R.id.btn2:
                startActivity(new Intent(this, TestTouchDispatchActivity.class));
//                throw new NullPointerException("null");
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, "onNewIntent", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setAutorData(Author author) {
        mText.setText(author.getSlug());
    }
}
