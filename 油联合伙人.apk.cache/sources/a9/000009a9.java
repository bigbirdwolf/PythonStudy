package com.alipay.sdk.tid;

import android.text.TextUtils;

/* loaded from: classes.dex */
public final class Tid {
    private final String key;
    private final String tid;
    private final long time;

    public Tid(String str, String str2, long j) {
        this.tid = str;
        this.key = str2;
        this.time = j;
    }

    public String getTid() {
        return this.tid;
    }

    public String getTidSeed() {
        return this.key;
    }

    public long getTimestamp() {
        return this.time;
    }

    public static boolean isEmpty(Tid tid) {
        return tid == null || TextUtils.isEmpty(tid.tid);
    }
}