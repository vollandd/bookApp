package fr.it_akademy.book_app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EditorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Editor getEditorSample1() {
        return new Editor().id(1L).editorName("editorName1");
    }

    public static Editor getEditorSample2() {
        return new Editor().id(2L).editorName("editorName2");
    }

    public static Editor getEditorRandomSampleGenerator() {
        return new Editor().id(longCount.incrementAndGet()).editorName(UUID.randomUUID().toString());
    }
}
