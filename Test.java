import java.util.concurrent.Semaphore;

class Communicate {
    static Semaphore odd = new Semaphore(1);
    static Semaphore even = new Semaphore(0);
}

class ForwardPrint extends Thread {
    int start;
    final int MAX;
    SharedPrinter sp;

    ForwardPrint(SharedPrinter sp, int mx) {
        start = 1;
        MAX = mx;
        this.sp = sp;
        // count = t;
    }

    public void run() {
        while (start <= MAX) {
            try {
                sp.oddPrint(start);
                start++;
            } catch (Exception e) {
                e.printStackTrace();

            }
            // Communicate.even.release();
        }

    }
}

class BackwardPrint extends Thread {
    int start;
    // static Integer count;
    final int MIN;
    SharedPrinter sp;

    BackwardPrint(SharedPrinter sp, int min) {
        start = 10;
        this.sp = sp;
        MIN = min;
        // count = t;
    }

    public void run() {
        while (start >= MIN) {
            try {
                sp.evenPrint(start);
                start--;
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Communicate.odd.release();
        }
    }

}

class SharedPrinter {
    Semaphore odd = new Semaphore(1);
    Semaphore even = new Semaphore(0);

    public SharedPrinter() {
    }

    public void oddPrint(int p) {
        try {
            odd.acquire();
            System.out.println(p + " " + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        even.release();
    }

    public void evenPrint(int p) {
        try {
            even.acquire();
            System.out.println(p + " " + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        odd.release();
    }

}

public class Test {
    public static void main(String args[]) {
        SharedPrinter sp = new SharedPrinter();
        ForwardPrint t1 = new ForwardPrint(sp, 10);
        BackwardPrint t2 = new BackwardPrint(sp, 1);
        t2.start();
        t1.start();
    }
}