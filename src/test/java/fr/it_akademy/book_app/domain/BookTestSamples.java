package fr.it_akademy.book_app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BookTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Book getBookSample1() {
        return new Book().id(1L).bookName("bookName1");
    }

    public static Book getBookSample2() {
        return new Book().id(2L).bookName("bookName2");
    }

    public static Book getBookRandomSampleGenerator() {
        return new Book().id(longCount.incrementAndGet()).bookName(UUID.randomUUID().toString());
    }
}
