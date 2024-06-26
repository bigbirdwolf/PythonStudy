package rx.internal.util;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class LinkedArrayList {
    final int capacityHint;
    Object[] head;
    int indexInTail;
    volatile int size;
    Object[] tail;

    public LinkedArrayList(int i) {
        this.capacityHint = i;
    }

    public void add(Object obj) {
        if (this.size == 0) {
            this.head = new Object[this.capacityHint + 1];
            this.tail = this.head;
            this.head[0] = obj;
            this.indexInTail = 1;
            this.size = 1;
        } else if (this.indexInTail == this.capacityHint) {
            Object[] objArr = new Object[this.capacityHint + 1];
            objArr[0] = obj;
            this.tail[this.capacityHint] = objArr;
            this.tail = objArr;
            this.indexInTail = 1;
            this.size++;
        } else {
            this.tail[this.indexInTail] = obj;
            this.indexInTail++;
            this.size++;
        }
    }

    public Object[] head() {
        return this.head;
    }

    public Object[] tail() {
        return this.tail;
    }

    public int size() {
        return this.size;
    }

    public int indexInTail() {
        return this.indexInTail;
    }

    public int capacityHint() {
        return this.capacityHint;
    }

    List<Object> toList() {
        int i = this.capacityHint;
        int i2 = this.size;
        ArrayList arrayList = new ArrayList(i2 + 1);
        Object[] head = head();
        int i3 = 0;
        while (true) {
            int i4 = 0;
            while (i3 < i2) {
                arrayList.add(head[i4]);
                i3++;
                i4++;
                if (i4 == i) {
                    break;
                }
            }
            return arrayList;
            head = head[i];
        }
    }

    public String toString() {
        return toList().toString();
    }
}