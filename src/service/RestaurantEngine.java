package service;

import utils.Validator;
import worker.Chef;
import worker.Client;
import java.util.List;
import java.util.concurrent.*;

public class RestaurantEngine {
    private final ExecutorService executor;
    private final RestaurantKitchen kitchen;
    private final Client guestGenerator;
    private final List<Chef> chefs;

    public RestaurantEngine(RestaurantKitchen kitchen, Client guestGenerator, List<Chef> chefs) {
        this.kitchen = Validator.validateNotNull(kitchen, "Kitchen service");
        this.guestGenerator = Validator.validateNotNull(guestGenerator, "Guest generator");
        this.chefs = Validator.validateNotNull(chefs, "Готвачите");
        this.executor = Executors.newFixedThreadPool(1 + chefs.size());
    }

    public void runSimulation(long durationMillis) {
        // Стартираме всичко
        Future<?> generatorHandle = executor.submit(guestGenerator);
        chefs.forEach(executor::submit);

        try {
            Thread.sleep(durationMillis);

            guestGenerator.stop();
            generatorHandle.cancel(true); // Прекъсваме генератора веднага

            // Изчакваме готвачите да изпразнят опашките
            while (!kitchen.isQueueEmpty()) {
                Thread.sleep(1000);
            }

            System.out.println("\nОпашките са празни. Изчакваме готвачите...");
            executor.shutdown();

            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }

        } catch (Exception e) {
            executor.shutdownNow();
        }
    }
}
