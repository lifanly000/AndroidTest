package com.sleep.sunshine.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.sleep.sunshine.presenter.MainPresenter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by lifan on 2017/7/24.
 */

public abstract class BaseActivity<T extends IBasePresenter> extends RxAppCompatActivity {

    protected T presenter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayoutRes());
        ButterKnife.bind(this);
        initPresenter();
        initViews();
    }

    /**
     * 初始化presenter
     */
    protected abstract void initPresenter();

    /**
     * 绑定布局文件
     * @return
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * 初始化视图
     */
    protected abstract void initViews();

    /**
     * loading
     */
    protected void showLoading(){

    }

    /**
     * 隐藏loading
     */
    protected void hideLoading(){

    }


}
