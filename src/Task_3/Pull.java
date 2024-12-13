import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;

public class Pull {
    public static void main(String[] args) {
        // Розмір пулу потоків
        int poolSize = 4;
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        // Масив чисел для перевірки на простоту
        int[] numbers = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};

        // ConcurrentHashMap для зберігання результатів
        ConcurrentHashMap<Integer, Boolean> results = new ConcurrentHashMap<>();

        // Обробка чисел
        for (int number : numbers) {
            executor.submit(() -> {
                boolean isPrime = isPrime(number);
                results.put(number, isPrime);
                System.out.println("Number " + number + " is prime: " + isPrime);
            });
        }

        // Завершення виконання задач
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        // Виведення результатів
        System.out.println("Results: " + results);
    }

    // Метод перевірки числа на простоту
    public static boolean isPrime(int number) {
        if (number < 2) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}
