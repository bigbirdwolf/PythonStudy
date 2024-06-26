package com.alibaba.sdk.android.oss.common.auth;

/* loaded from: classes.dex */
public class OSSStsTokenCredentialProvider extends OSSCredentialProvider {
    private String accessKeyId;
    private String secretKeyId;
    private String securityToken;

    public OSSStsTokenCredentialProvider(String str, String str2, String str3) {
        this.accessKeyId = str.trim();
        this.secretKeyId = str2.trim();
        this.securityToken = str3.trim();
    }

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public void setAccessKeyId(String str) {
        this.accessKeyId = str;
    }

    public String getSecretKeyId() {
        return this.secretKeyId;
    }

    public void setSecretKeyId(String str) {
        this.secretKeyId = str;
    }

    public String getSecurityToken() {
        return this.securityToken;
    }

    public void setSecurityToken(String str) {
        this.securityToken = str;
    }

    public OSSFederationToken getFederationToken() {
        return new OSSFederationToken(this.accessKeyId, this.secretKeyId, this.securityToken, Long.MAX_VALUE);
    }
}