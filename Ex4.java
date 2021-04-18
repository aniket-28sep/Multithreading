class Test1 {
    static private int value;

    synchronized static public void add(int num) {
        value += num;
        System.out.println("THREAD :" + Thread.currentThread().getName() + " " + value);
    }

    public static void decrement() {
        synchronized (this) {
            value--;
            System.out.println("THREAD :" + Thread.currentThread().getName() + " " + value);
        }
    }
}

class Adding extends Thread {

    public void run() {
        for (int i = 0; i < 10; i++) {
            Test1.add(1);

            // try {
            // Thread.sleep(10);
            // } catch (Exception e) {
            // e.printStackTrace();
            // }
        }
    }
}

public class Ex4 {
    public static void main(String[] args) {
        Thread t1 = new Adding();
        Thread t2 = new Adding();
        Thread t3 = new Adding();
        t1.start();
        t2.start();
        t3.start();
    }
}
