package entity;

import utils.Validator;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseOrder implements Delayed {
    // Това е базовият ни клас, имплементира Delayed (extendva Comparable), което е критично
    // за работата ни с DelayQueue.

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    // Важен елемент за синхронизация, всяка поръчка получава индекс ид от 1 и увеличаващо се с 1
    // с всяква следваща поръчка. Static - гарантира, че броячът се споделя между всички обекти

    private final String mealName;
    private final OrderType type;
    private final int id;

    protected BaseOrder(String mealName, OrderType type) {
        this.mealName = Validator.validateString(mealName, "Име на ястието");
        Validator.validatePositive(type.getPriorityValue(), "Приоритет");
        this.type = type;
        this.id = ID_GENERATOR.getAndIncrement();
    }
    // Защо protected - да бъде достъпен само за неговите деца, но скрива конструктора от всички останали класовр

    public String getMealName() {
        return mealName;
    }

    public OrderType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    // Нямаме setter-и - Thread Safety. Чрез final ние си гарантираме, че обека е thread-safe.
    // Ако нишката на готвача чете mealName, докато нишката на клиента се опитва да го смени
    // през setMealName(), може да се получи неочаквано поведение.


    // Абстракция на методите
    @Override
    public abstract long getDelay(TimeUnit unit);

    @Override
    public int compareTo(Delayed other) {
        if (other instanceof BaseOrder) {
            BaseOrder otherOrder = (BaseOrder) other;

            // Тука сравняваме по Priority от enum-a
            return Integer.compare(this.type.getPriorityValue(), otherOrder.type.getPriorityValue());
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s #%d", type, mealName, id);
    }
}
