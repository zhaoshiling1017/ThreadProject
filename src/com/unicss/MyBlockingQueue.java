package com.unicss;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
public class MyBlockingQueue extends Thread {
public static BlockingQueue<String> queue = new LinkedBlockingQueue<String>(3);
private int index;
public MyBlockingQueue(int i) {
   this.index = i;
}
public void run() {
   try {
    queue.put(String.valueOf(this.index));
    System.out.println("{" + this.index + "} in queue!");
   } catch (Exception e) {
    e.printStackTrace();
   }
}
public static void main(String args[]) {
   ExecutorService service = Executors.newCachedThreadPool();
   for (int i = 0; i < 10; i++) {
    service.submit(new MyBlockingQueue(i));
   }
   Thread thread = new Thread() {
    public void run() {
     try {
      while (true) {
       Thread.sleep((int) (Math.random() * 1000));
       System.out.println("======="+MyBlockingQueue.queue.size());
       if(MyBlockingQueue.queue.isEmpty())
        break;
       String str = MyBlockingQueue.queue.take();
       System.out.println(str + " has take!");
      }
     } catch (Exception e) {
      e.printStackTrace();
     }
    }
   };
   service.submit(thread);
   service.shutdown();
}
}