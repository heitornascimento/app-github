package cl.com.br.github.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import java.lang.ref.WeakReference;
import java.util.List;

import cl.com.br.github.endpoint.EndpointResult;
import cl.com.br.github.model.Repository;

/**
 * Created by heitornascimento on 8/20/16.
 */
public class BasePresenter implements EndpointResult.Receiver {


    public Presentable mPresentable;
    private WeakReference<Context> mWeakReference;

    public BasePresenter(Presentable presentable, Context ctx) {
        this.mPresentable = presentable;
        mWeakReference = new WeakReference<Context>(ctx);
    }

    public BasePresenter(Context ctx) {
        mWeakReference = new WeakReference<Context>(ctx);
    }


    protected Context getContext() {
        return mWeakReference.get();
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

    }

    public static interface Presentable {
        public void onReceiveData(Parcelable data);
        public void onFailure(String msg);
    }

}
