package com.facebook.stetho.inspector;

import com.alipay.sdk.packet.e;
import com.facebook.stetho.common.LogRedirector;
import com.facebook.stetho.common.Util;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcException;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcPeer;
import com.facebook.stetho.inspector.jsonrpc.PendingRequest;
import com.facebook.stetho.inspector.jsonrpc.protocol.JsonRpcError;
import com.facebook.stetho.inspector.jsonrpc.protocol.JsonRpcRequest;
import com.facebook.stetho.inspector.jsonrpc.protocol.JsonRpcResponse;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.json.ObjectMapper;
import com.facebook.stetho.websocket.SimpleEndpoint;
import com.facebook.stetho.websocket.SimpleSession;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class ChromeDevtoolsServer implements SimpleEndpoint {
    public static final String PATH = "/inspector";
    private static final String TAG = "ChromeDevtoolsServer";
    private final MethodDispatcher mMethodDispatcher;
    private final Map<SimpleSession, JsonRpcPeer> mPeers = Collections.synchronizedMap(new HashMap());
    private final ObjectMapper mObjectMapper = new ObjectMapper();

    public ChromeDevtoolsServer(Iterable<ChromeDevtoolsDomain> iterable) {
        this.mMethodDispatcher = new MethodDispatcher(this.mObjectMapper, iterable);
    }

    @Override // com.facebook.stetho.websocket.SimpleEndpoint
    public void onOpen(SimpleSession simpleSession) {
        LogRedirector.d(TAG, "onOpen");
        this.mPeers.put(simpleSession, new JsonRpcPeer(this.mObjectMapper, simpleSession));
    }

    @Override // com.facebook.stetho.websocket.SimpleEndpoint
    public void onClose(SimpleSession simpleSession, int i, String str) {
        LogRedirector.d(TAG, "onClose: reason=" + i + " " + str);
        JsonRpcPeer remove = this.mPeers.remove(simpleSession);
        if (remove != null) {
            remove.invokeDisconnectReceivers();
        }
    }

    @Override // com.facebook.stetho.websocket.SimpleEndpoint
    public void onMessage(SimpleSession simpleSession, byte[] bArr, int i) {
        LogRedirector.d(TAG, "Ignoring binary message of length " + i);
    }

    @Override // com.facebook.stetho.websocket.SimpleEndpoint
    public void onMessage(SimpleSession simpleSession, String str) {
        if (LogRedirector.isLoggable(TAG, 2)) {
            LogRedirector.v(TAG, "onMessage: message=" + str);
        }
        try {
            JsonRpcPeer jsonRpcPeer = this.mPeers.get(simpleSession);
            Util.throwIfNull(jsonRpcPeer);
            handleRemoteMessage(jsonRpcPeer, str);
        } catch (MessageHandlingException e) {
            LogRedirector.i(TAG, "Message could not be processed by implementation: " + e);
            closeSafely(simpleSession, 1011, e.getClass().getSimpleName());
        } catch (IOException e2) {
            if (LogRedirector.isLoggable(TAG, 2)) {
                LogRedirector.v(TAG, "Unexpected I/O exception processing message: " + e2);
            }
            closeSafely(simpleSession, 1011, e2.getClass().getSimpleName());
        } catch (JSONException e3) {
            LogRedirector.v(TAG, "Unexpected JSON exception processing message", e3);
            closeSafely(simpleSession, 1011, e3.getClass().getSimpleName());
        }
    }

    private void closeSafely(SimpleSession simpleSession, int i, String str) {
        simpleSession.close(i, str);
    }

    private void handleRemoteMessage(JsonRpcPeer jsonRpcPeer, String str) throws IOException, MessageHandlingException, JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.has(e.q)) {
            handleRemoteRequest(jsonRpcPeer, jSONObject);
        } else if (jSONObject.has("result")) {
            handleRemoteResponse(jsonRpcPeer, jSONObject);
        } else {
            throw new MessageHandlingException("Improper JSON-RPC message: " + str);
        }
    }

    private void handleRemoteRequest(JsonRpcPeer jsonRpcPeer, JSONObject jSONObject) throws MessageHandlingException {
        JSONObject jSONObject2;
        JSONObject jSONObject3;
        String jSONObject4;
        JsonRpcRequest jsonRpcRequest = (JsonRpcRequest) this.mObjectMapper.convertValue(jSONObject, JsonRpcRequest.class);
        try {
            jSONObject3 = this.mMethodDispatcher.dispatch(jsonRpcPeer, jsonRpcRequest.method, jsonRpcRequest.params);
            jSONObject2 = null;
        } catch (JsonRpcException e) {
            logDispatchException(e);
            jSONObject2 = (JSONObject) this.mObjectMapper.convertValue(e.getErrorMessage(), JSONObject.class);
            jSONObject3 = null;
        }
        if (jsonRpcRequest.id != null) {
            JsonRpcResponse jsonRpcResponse = new JsonRpcResponse();
            jsonRpcResponse.id = jsonRpcRequest.id.longValue();
            jsonRpcResponse.result = jSONObject3;
            jsonRpcResponse.error = jSONObject2;
            try {
                jSONObject4 = ((JSONObject) this.mObjectMapper.convertValue(jsonRpcResponse, JSONObject.class)).toString();
            } catch (OutOfMemoryError e2) {
                jsonRpcResponse.result = null;
                jsonRpcResponse.error = (JSONObject) this.mObjectMapper.convertValue(e2.getMessage(), JSONObject.class);
                jSONObject4 = ((JSONObject) this.mObjectMapper.convertValue(jsonRpcResponse, JSONObject.class)).toString();
            }
            jsonRpcPeer.getWebSocket().sendText(jSONObject4);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.facebook.stetho.inspector.ChromeDevtoolsServer$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$stetho$inspector$jsonrpc$protocol$JsonRpcError$ErrorCode = new int[JsonRpcError.ErrorCode.values().length];

        static {
            try {
                $SwitchMap$com$facebook$stetho$inspector$jsonrpc$protocol$JsonRpcError$ErrorCode[JsonRpcError.ErrorCode.METHOD_NOT_FOUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    private static void logDispatchException(JsonRpcException jsonRpcException) {
        JsonRpcError errorMessage = jsonRpcException.getErrorMessage();
        if (AnonymousClass1.$SwitchMap$com$facebook$stetho$inspector$jsonrpc$protocol$JsonRpcError$ErrorCode[errorMessage.code.ordinal()] == 1) {
            LogRedirector.d(TAG, "Method not implemented: " + errorMessage.message);
            return;
        }
        LogRedirector.w(TAG, "Error processing remote message", jsonRpcException);
    }

    private void handleRemoteResponse(JsonRpcPeer jsonRpcPeer, JSONObject jSONObject) throws MismatchedResponseException {
        JsonRpcResponse jsonRpcResponse = (JsonRpcResponse) this.mObjectMapper.convertValue(jSONObject, JsonRpcResponse.class);
        PendingRequest andRemovePendingRequest = jsonRpcPeer.getAndRemovePendingRequest(jsonRpcResponse.id);
        if (andRemovePendingRequest == null) {
            throw new MismatchedResponseException(jsonRpcResponse.id);
        }
        if (andRemovePendingRequest.callback != null) {
            andRemovePendingRequest.callback.onResponse(jsonRpcPeer, jsonRpcResponse);
        }
    }

    @Override // com.facebook.stetho.websocket.SimpleEndpoint
    public void onError(SimpleSession simpleSession, Throwable th) {
        LogRedirector.e(TAG, "onError: ex=" + th.toString());
    }
}