package com.umeng.commonsdk.framework;

import android.content.Context;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class UMEnvelopeBuild {
    public static boolean isOnline(Context context) {
        return UMFrUtils.isOnline(context);
    }

    public static boolean isReadyBuild(Context context, UMLogDataProtocol.UMBusinessType uMBusinessType) {
        return a.a(context, uMBusinessType);
    }

    public static JSONObject buildEnvelopeWithExtHeader(Context context, JSONObject jSONObject, JSONObject jSONObject2) {
        return a.a(context, jSONObject, jSONObject2);
    }

    public static String imprintProperty(Context context, String str, String str2) {
        return a.a(context, str, str2);
    }

    public static long maxDataSpace(Context context) {
        return a.c(context);
    }

    public static long lastSuccessfulBuildTime(Context context) {
        return a.a(context);
    }

    public static long lastInstantBuildTime(Context context) {
        return a.b(context);
    }
}