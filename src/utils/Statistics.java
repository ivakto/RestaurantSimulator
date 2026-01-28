package utils;

import worker.Chef;
import worker.Client;

import java.util.List;

public class Statistics {

    public void printStatistics(Client client, List<Chef> chefList) {
        System.out.println("\nСТАТИСТИКА ЗА ДЕНЯ:");
        System.out.println("\nОбщо влезли клиенти: " + client.getTotalGuests());
        System.out.println("\nРабота на готвачите:");

        for (Chef chef : chefList) {
            System.out.printf(" - %-12s: %d ястия%n", chef.getName(), chef.getCookedCount());
        }
    }
}
