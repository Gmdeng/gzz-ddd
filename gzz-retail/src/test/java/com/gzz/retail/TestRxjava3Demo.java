package com.gzz.retail;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class TestRxjava3Demo {
    public static void main(String[] args) {
        //创建一个上游 Observable：
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
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
        }).subscribe(new Consumer<Integer>() {   //创建一个下游 Observer
            // Consumer参数的方法表示下游只关心onNext事件, 其他的事件我假装没看见
            @Override
            public void accept(Integer value) throws Exception {
                System.out.println("onNext: " + value);
            }
        });
//        }).subscribe(new Observer<Integer>() {   //创建一个下游 Observer
//            private Disposable mDisposable;
//            private int i;
//
//            @Override
//            public void onSubscribe(Disposable d) {
//                System.out.println("subscribe");
//                mDisposable = d;
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                //Log.d(TAG, "" + value);
//                System.out.println( "onNext: " + value);
//                i++;
//                if (i == 2) {
//                    System.out.println("dispose");
//                    mDisposable.dispose();
//                    System.out.println("isDisposed : " + mDisposable.isDisposed());
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                //Log.d(TAG, "error");
//                System.out.println("error");
//            }
//
//            @Override
//            public void onComplete() {
//                //Log.d(TAG, "complete");
//                System.out.println("complete");
//            }
//        });
    }
}
