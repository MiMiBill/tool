package com.zkys.operationtool.baseImpl;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 创建activity中泛型传入相应的view接口，presenter中泛型传入相应的presenter接口
 * activity中onCreate中初始化presenter，onDestroy中调用detach，将presenter中正在执行的任务取消，将view对象置为空。
 * presenter中通过构造传递参数。将view的实例传递进入presenter
 * Created by DGDL-08 on ${DATA}
 */
public abstract class BasePresenterImpl <V extends BaseView> implements BasePresenter {

    protected V view;//给子类使用view

    public BasePresenterImpl(V view) {
        this.view = view;
        start();
    }

    @Override
    public void detach() {
        this.view = null;
        unDisposable();
    }

    @Override
    public void start() {

    }

    /*-----------------------我是分割线--------------------------------*/

    //以下下为配合RxJava2+retrofit2使用的

    //将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
    private CompositeDisposable mCompositeDisposable;

    /**
     * 将Disposable添加
     *
     * @param subscription
     */
    @Override
    public void addDisposable(Disposable subscription) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    @Override
    public void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

}
