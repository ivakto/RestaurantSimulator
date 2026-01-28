package utils;

import entity.BaseOrder;
import entity.DelayedOrder;
import entity.NormalOrder;
import entity.VipOrder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OrderFactory {

    private static final Random random = new Random();
    private static final List<String> MENU = Arrays.asList(
            "Класически Бургер", "Пица Маргарита", "Цезар Салата",
            "Сьомга със сос", "Телешки Стек", "Омар",
            "Суши Сет Делукс", "Китайска храна", "Ризото с гъби",
            "Паста Карбонара", "Пилешки крилца", "Шопска салата"
    );

    private OrderFactory() {
    }

    public static BaseOrder generateRandomOrder(String platform) {
        String meal = MENU.get(random.nextInt(MENU.size()));
        if (platform.equals("Online")) {
            return new DelayedOrder(meal, 3, TimeUnit.SECONDS);
        } else {
            int chance = random.nextInt(100);
            if (chance < 40) {
                return new VipOrder(meal);
            } else {
                return new NormalOrder(meal);
            }
        }
    }
}
