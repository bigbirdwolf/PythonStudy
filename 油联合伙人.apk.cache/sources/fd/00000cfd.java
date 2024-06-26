package com.facebook.stetho.inspector.database;

import android.net.Uri;

/* loaded from: classes.dex */
public class ContentProviderSchema {
    private final String[] mProjection;
    private final String mTableName;
    private final Uri mUri;

    private ContentProviderSchema(Builder builder) {
        this.mTableName = builder.mTable.mTableName;
        this.mUri = builder.mTable.mUri;
        this.mProjection = builder.mTable.mProjection;
    }

    public String getTableName() {
        return this.mTableName;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public String[] getProjection() {
        return this.mProjection;
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private Table mTable;

        public Builder table(Table table) {
            this.mTable = table;
            return this;
        }

        public ContentProviderSchema build() {
            return new ContentProviderSchema(this);
        }
    }

    /* loaded from: classes.dex */
    public static class Table {
        private String[] mProjection;
        private String mTableName;
        private Uri mUri;

        private Table(Builder builder) {
            this.mUri = builder.mUri;
            this.mProjection = builder.mProjection;
            this.mTableName = builder.mTableName;
            if (this.mTableName == null) {
                this.mTableName = this.mUri.getLastPathSegment();
            }
        }

        /* loaded from: classes.dex */
        public static class Builder {
            private String[] mProjection;
            private String mTableName;
            private Uri mUri;

            public Builder uri(Uri uri) {
                this.mUri = uri;
                return this;
            }

            public Builder projection(String[] strArr) {
                this.mProjection = strArr;
                return this;
            }

            public Builder name(String str) {
                this.mTableName = str;
                return this;
            }

            public Table build() {
                return new Table(this);
            }
        }
    }
}