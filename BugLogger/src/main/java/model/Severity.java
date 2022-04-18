package model;

public enum Severity {
    TRIVIAL(1),
    LOW(2),
    MEDIUM(3),
    BLOCKER(4);
    private final int value;

    Severity(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }

    public static Severity valueOf(int i) {
        return switch (i) {
            case 1 -> TRIVIAL;
            case 2 -> LOW;
            case 3 -> MEDIUM;
            case 4 -> BLOCKER;
            default -> null;
        };
    }

    public String getSeverityString(){
        return switch (this.value) {
            case 1 -> "TRIVIAL";
            case 2 -> "LOW";
            case 3 -> "MEDIUM";
            case 4 -> "BLOCKER";
            default -> null;
        };
    }
}
