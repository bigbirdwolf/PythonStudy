package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;
import com.tencent.mm.opensdk.utils.Log;
import com.yltx.oil.partner.base.HttpStatusCodes;
import org.json.JSONObject;

/* loaded from: classes.dex */
final class f extends AsyncTask<Void, Void, a> {
    private OAuthListener l;
    private String o;
    private int u;
    private String url;

    /* loaded from: classes.dex */
    static class a {
        public OAuthErrCode n;
        public String v;
        public int w;

        a() {
        }

        public static a b(byte[] bArr) {
            OAuthErrCode oAuthErrCode;
            String str;
            String str2;
            Object[] objArr;
            OAuthErrCode oAuthErrCode2;
            a aVar = new a();
            if (bArr == null || bArr.length == 0) {
                Log.e("MicroMsg.SDK.NoopingResult", "parse fail, buf is null");
                oAuthErrCode = OAuthErrCode.WechatAuth_Err_NetworkErr;
            } else {
                try {
                    try {
                        JSONObject jSONObject = new JSONObject(new String(bArr, "utf-8"));
                        aVar.w = jSONObject.getInt("wx_errcode");
                        Log.d("MicroMsg.SDK.NoopingResult", String.format("nooping uuidStatusCode = %d", Integer.valueOf(aVar.w)));
                        int i = aVar.w;
                        if (i == 408) {
                            oAuthErrCode2 = OAuthErrCode.WechatAuth_Err_OK;
                        } else if (i != 500) {
                            switch (i) {
                                case HttpStatusCodes.CODE_402 /* 402 */:
                                    oAuthErrCode2 = OAuthErrCode.WechatAuth_Err_Timeout;
                                    break;
                                case HttpStatusCodes.CODE_403 /* 403 */:
                                    oAuthErrCode2 = OAuthErrCode.WechatAuth_Err_Cancel;
                                    break;
                                case 404:
                                    oAuthErrCode2 = OAuthErrCode.WechatAuth_Err_OK;
                                    break;
                                case HttpStatusCodes.CODE_405 /* 405 */:
                                    aVar.n = OAuthErrCode.WechatAuth_Err_OK;
                                    aVar.v = jSONObject.getString("wx_code");
                                    break;
                                default:
                                    oAuthErrCode2 = OAuthErrCode.WechatAuth_Err_NormalErr;
                                    break;
                            }
                            return aVar;
                        } else {
                            oAuthErrCode2 = OAuthErrCode.WechatAuth_Err_NormalErr;
                        }
                        aVar.n = oAuthErrCode2;
                        return aVar;
                    } catch (Exception e) {
                        str = "MicroMsg.SDK.NoopingResult";
                        str2 = "parse json fail, ex = %s";
                        objArr = new Object[]{e.getMessage()};
                        Log.e(str, String.format(str2, objArr));
                        oAuthErrCode = OAuthErrCode.WechatAuth_Err_NormalErr;
                        aVar.n = oAuthErrCode;
                        return aVar;
                    }
                } catch (Exception e2) {
                    str = "MicroMsg.SDK.NoopingResult";
                    str2 = "parse fail, build String fail, ex = %s";
                    objArr = new Object[]{e2.getMessage()};
                }
            }
            aVar.n = oAuthErrCode;
            return aVar;
        }
    }

    public f(String str, OAuthListener oAuthListener) {
        this.o = str;
        this.l = oAuthListener;
        this.url = String.format("https://long.open.weixin.qq.com/connect/l/qrconnect?f=json&uuid=%s", str);
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ a doInBackground(Void[] voidArr) {
        a aVar;
        OAuthErrCode oAuthErrCode;
        String str;
        if (this.o == null || this.o.length() == 0) {
            Log.e("MicroMsg.SDK.NoopingTask", "run fail, uuid is null");
            aVar = new a();
            oAuthErrCode = OAuthErrCode.WechatAuth_Err_NormalErr;
        } else {
            while (!isCancelled()) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.url);
                if (this.u == 0) {
                    str = "";
                } else {
                    str = "&last=" + this.u;
                }
                sb.append(str);
                String sb2 = sb.toString();
                long currentTimeMillis = System.currentTimeMillis();
                byte[] a2 = e.a(sb2, 60000);
                long currentTimeMillis2 = System.currentTimeMillis();
                a b = a.b(a2);
                Log.d("MicroMsg.SDK.NoopingTask", String.format("nooping, url = %s, errCode = %s, uuidStatusCode = %d, time consumed = %d(ms)", sb2, b.n.toString(), Integer.valueOf(b.w), Long.valueOf(currentTimeMillis2 - currentTimeMillis)));
                if (b.n != OAuthErrCode.WechatAuth_Err_OK) {
                    Log.e("MicroMsg.SDK.NoopingTask", String.format("nooping fail, errCode = %s, uuidStatusCode = %d", b.n.toString(), Integer.valueOf(b.w)));
                    return b;
                }
                this.u = b.w;
                if (b.w == g.UUID_SCANED.getCode()) {
                    this.l.onQrcodeScanned();
                } else if (b.w != g.UUID_KEEP_CONNECT.getCode() && b.w == g.UUID_CONFIRM.getCode()) {
                    if (b.v == null || b.v.length() == 0) {
                        Log.e("MicroMsg.SDK.NoopingTask", "nooping fail, confirm with an empty code!!!");
                        b.n = OAuthErrCode.WechatAuth_Err_NormalErr;
                    }
                    return b;
                }
            }
            Log.i("MicroMsg.SDK.NoopingTask", "IDiffDevOAuth.stopAuth / detach invoked");
            aVar = new a();
            oAuthErrCode = OAuthErrCode.WechatAuth_Err_Auth_Stopped;
        }
        aVar.n = oAuthErrCode;
        return aVar;
    }

    @Override // android.os.AsyncTask
    protected final /* synthetic */ void onPostExecute(a aVar) {
        a aVar2 = aVar;
        this.l.onAuthFinish(aVar2.n, aVar2.v);
    }
}