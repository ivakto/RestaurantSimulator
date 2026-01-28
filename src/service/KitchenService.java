package service;

import entity.BaseOrder;

public interface KitchenService {

    void submitOrder(BaseOrder order); // Метод за клиента

    BaseOrder getNextOrder(); // Метод за готвача
}
