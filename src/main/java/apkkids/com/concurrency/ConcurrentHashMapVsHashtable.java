package apkkids.com.concurrency;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/8/16
 * Create Time: 20:42
 * Description:
 */
public class ConcurrentHashMapVsHashtable {
    private static int INPUT_NUMBER = 100000;

    public static void main(String[] args) throws InterruptedException {
        Map<Integer, String> map = new Hashtable<>(12 * INPUT_NUMBER);
//        Map<Integer, String> map = new ConcurrentHashMap<>(12 * INPUT_NUMBER);
        long begin = System.currentTimeMillis();
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            service.execute(new InputWorker(map, i));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        long end = System.currentTimeMillis();
        System.out.println("span time = "+(end-begin)+", map size = "+map.size());
    }

    private static class InputWorker implements Runnable {
        private static Random rand = new Random(System.currentTimeMillis());
        private final Map<Integer,String> map;
        private final int flag;

        private InputWorker(Map<Integer, String> map, int begin) {
            this.map = map;
            this.flag = begin;
        }

        @Override
        public void run() {
            int input = 0;
            while (input < INPUT_NUMBER) {
                int x = rand.nextInt();
                if (!map.containsKey(x)) {
                    map.put(x, "Alex Wang" + x);
                    input++;
                }
            }
            System.out.println("InputWorker"+flag+" is over.");
        }
    }
}
