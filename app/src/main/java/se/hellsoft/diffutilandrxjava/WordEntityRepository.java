package se.hellsoft.diffutilandrxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class WordEntityRepository {
    private static final int COUNT = 100;

    private static Random random = new Random();

    // Create a random list of Thing but with a consistent set of IDs.
    // Will just return a subset of the list to simulate removed items as well
    public static Flowable<List<WordEntity>> latestThings(long interval, TimeUnit timeUnit) {
        return Flowable
                .interval(0, interval, timeUnit, Schedulers.computation())
                .map(i -> shuffle(randomThings()).subList(0, (int) (COUNT * 0.8f)));
    }

    private static List<WordEntity> randomThings() {
        List<WordEntity> things = new ArrayList<>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            things.add(newThing(i));
        }
        return things;
    }

    private static List<WordEntity> shuffle(List<WordEntity> things) {
        List<WordEntity> shuffled = new ArrayList<>(things.size());
        while (!things.isEmpty()) {
            WordEntity thing = things.remove(random.nextInt(things.size()));
            shuffled.add(thing);
        }
        return shuffled;
    }

    private static WordEntity newThing(int id) {
        WordEntity.Builder builder = WordEntity.builder();

        builder.id(id);
        char first = (char) (random.nextInt(25) + 65);
        char second = (char) (random.nextInt(25) + 65);
        char third = (char) (random.nextInt(25) + 65);
        builder.text(String.valueOf(new char[]{first, second, third}));
        builder.color(random.nextInt());

        return builder.build();
    }
}
