package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public class CreateBucketResult extends OSSResult {
    private String bucketLocation;

    public void setBucketLocation(String str) {
        this.bucketLocation = str;
    }

    public String getBucketLocation() {
        return this.bucketLocation;
    }
}