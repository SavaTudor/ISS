package model;

public enum Status {
    NEW(1),
    OPEN(2),
    FIXED(3),
    CLOSED(4);
    private final int value;

    Status(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }

    public static Status valueOf(int i) {
        return switch (i) {
            case 1 -> NEW;
            case 2 -> OPEN;
            case 3 -> FIXED;
            case 4 -> CLOSED;
            default -> null;
        };
    }

    public String getStatusString(){
        return switch (this.value) {
            case 1 -> "NEW";
            case 2 -> "OPEN";
            case 3 -> "FIXED";
            case 4 -> "CLOSED";
            default -> null;
        };
    }
}
