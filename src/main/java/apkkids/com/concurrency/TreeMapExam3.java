package apkkids.com.concurrency;

import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by wxb on 2017/9/18 0018.
 */
public class TreeMapExam3 {
    public static void main(String[] args) throws InterruptedException {
        TreeMap<Integer, String> map = new TreeMap<>();
        ExecutorService service = Executors.newCachedThreadPool();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            service.execute(new Putter(i, map));
        }
        for (int i = 0; i < 90; i++) {
            service.execute(new Getter(i, map));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        long end = System.currentTimeMillis();
        System.out.println("time span = " + (end - start) + ", map size = " + map.size());
    }

    private static class Putter implements Runnable {
        private final int num;
        private final TreeMap<Integer, String> map;
        private static Random rand = new Random(System.currentTimeMillis());

        private Putter(int num, TreeMap<Integer, String> map) {
            this.num = num;
            this.map = map;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                int key = rand.nextInt(200 * 100000); //在200万中随机产生key
                synchronized (map) {
                    map.put(key, "Alex" + key);
                }
            }
        }
    }

    private static class Getter implements Runnable {
        private final int num;
        private final TreeMap<Integer, String> map;
        private static Random rand = new Random(System.currentTimeMillis());

        private Getter(int num, TreeMap<Integer, String> map) {
            this.num = num;
            this.map = map;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                int key = rand.nextInt(200 * 100000); //在200万中随机产生key
                synchronized (map) {
                    map.get(key);
                }
            }
        }
    }
}
