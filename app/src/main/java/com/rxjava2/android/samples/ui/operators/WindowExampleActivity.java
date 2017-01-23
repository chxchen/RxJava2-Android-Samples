package com.rxjava2.android.samples.ui.operators;import android.util.Log;import com.rxjava2.android.samples.ui.ExampleBaseActivity;import com.rxjava2.android.samples.utils.AppConstant;import java.util.concurrent.TimeUnit;import io.reactivex.Observable;import io.reactivex.android.schedulers.AndroidSchedulers;import io.reactivex.functions.Consumer;import io.reactivex.schedulers.Schedulers;public class WindowExampleActivity extends ExampleBaseActivity {    /*     * Sample of window operator     */    protected void doSomeWork() {        Observable.interval(1, TimeUnit.SECONDS).take(12)                .window(3, TimeUnit.SECONDS)                .subscribeOn(Schedulers.io())                .observeOn(AndroidSchedulers.mainThread())                .subscribe(new Consumer<Observable<Long>>() {                    @Override                    public void accept(Observable<Long> observable) {                        Log.d(TAG, "Sub Divide begin....");                        textView.append("Sub Divide begin ....");                        textView.append(AppConstant.LINE_SEPARATOR);                        observable                                .subscribeOn(Schedulers.io())                                .observeOn(AndroidSchedulers.mainThread())                                .subscribe(new Consumer<Long>() {                                    @Override                                    public void accept(Long aLong) {                                        Log.d(TAG, "Next:" + aLong);                                        textView.append("Next:" + aLong);                                        textView.append(AppConstant.LINE_SEPARATOR);                                    }                                });                    }                });        /*        输出        subdivide begin……        Next:0        Next:1        subdivide begin……        Next:2        Next:3        Next:4        subdivide begin……        Next:5        Next:6        Next:7        subdivide begin……        Next:8        Next:9        Next:10        subdivide begin……        Next:11        为什么不是 0，1，2     3，4，5 ..... 这种形式呢。        同学，问的非常好！！！        因为0秒的时刻啥也没发射，1秒的时刻发射了0，2秒的时刻发射了1，3秒的时刻发射了2        "对呀，前3秒嘛，因该0，1，2嘛"，但是3秒是指时间片段，[0,1) [1,2) [2,3) 左闭右开 这三秒只发射了0和1         */    }}