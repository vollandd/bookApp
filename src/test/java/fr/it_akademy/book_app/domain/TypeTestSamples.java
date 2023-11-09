package fr.it_akademy.book_app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Type getTypeSample1() {
        return new Type().id(1L).nameType("nameType1");
    }

    public static Type getTypeSample2() {
        return new Type().id(2L).nameType("nameType2");
    }

    public static Type getTypeRandomSampleGenerator() {
        return new Type().id(longCount.incrementAndGet()).nameType(UUID.randomUUID().toString());
    }
}
