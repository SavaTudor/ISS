package model;

public enum RoleType {
    TESTER(1),
    PROGRAMMER(2),
    ADMIN(3);
    private final int value;

    RoleType(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }

    public static RoleType valueOf(int i) {
        return switch (i) {
            case 1 -> TESTER;
            case 2 -> PROGRAMMER;
            case 3 -> ADMIN;
            default -> null;
        };
    }
}
