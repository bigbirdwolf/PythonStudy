package com.facebook.stetho.common.android;

import android.os.Handler;
import android.os.Looper;
import com.facebook.stetho.common.UncheckedCallable;
import com.facebook.stetho.common.Util;

/* loaded from: classes.dex */
public final class HandlerUtil {
    private HandlerUtil() {
    }

    public static boolean checkThreadAccess(Handler handler) {
        return Looper.myLooper() == handler.getLooper();
    }

    public static void verifyThreadAccess(Handler handler) {
        Util.throwIfNot(checkThreadAccess(handler));
    }

    public static <V> V postAndWait(Handler handler, final UncheckedCallable<V> uncheckedCallable) {
        if (checkThreadAccess(handler)) {
            try {
                return uncheckedCallable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new WaitableRunnable<V>() { // from class: com.facebook.stetho.common.android.HandlerUtil.1
            @Override // com.facebook.stetho.common.android.HandlerUtil.WaitableRunnable
            protected V onRun() {
                return (V) UncheckedCallable.this.call();
            }
        }.invoke(handler);
    }

    public static void postAndWait(Handler handler, final Runnable runnable) {
        if (checkThreadAccess(handler)) {
            try {
                runnable.run();
                return;
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        new WaitableRunnable<Void>() { // from class: com.facebook.stetho.common.android.HandlerUtil.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.stetho.common.android.HandlerUtil.WaitableRunnable
            public Void onRun() {
                runnable.run();
                return null;
            }
        }.invoke(handler);
    }

    /* loaded from: classes.dex */
    private static abstract class WaitableRunnable<V> implements Runnable {
        private Exception mException;
        private boolean mIsDone;
        private V mValue;

        protected abstract V onRun();

        protected WaitableRunnable() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                try {
                    this.mValue = onRun();
                    this.mException = null;
                    synchronized (this) {
                        this.mIsDone = true;
                        notifyAll();
                    }
                } catch (Exception e) {
                    this.mValue = null;
                    this.mException = e;
                    synchronized (this) {
                        this.mIsDone = true;
                        notifyAll();
                    }
                }
            } catch (Throwable th) {
                synchronized (this) {
                    this.mIsDone = true;
                    notifyAll();
                    throw th;
                }
            }
        }

        public V invoke(Handler handler) {
            if (!handler.post(this)) {
                throw new RuntimeException("Handler.post() returned false");
            }
            join();
            if (this.mException != null) {
                throw new RuntimeException(this.mException);
            }
            return this.mValue;
        }

        private void join() {
            synchronized (this) {
                while (!this.mIsDone) {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
        }
    }
}