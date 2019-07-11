package apkkids.com.zhihu;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadInFor {
    public static void main(String[] args) {
        AtomicInteger index = new AtomicInteger(0);

        //1.thread in for
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                consumerTime();
                System.out.println("index:"+index.addAndGet(1)+", in thread:"+Thread.currentThread());
            }).start();
        }
    }

    private static void consumerTime() {
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            Math.sin(rand.nextDouble());
        }
    }
}
