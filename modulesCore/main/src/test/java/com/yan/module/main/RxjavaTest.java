package com.yan.module.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxjavaTest {
    public static void main(String[] args) {
        System.out.println("================================================");
        RxjavaTest demo = new RxjavaTest();
//        demo.create();
//        demo.just();
//        demo.map();
//        demo.toMap();
//        demo.thread();
        demo.test();
        System.out.println("================================================");
    }

    // 观察者
    Observer observer = new Observer() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Object o) {
            System.out.println(o);
        }

        @Override
        public void onError(Throwable e) {
            System.out.println(e.getMessage());
        }

        @Override
        public void onComplete() {

        }
    };

    // 消费者
    Consumer consumer = new Consumer() {
        @Override
        public void accept(Object o) throws Throwable {
            System.out.println(o);
        }
    };

    private void create(){
        // 观察者模式订阅
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Throwable {
//                emitter.onNext("register");
//                emitter.onNext("login");
//            }
//        }).subscribe(observer);

        // 消费者模式订阅
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("register");
                emitter.onNext("login");
            }
        }).subscribe(consumer);
    }

    private void just(){
        Observable.just("zhangsan","lisi").subscribe(observer);
    }

    private void map(){
        List<Long> memberIds = new ArrayList<>();
        memberIds.add(101L);
        memberIds.add(102L);
        memberIds.add(103L);
        Observable.fromArray(memberIds)
           .map(new Function<List<Long>, Object>() {
               @Override
               public Object apply(List<Long> longList) throws Throwable {

                   return longList;
               }
           })
           .subscribe(new Consumer<Object>() {
               @Override
               public void accept(Object o) throws Throwable {
                   System.out.println("1qaz2wsx:"+o);
               }
           });
    }

    private void toMap(){
        List<Inside> insideList = new ArrayList<>();
        Inside inside = new Inside();
        inside.setId(1);
        inside.setName("zhangsan");
        insideList.add(inside);

        inside = new Inside();
        inside.setId(2);
        inside.setName("lisi");
        insideList.add(inside);
        Observable.just(insideList)
            .toMap(
                new Function<List<Inside>, Object>() {
                    @Override
                    public Object apply(List<Inside> insides) throws Throwable {

                        return insides.get(0).getId();
                    }
                },
                new Function<List<Inside>, Object>() {
                    @Override
                    public Object apply(List<Inside> insides) throws Throwable {

                        return insides.get(0);
                    }
                }
            )
            .subscribe(new Consumer<Map<Object, Object>>() {
                @Override
                public void accept(Map<Object, Object> objectObjectMap) throws Throwable {
                    System.out.println(objectObjectMap.toString());
                }
            });
    }

    public static class Inside{
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private void thread(){
        Observable.just("a")
                .map(new Function<String, Object>() {
                    @Override
                    public Object apply(String s) throws Throwable {
                        System.out.println("RxjavaTest map:"+Thread.currentThread());
                        return s;
                    }
                })
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Throwable {
                        System.out.println("RxjavaTest map2:"+Thread.currentThread());
                        return o;
                    }
                })
                .subscribeOn(Schedulers.newThread())       // 子线程
                .observeOn(AndroidSchedulers.mainThread()) // 主线程
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("RxjavaTest onSubscribe:"+Thread.currentThread());
                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        System.out.println("RxjavaTest onNext:"+Thread.currentThread());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("RxjavaTest onError:"+Thread.currentThread());
                        System.out.println("RxjavaTest onError:"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("RxjavaTest onComplete:"+Thread.currentThread());
                    }
                });
    }

    private void test(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        for(int i=0;i<list.size();i++){
            Observable.just(list.get(i))
                .map(new Function<Integer, Object>() {
                    @Override
                    public Object apply(Integer integer1) throws Throwable {
                        System.out.println("11111111111111111111111111111111111");
                        return integer1;
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Throwable {
                        System.out.println("1qaz2wsx:"+o);
                    }
                });
        }
    }
}
