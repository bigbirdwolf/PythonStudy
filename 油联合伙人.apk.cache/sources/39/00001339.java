package com.umeng.commonsdk.proguard;

import java.util.BitSet;

/* compiled from: TTupleProtocol.java */
/* loaded from: classes.dex */
public final class ao extends ac {

    /* compiled from: TTupleProtocol.java */
    /* loaded from: classes.dex */
    public static class a implements ak {
        @Override // com.umeng.commonsdk.proguard.ak
        public ai a(aw awVar) {
            return new ao(awVar);
        }
    }

    public ao(aw awVar) {
        super(awVar);
    }

    @Override // com.umeng.commonsdk.proguard.ai
    public Class<? extends aq> D() {
        return at.class;
    }

    public void a(BitSet bitSet, int i) throws p {
        for (byte b : b(bitSet, i)) {
            a(b);
        }
    }

    public BitSet b(int i) throws p {
        double d = i;
        Double.isNaN(d);
        int ceil = (int) Math.ceil(d / 8.0d);
        byte[] bArr = new byte[ceil];
        for (int i2 = 0; i2 < ceil; i2++) {
            bArr[i2] = u();
        }
        return a(bArr);
    }

    public static BitSet a(byte[] bArr) {
        BitSet bitSet = new BitSet();
        for (int i = 0; i < bArr.length * 8; i++) {
            if ((bArr[(bArr.length - (i / 8)) - 1] & (1 << (i % 8))) > 0) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    public static byte[] b(BitSet bitSet, int i) {
        double d = i;
        Double.isNaN(d);
        byte[] bArr = new byte[(int) Math.ceil(d / 8.0d)];
        for (int i2 = 0; i2 < bitSet.length(); i2++) {
            if (bitSet.get(i2)) {
                int length = (bArr.length - (i2 / 8)) - 1;
                bArr[length] = (byte) ((1 << (i2 % 8)) | bArr[length]);
            }
        }
        return bArr;
    }
}