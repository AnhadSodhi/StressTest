package p1;

public class CPUTest extends Thread {
    public void run() {
        while (true) {
            double result = 0;
            for (int i = 0; i < 100000; i++) {
                result += Math.random();
            }
        }
    }
    
    public static void main(String[] args) {
        int numThreads = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < numThreads; i++) {
            CPUTest thread = new CPUTest();
            thread.start();
        }
        while (true);
    }
}
