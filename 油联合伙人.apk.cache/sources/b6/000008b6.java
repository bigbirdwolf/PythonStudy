package com.alibaba.sdk.android.oss.internal;

import com.alibaba.sdk.android.oss.common.HttpMethod;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.HttpHeaders;
import com.alibaba.sdk.android.oss.common.utils.HttpUtil;
import com.alibaba.sdk.android.oss.common.utils.HttpdnsMini;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class RequestMessage {
    private String bucketName;
    private OSSCredentialProvider credentialProvider;
    private String downloadFilePath;
    private URI endpoint;
    private HttpMethod method;
    private String objectKey;
    private long readStreamLength;
    private byte[] uploadData;
    private String uploadFilePath;
    private InputStream uploadInputStream;
    private boolean isAuthorizationRequired = true;
    private Map<String, String> headers = new HashMap();
    private Map<String, String> parameters = new LinkedHashMap();
    private boolean isHttpdnsEnable = true;

    public HttpMethod getMethod() {
        return this.method;
    }

    public void setMethod(HttpMethod httpMethod) {
        this.method = httpMethod;
    }

    public URI getEndpoint() {
        return this.endpoint;
    }

    public OSSCredentialProvider getCredentialProvider() {
        return this.credentialProvider;
    }

    public void setCredentialProvider(OSSCredentialProvider oSSCredentialProvider) {
        this.credentialProvider = oSSCredentialProvider;
    }

    public void setEndpoint(URI uri) {
        this.endpoint = uri;
    }

    public boolean isHttpdnsEnable() {
        return this.isHttpdnsEnable;
    }

    public void setIsHttpdnsEnable(boolean z) {
        this.isHttpdnsEnable = z;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public String getObjectKey() {
        return this.objectKey;
    }

    public void setObjectKey(String str) {
        this.objectKey = str;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Map<String, String> map) {
        if (map != null) {
            this.headers = map;
        }
    }

    public void addHeaders(Map<String, String> map) {
        if (map != null) {
            this.headers.putAll(map);
        }
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public void setParameters(Map<String, String> map) {
        this.parameters = map;
    }

    public byte[] getUploadData() {
        return this.uploadData;
    }

    public void setUploadData(byte[] bArr) {
        this.uploadData = bArr;
    }

    public String getUploadFilePath() {
        return this.uploadFilePath;
    }

    public void setUploadFilePath(String str) {
        this.uploadFilePath = str;
    }

    public String getDownloadFilePath() {
        return this.downloadFilePath;
    }

    public void setDownloadFilePath(String str) {
        this.downloadFilePath = str;
    }

    public boolean isAuthorizationRequired() {
        return this.isAuthorizationRequired;
    }

    public void setIsAuthorizationRequired(boolean z) {
        this.isAuthorizationRequired = z;
    }

    public void setUploadInputStream(InputStream inputStream, long j) {
        if (inputStream != null) {
            this.uploadInputStream = inputStream;
            this.readStreamLength = j;
        }
    }

    public InputStream getUploadInputStream() {
        return this.uploadInputStream;
    }

    public void createBucketRequestBodyMarshall(String str) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        if (str != null) {
            stringBuffer.append("<CreateBucketConfiguration>");
            stringBuffer.append("<LocationConstraint>" + str + "</LocationConstraint>");
            stringBuffer.append("</CreateBucketConfiguration>");
            byte[] bytes = stringBuffer.toString().getBytes("utf-8");
            setUploadInputStream(new ByteArrayInputStream(bytes), (long) bytes.length);
        }
    }

    public long getReadStreamLength() {
        return this.readStreamLength;
    }

    public String buildCanonicalURL() {
        OSSUtils.assertTrue(this.endpoint != null, "Endpoint haven't been set!");
        String scheme = this.endpoint.getScheme();
        String host = this.endpoint.getHost();
        if (!OSSUtils.isCname(host) && this.bucketName != null) {
            host = this.bucketName + "." + host;
        }
        String str = null;
        if (this.isHttpdnsEnable) {
            str = HttpdnsMini.getInstance().getIpByHostAsync(host);
        } else {
            OSSLog.logD("[buildCannonicalURL] - proxy exist, disable httpdns");
        }
        if (str == null) {
            str = host;
        }
        this.headers.put(HttpHeaders.HOST, host);
        String str2 = scheme + "://" + str;
        if (this.objectKey != null) {
            str2 = str2 + "/" + HttpUtil.urlEncode(this.objectKey, "utf-8");
        }
        String paramToQueryString = OSSUtils.paramToQueryString(this.parameters, "utf-8");
        if (OSSUtils.isEmptyString(paramToQueryString)) {
            return str2;
        }
        return str2 + "?" + paramToQueryString;
    }
}