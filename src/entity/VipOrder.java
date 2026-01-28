package entity;

import java.util.concurrent.TimeUnit;

public class VipOrder extends BaseOrder {

    public VipOrder(String mealName) {
        super(mealName, OrderType.VIP);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return 0; // VIP клиентите не чакаме, те са готови веднага
    }
}
