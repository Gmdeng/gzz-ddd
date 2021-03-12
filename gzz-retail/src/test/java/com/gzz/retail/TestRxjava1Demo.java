package com.gzz.retail;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TestRxjava1Demo {
    public static void main(String[] args) {
        //创建一个上游 Observable：被观察者（Observable）
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//                emitter.onComplete();


                System.out.println("emit 1");
                emitter.onNext(1);
                System.out.println("emit 2");
                emitter.onNext(2);
                System.out.println("emit 3");
                emitter.onNext(3);
                System.out.println("emit complete");
                emitter.onComplete();
                System.out.println("emit 4");
                emitter.onNext(4);
            }
        });
        //创建一个下游 Observer 观察者（Observer）
        Observer<Integer> observer = new Observer<Integer>() {
            // 被观察者 & 观察者 沟通的载体
            @Override
            public void onSubscribe(Disposable d) {//事件（Event）
                //Log.d(TAG, "subscribe");
                System.out.println("subscribe");
            }

            @Override
            public void onNext(Integer value) {//事件（Event）
                //Log.d(TAG, "" + value);
                System.out.println("Handle: " + value);
            }

            @Override
            public void onError(Throwable e) { //事件（Event）
                //Log.d(TAG, "error");
                System.out.println("error");
            }

            @Override
            public void onComplete() { //事件（Event）
                //Log.d(TAG, "complete");
                System.out.println("complete");
            }
        };
        //建立连接 订阅（Subscribe） 连接 被观察者 & 观察者， 相当于注册监听
        observable.subscribe(observer);

    }
}
