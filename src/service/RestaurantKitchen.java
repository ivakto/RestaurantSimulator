package service;

import entity.BaseOrder;
import entity.DelayedOrder;
import entity.VipOrder;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class RestaurantKitchen implements KitchenService {
    private final LinkedBlockingQueue<BaseOrder> normalOrderQueue = new LinkedBlockingQueue<>();
    private final PriorityBlockingQueue<BaseOrder> vipOrderQueue = new PriorityBlockingQueue<>();
    private final DelayQueue<BaseOrder> delayOrderQueue = new DelayQueue<>();

    @Override
    public void submitOrder(BaseOrder order) {
        try {
            if (order instanceof VipOrder) {
                vipOrderQueue.put(order);
            } else if (order instanceof DelayedOrder) {
                delayOrderQueue.put(order);
            } else {
                normalOrderQueue.put(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    // С put(), нишката на производителя (Producer) ще влезе в състояние на изчакване (WAITING),
    // докато се освободи място. Това гарантира надеждност на данните. БЛОКИРАЩА ОПЕРАЦИЯ.

    @Override
    public BaseOrder getNextOrder() {
        BaseOrder order = vipOrderQueue.poll();

        if (order == null) {
            order = delayOrderQueue.poll();
        }

        if (order == null) {
            order = normalOrderQueue.poll();
        }

        return order;
    }
    // Тъй като моят дизайн включва няколко специализирани опашки (VIP, Delayed, Normal), готвачът (Consumer)
    // не трябва да зацикля на една от тях. poll() му позволява бързо да провери за приоритетни поръчки и, ако няма такива (null),
    // веднага да премине към следващата опашка. Това оптимизира производителността (throughput) на системата.
}
