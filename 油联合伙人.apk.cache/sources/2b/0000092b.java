package com.alipay.android.phone.mrpc.core;

import android.content.Context;

/* loaded from: classes.dex */
public final class s {
    private static Boolean a;

    public static final boolean a(Context context) {
        if (a != null) {
            return a.booleanValue();
        }
        try {
            Boolean valueOf = Boolean.valueOf((context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).flags & 2) != 0);
            a = valueOf;
            return valueOf.booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }
}