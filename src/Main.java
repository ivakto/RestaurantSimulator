import service.RestaurantKitchen;
import service.RestaurantEngine;
import worker.Chef;
import worker.Client;
import utils.Statistics;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("РЕСТОРАНТЪТ ОТВАРЯ ВРАТИ!");

        RestaurantKitchen kitchen = new RestaurantKitchen();
        Client guestGenerator = new Client(kitchen);
        List<Chef> chefs = List.of(
                new Chef(kitchen, "Готвач Иван"),
                new Chef(kitchen, "Готвач Петър"),
                new Chef(kitchen, "Готвач Мария")
        );

        // 2. Инстанциране на помощните класове
        RestaurantEngine engine = new RestaurantEngine(kitchen, guestGenerator, chefs);
        Statistics printer = new Statistics();

        // 3. Изпълнение
        engine.runSimulation(15000); // Симулация за 15 секунди
        printer.printStatistics(guestGenerator, chefs);
    }
}