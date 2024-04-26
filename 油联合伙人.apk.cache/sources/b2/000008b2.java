package com.alibaba.sdk.android.oss.internal;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.OSSResult;
import com.alibaba.sdk.android.oss.network.ExecutionContext;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public class OSSAsyncTask<T extends OSSResult> {
    private ExecutionContext context;
    private Future<T> future;

    public void cancel() {
        if (this.context != null) {
            this.context.getCancellationHandler().cancel();
        }
    }

    public boolean isCompleted() {
        return this.future.isDone();
    }

    public T getResult() throws ClientException, ServiceException {
        try {
            return this.future.get();
        } catch (InterruptedException e) {
            throw new ClientException(e.getMessage(), e);
        } catch (ExecutionException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof ClientException) {
                throw ((ClientException) cause);
            }
            if (cause instanceof ServiceException) {
                throw ((ServiceException) cause);
            }
            cause.printStackTrace();
            throw new ClientException("Unexpected exception!" + cause.getMessage());
        }
    }

    public static OSSAsyncTask wrapRequestTask(Future future, ExecutionContext executionContext) {
        OSSAsyncTask oSSAsyncTask = new OSSAsyncTask();
        oSSAsyncTask.future = future;
        oSSAsyncTask.context = executionContext;
        return oSSAsyncTask;
    }

    public void waitUntilFinished() {
        try {
            this.future.get();
        } catch (Exception unused) {
        }
    }
}