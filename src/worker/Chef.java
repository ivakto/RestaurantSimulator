package worker;

import service.KitchenService;
import entity.BaseOrder;
import utils.Validator;

import java.util.concurrent.atomic.AtomicInteger;

public class Chef implements Runnable{
    private final KitchenService kitchen;
    private final String name;
    private final AtomicInteger cookedCount = new AtomicInteger(0);

    public Chef(KitchenService kitchen, String name) {
        this.kitchen = Validator.validateNotNull(kitchen, "KitchenService (за готвача)");
        this.name = Validator.validateString(name, "Име на готвача");
    }

    @Override
    public void run() {
        while (true) {
            try {
                BaseOrder orderToCook = kitchen.getNextOrder();
                if (orderToCook != null) {
                    System.out.println(this.name + ": Започвам -> " + orderToCook);
                    Thread.sleep(2000);
                    cookedCount.incrementAndGet();
                    System.out.println(this.name + ": ГОТОВО -> " + orderToCook.getMealName());
                } else {
                    Thread.sleep(500);
                }

            } catch (InterruptedException e) {
                System.out.println(this.name + " приключва смяната.");
                return;
            } catch (Exception e) {
                System.err.println("ГРЕШКА при " + this.name);
                e.printStackTrace();
            }
        }
    }

    public int getCookedCount() {
        return cookedCount.get();
    }

    public String getName() {
        return name;
    }
}

