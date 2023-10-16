public class Task1 {
    public static void main(String[] args) {
        Thread timeThread = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            while (true) {
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                System.out.println("\u001B[31mTime elapsed: " + elapsedTime + " seconds\u001B[0m");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread messageThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    System.out.println("\u001B[34m5 seconds have passed\u001B[0m");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        timeThread.start();
        messageThread.start();
    }
}



