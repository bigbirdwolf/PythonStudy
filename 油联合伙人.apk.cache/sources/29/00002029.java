package rx.internal.operators;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.observers.Subscribers;
import rx.subscriptions.Subscriptions;

/* loaded from: classes.dex */
public class OperatorDoOnUnsubscribe<T> implements Observable.Operator<T, T> {
    private final Action0 unsubscribe;

    @Override // rx.functions.Func1
    public /* bridge */ /* synthetic */ Object call(Object obj) {
        return call((Subscriber) ((Subscriber) obj));
    }

    public OperatorDoOnUnsubscribe(Action0 action0) {
        this.unsubscribe = action0;
    }

    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        subscriber.add(Subscriptions.create(this.unsubscribe));
        return Subscribers.wrap(subscriber);
    }
}