import java.util.concurrent.*;

public class SquareCalculator {
    public static void main(String[] args) {
        // Розмір пулу потоків
        int poolSize = 3;
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        // Масив чисел для обчислення квадратів
        int[] numbers = {2, 4, 6, 8, 10};

        // Мапа Future для зберігання результатів
        ConcurrentHashMap<Integer, Future<Integer>> results = new ConcurrentHashMap<>();

        // Створення та запуск задач
        for (int number : numbers) {
            Future<Integer> future = executor.submit(new SquareTask(number));
            results.put(number, future);
        }

        // Отримання та виведення результатів
        results.forEach((number, future) -> {
            try {
                System.out.println("Square of " + number + " is: " + future.get());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error calculating square for " + number);
            }
        });

        // Завершення виконання задач
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    // Клас SquareTask реалізує Callable
    static class SquareTask implements Callable<Integer> {
        private final int number;

        public SquareTask(int number) {
            this.number = number;
        }

        @Override
        public Integer call() throws Exception {
            return calculateSquare(number);
        }

        private Integer calculateSquare(int n) {
            return n * n;
        }
    }
}
