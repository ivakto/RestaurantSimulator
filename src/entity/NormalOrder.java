package entity;

import java.util.concurrent.TimeUnit;

public class NormalOrder extends BaseOrder {

    public NormalOrder(String mealName) {
        super(mealName, OrderType.NORMAL);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return 0; // Нормалните също са готови за обработка веднага
    }
}
