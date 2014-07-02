package com.dv.ssss.ui.other;

import javafx.event.Event;
import javafx.event.EventHandler;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class ObservableEvent<T extends Event> implements Observable.OnSubscribe<T>, EventHandler<T> {

    private Action1<EventHandler<T>> addListener;
    private Subscriber<? super T> subscriber;

    public ObservableEvent(Action1<EventHandler<T>> addListener) {

        this.addListener = addListener;
    }

    @Override
    public void handle(T event) {

        subscriber.onNext(event);
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {

        this.subscriber = subscriber;
        this.addListener
                .call(this);
    }
}
