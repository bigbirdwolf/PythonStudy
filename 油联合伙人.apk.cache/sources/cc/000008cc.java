package com.alibaba.sdk.android.oss.model;

/* loaded from: classes.dex */
public enum CannedAccessControlList {
    Private("private"),
    PublicRead("public-read"),
    PublicReadWrite("public-read-write");
    
    private String ACLString;

    CannedAccessControlList(String str) {
        this.ACLString = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.ACLString;
    }

    public static CannedAccessControlList parseACL(String str) {
        CannedAccessControlList[] values;
        for (CannedAccessControlList cannedAccessControlList : values()) {
            if (cannedAccessControlList.toString().equals(str)) {
                return cannedAccessControlList;
            }
        }
        throw new IllegalArgumentException("Unable to parse the provided acl " + str);
    }
}