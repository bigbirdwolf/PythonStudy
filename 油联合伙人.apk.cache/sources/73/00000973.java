package com.alipay.sdk.app.statistic;

import android.content.Context;

/* loaded from: classes.dex */
public class a {
    public static final String a = "alipay_cashier_statistic_record";
    private static c b;

    public static void a(Context context) {
        if (b != null) {
            return;
        }
        b = new c(context);
    }

    public static void a(Context context, String str) {
        new Thread(new b(context, str)).start();
    }

    public static synchronized void b(Context context, String str) {
        synchronized (a.class) {
            if (b == null) {
                return;
            }
            a(context, b.a(str));
            b = null;
        }
    }

    public static void a(String str, Throwable th) {
        if (b == null || th == null || th.getClass() == null) {
            return;
        }
        b.a(str, th.getClass().getSimpleName(), th);
    }

    public static void a(String str, String str2, Throwable th, String str3) {
        if (b == null) {
            return;
        }
        b.a(str, str2, th, str3);
    }

    public static void a(String str, String str2, Throwable th) {
        if (b == null) {
            return;
        }
        b.a(str, str2, th);
    }

    public static void a(String str, String str2, String str3) {
        if (b == null) {
            return;
        }
        b.a(str, str2, str3);
    }

    public static void a(Context context, String str, String str2, String str3) {
        if (context == null) {
            return;
        }
        try {
            c cVar = new c(context);
            cVar.a(str, str2, str3);
            a(context, cVar.a(""));
        } catch (Throwable th) {
            com.alipay.sdk.util.c.a(th);
        }
    }
}