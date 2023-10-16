import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task2 {
    private int n;
    private BlockingQueue<String> queue;
    private int current;
    private Lock lock;
    private Condition condition;
    private List<String> output;

    public Task2(int n) {
        this.n = n;
        this.queue = new LinkedBlockingQueue<>();
        this.current = 1;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        this.output = new ArrayList<>();
    }

    public void fizz() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                if (current > n) {
                    return;
                }
                if (current % 3 == 0 && current % 5 != 0) {
                    queue.put("fizz");
                    current++;
                    condition.signalAll();
                } else {
                    condition.await();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public void buzz() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                if (current > n) {
                    return;
                }
                if (current % 3 != 0 && current % 5 == 0) {
                    queue.put("buzz");
                    current++;
                    condition.signalAll();
                } else {
                    condition.await();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public void fizzbuzz() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                if (current > n) {
                    return;
                }
                if (current % 3 == 0 && current % 5 == 0) {
                    queue.put("fizzbuzz");
                    current++;
                    condition.signalAll();
                } else {
                    condition.await();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public void number() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                if (current > n) {
                    return;
                }
                if (current % 3 != 0 && current % 5 != 0) {
                    queue.put(Integer.toString(current));
                    current++;
                    condition.signalAll();
                } else {
                    condition.await();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        int n = 15;
        Task2 task = new Task2(n);

        Thread threadA = new Thread(() -> {
            try {
                task.fizz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                task.buzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                task.fizzbuzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                task.number();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!task.queue.isEmpty()) {
            try {
                task.output.add(task.queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String result = String.join(", ", task.output);
        System.out.println(result);
    }
}