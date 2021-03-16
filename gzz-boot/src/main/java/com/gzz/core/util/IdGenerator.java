package com.gzz.core.util;

import com.google.common.base.Preconditions;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ID生成器
 */
public class IdGenerator {
    public static final long SJDBC_EPOCH;
    //    private static final long SEQUENCE_BITS = 12L;
//    private static final long WORKER_ID_BITS = 10L;
//    private static final long SEQUENCE_MASK = 4095L;
//    private static final long WORKER_ID_LEFT_SHIFT_BITS = 12L;
//    private static final long TIMESTAMP_LEFT_SHIFT_BITS = 22L;
//    private static final long WORKER_ID_MAX_VALUE = 1024L;
    private static long workerId = 100L;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 0, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        SJDBC_EPOCH = calendar.getTimeInMillis();
    }

    private long sequence;
    private long lastTime;

    /**
     * @param args
     */
    public static void main(String[] args) {
        IdGenerator idGenerator = new IdGenerator();

        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        for (int i = 0; i < 100; i++) {
                            System.out.println("insert into orderdemo(id) values(" + idGenerator.generateId() + "); ");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
            latch.countDown();
        }
        service.shutdown();

        System.out.println(String.valueOf(SJDBC_EPOCH));
        Map<String, String> map = System.getenv();
        System.out.println(System.getenv("ALLUSERSPROFILE"));
        System.out.println(System.getenv("TNS_ADMIN"));
        System.out.println(map);


//        for (int i = 0; i < 1025; i++) {
//            System.out.println(idGenerator.generateId() + " - " + idGenerator.getSequence() + " - " + idGenerator.getLastTime());
//        }

        /**
         *
         */
        for (Iterator<String> it = map.keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            System.out.println(key + "=" + map.get(key));
        }

    }

    /**
     * 生成序号
     *
     * @return
     */
    public synchronized Long generateId() {
        long time = System.currentTimeMillis();
        Preconditions.checkState(this.lastTime <= time, "Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds", this.lastTime, time);
        if (this.lastTime == time) {
            if (0L == (this.sequence = ++this.sequence & 4095L)) {
                time = this.waitUntilNextTime(time);
            }
        } else {
            this.sequence = 0L;
        }

        this.lastTime = time;
//        if (log.isDebugEnabled()) {
//            log.debug("{}-{}-{}", new Object[]{(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new Date(this.lastTime)), workerId, this.sequence});
//        }

        return time - SJDBC_EPOCH << 22 | workerId << 12 | this.sequence;
    }

    /**
     * 生成指定前缀的序号
     *
     * @param prefix
     * @return
     */
    public String generateNo(String prefix) {
        return prefix + this.generateId();
    }

    private long waitUntilNextTime(long lastTime) {
        long time;
        for (time = System.currentTimeMillis(); time <= lastTime; time = System.currentTimeMillis()) {
        }

        return time;
    }

    public long getSequence() {
        return this.sequence;
    }

    public long getLastTime() {
        return this.lastTime;
    }
}
