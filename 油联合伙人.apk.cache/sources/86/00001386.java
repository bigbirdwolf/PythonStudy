package com.umeng.commonsdk.statistics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.commonsdk.statistics.common.d;

/* compiled from: AnalyticsConfig.java */
/* loaded from: classes.dex */
public class a {
    public static String a = "native";
    public static String b = "";
    public static int c;
    private static String d;

    public static String a(Context context) {
        if (TextUtils.isEmpty(d)) {
            d = d.a(context).b();
        }
        return d;
    }
}