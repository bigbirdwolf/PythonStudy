package rx.schedulers;

import rx.Scheduler;

@Deprecated
/* loaded from: classes.dex */
public final class NewThreadScheduler extends Scheduler {
    @Override // rx.Scheduler
    public Scheduler.Worker createWorker() {
        return null;
    }

    private NewThreadScheduler() {
        throw new IllegalStateException("No instances!");
    }
}