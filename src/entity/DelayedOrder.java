package entity;

import utils.Validator;

import java.util.concurrent.TimeUnit;

public class DelayedOrder extends BaseOrder {
    private final long startTime;

    public DelayedOrder(String mealName, long time, TimeUnit unit) {
        super(mealName, OrderType.DELAYED); // Гарантира, че поръчката получава своето специално id и типа
        // Смятаме кога трябва да се изпълни

        Validator.validateNotNull(unit, "Time Unit");
        Validator.validatePositive(time, "Време за закъснение");
        this.startTime = System.currentTimeMillis() + unit.toMillis(time);
        // 1. Взимам текущото системно време в милисекунди
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }
}
