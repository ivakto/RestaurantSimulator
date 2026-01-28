package worker;

import service.KitchenService;
import entity.BaseOrder;
import utils.OrderFactory;
import utils.Validator;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Client implements Runnable {
    private final KitchenService kitchen; // Зависим от абстракция
    private final AtomicInteger guestCounter = new AtomicInteger(0);

    public Client(KitchenService kitchen) {
        this.kitchen = Validator.validateNotNull(kitchen, "KitchenService (за клиента)");
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                int guestId = guestCounter.incrementAndGet();

                String platform = (Math.random() > 0.4) ? "На място" : "Online";
                BaseOrder order = OrderFactory.generateRandomOrder(platform);

                System.out.printf("Клиент #%d (%s): Направи поръчка -> %s%n",
                        guestId, platform, order);

                kitchen.submitOrder(order);
                Thread.sleep((long) (800 + Math.random() * 1200));

            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public int getTotalGuests() {
        return guestCounter.get();
    }
}

