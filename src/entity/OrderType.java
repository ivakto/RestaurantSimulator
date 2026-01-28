package entity;

public enum OrderType {
    VIP(1),         // Най-важен (излиза най-отпред)
    DELAYED(2),     // С предимство пред случайните клиенти
    NORMAL(3);      // Най-нисък приоритет

    private final int priorityValue;

    OrderType(int priorityValue) {
        this.priorityValue = priorityValue;
    }

    public int getPriorityValue() {
        return priorityValue;
    }
}
