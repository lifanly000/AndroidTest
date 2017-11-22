package com.sleep.sunshine.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.Toast;

import com.sleep.sunshine.MainActivity;
import com.sleep.sunshine.base.IBasePresenter;
import com.sleep.sunshine.dao.User;
import com.usher.greendao_demo.greendao.gen.DaoMaster;
import com.usher.greendao_demo.greendao.gen.DaoSession;
import com.usher.greendao_demo.greendao.gen.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import static android.text.TextUtils.isEmpty;

/**
 * Created by lifan on 2017/7/24.
 */

public class MainPresenter implements IBasePresenter {

    private MainView mMainView;

    UserDao userDao;

    public MainPresenter(MainView mainView) {
        mMainView = mainView;
    }

    public void initDbHelp(Context context){

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "recluse-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();
    }

    public void dbAdd(Context context,String id,String name){
        if(!isEmpty(id) && !isEmpty(name)){
            QueryBuilder qb = userDao.queryBuilder();
            ArrayList<User> list = (ArrayList<User>) qb.where(UserDao.Properties.Id.eq(id)).list();
            if(list.size()>0){
                Toast.makeText(context,"主键重复",Toast.LENGTH_SHORT).show();
            }else{
                userDao.insert(new User(Long.valueOf(id),name));
                Toast.makeText(context,"插入数据成功",Toast.LENGTH_SHORT).show();
            }
        }else{
            if (isEmpty(id) && !isEmpty(name)) {
                Toast.makeText(context, "id为空", Toast.LENGTH_SHORT).show();
            }
            if (isEmpty(name) && !isEmpty(id)) {
                Toast.makeText(context, "姓名为空", Toast.LENGTH_SHORT).show();
            }
            if (isEmpty(id) && isEmpty(name)) {
                Toast.makeText(context, "请填写信息", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void dbDelete(Context context,String id){
        if(!isEmpty(id)){
            userDao.deleteByKey(Long.valueOf(id));
            QueryBuilder qb = userDao.queryBuilder();
            ArrayList<User> list = (ArrayList<User>) qb.where(UserDao.Properties.Id.eq(id)).list();
            if (list.size() < 1) {
                Toast.makeText(context, "删除数据成功", Toast.LENGTH_SHORT).show();
                mMainView.clearData();
            }

        }else{
            Toast.makeText(context, "id为空", Toast.LENGTH_SHORT).show();
        }
    }

    public void dbQuery(Context context,String id){
        if(!isEmpty(id)){
            QueryBuilder qb = userDao.queryBuilder();
            ArrayList<User> list = (ArrayList<User>) qb.where(UserDao.Properties.Id.eq(id)).list();
            if(list.size()>0){
                String text = "";
                for (User user : list) {
                    text = text + "\r\n" + user.getName();
                }
                mMainView.fillQueryData(text);
            }else{
                mMainView.fillQueryData("");
                Toast.makeText(context, "不存在该数据", Toast.LENGTH_SHORT).show();
            }
            mMainView.clearData();
        }else{
            Toast.makeText(context, "id为空", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getData() {

    }





    public interface MainView {
        void clearData();
        void fillQueryData(String data);
    }
}
