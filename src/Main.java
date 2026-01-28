import worker.Chef;
import worker.Client;
import service.KitchenService;
import service.RestaurantKitchen;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("РЕСТОРАНТЪТ ОТВАРЯ ВРАТИ!");

        KitchenService kitchen = new RestaurantKitchen();

        ExecutorService executor = Executors.newFixedThreadPool(4);

        Client guestGenerator = new Client(kitchen);
        Chef chef1 = new Chef(kitchen, "Готвач Иван");
        Chef chef2 = new Chef(kitchen, "Готвач Петър");
        Chef chef3 = new Chef(kitchen, "Готвач Мария");

        executor.submit(guestGenerator);
        executor.submit(chef1);
        executor.submit(chef2);
        executor.submit(chef3);

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nВРЕМЕ Е ЗА ЗАТВАРЯНЕ! Спираме приемането на поръчки...");
        executor.shutdownNow();
        try {
            if (executor.awaitTermination(2, TimeUnit.SECONDS)) {
                System.out.println("\nСТАТИСТИКА ЗА ДЕНЯ:");
                System.out.println("Общо влезли клиенти: " + guestGenerator.getTotalGuests());
                System.out.println("Работа на готвачите:");
                System.out.println(" - " + chef1.getName() + ": " + chef1.getCookedCount() + " ястия");
                System.out.println(" - " + chef2.getName() + ": " + chef2.getCookedCount() + " ястия");
                System.out.println(" - " + chef3.getName() + ": " + chef3.getCookedCount() + " ястия");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

