import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Stock {
    private int items;
    private final Lock lock = new ReentrantLock();

    public Stock(int initialItems) {
        this.items = initialItems;
    }

    public void takeItem(int cashierId) {
        lock.lock();
        try {
            if (items > 0) {
                System.out.println("Cashier " + cashierId + " is taking an item from stock.");
                items--;
                System.out.println("Items left in stock: " + items);
            } else {
                System.out.println("Cashier " + cashierId + " tried to take an item, but stock is empty.");
            }
        } finally {
            lock.unlock();
        }
    }
}

class Cashier implements Runnable {
    private final int cashierId;
    private final Stock stock;

    public Cashier(int cashierId, Stock stock) {
        this.cashierId = cashierId;
        this.stock = stock;
    }

    @Override
    public void run() {
        System.out.println("Cashier " + cashierId + " started serving a customer.");
        try {
            // Моделюємо час обслуговування клієнта (від 1 до 3 секунд)
            Thread.sleep((long) (Math.random() * 2000 + 1000));
        } catch (InterruptedException e) {
            System.out.println("Cashier " + cashierId + " was interrupted.");
        }
        stock.takeItem(cashierId);
        System.out.println("Cashier " + cashierId + " finished serving a customer.");
    }
}

public class SupermarketUpdate {
    public static void main(String[] args) {
        // Кількість кас у супермаркеті
        int numberOfCashiers = 5;

        // Початковий стан складу
        Stock stock = new Stock(10); // Наприклад, 10 товарів

        // Створення та запуск потоків
        for (int i = 1; i <= numberOfCashiers; i++) {
            Thread cashierThread = new Thread(new Cashier(i, stock));
            cashierThread.start();
        }
    }
}
