class Cashier implements Runnable {
    private final int cashierId;

    public Cashier(int cashierId) {
        this.cashierId = cashierId;
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
        System.out.println("Cashier " + cashierId + " finished serving a customer.");
    }
}

public class Supermarket {
    public static void main(String[] args) {
        // Кількість кас у супермаркеті
        int numberOfCashiers = 5;

        // Створення та запуск потоків
        for (int i = 1; i <= numberOfCashiers; i++) {
            Thread cashierThread = new Thread(new Cashier(i));
            cashierThread.start();
        }
    }
}
