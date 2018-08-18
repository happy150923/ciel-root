package com.ciel.framework.demo.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class ReferenceSample {

    public static void main(String[] args) {
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        Object counter = new Object();
        PhantomReference<Object> phantomReference = new PhantomReference<>(counter, referenceQueue);
        counter = null;
        System.gc();
        try {

            Reference<?> reference = referenceQueue.remove(100L);
            System.out.println(reference);
            System.out.println(phantomReference.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
