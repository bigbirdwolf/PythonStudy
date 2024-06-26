package com.facebook.stetho.inspector.elements;

import com.facebook.stetho.common.ThreadBound;

/* loaded from: classes.dex */
public interface DocumentProviderFactory extends ThreadBound {
    DocumentProvider create();
}