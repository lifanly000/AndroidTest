package com.sleep.sunshine.presenter;

import android.content.Context;

import com.sleep.sunshine.base.IBasePresenter;
import com.sleep.sunshine.http.APIWebService;
import com.sleep.sunshine.http.model.Author;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lifan on 2017/9/5.
 */

public class SecondPresenter implements IBasePresenter {

    private SecondView mSecondView;
    private Context mContext;

    public SecondPresenter(SecondView secondView, Context context) {
        mSecondView = secondView;
        mContext = context;
    }



    @Override
    public void getData() {
        Observable<Author> call = APIWebService.getInstance(mContext).getExcuteApi().getAuthor("qinchao");
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Author>() {
                    @Override
                    public void accept(Author author) throws Exception {
                        mSecondView.setAutorData(author);
                    }
                });
    }

    public interface SecondView{
           void setAutorData(Author author);
    }
}
