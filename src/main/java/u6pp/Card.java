package u6pp;

public class Card {

    public static String RED = "RED";
    public static String GREEN = "GREEN";
    public static String BLUE = "BLUE";
    public static String YELLOW = "YELLOW";

    public static String ZERO = "0";
    public static String ONE = "1";
    public static String TWO = "2";
    public static String THREE = "3";
    public static String FOUR = "4";
    public static String FIVE = "5";
    public static String SIX = "6";
    public static String SEVEN = "7";
    public static String EIGHT = "8";
    public static String NINE = "9";

    public static String DRAW_2 = "DRAW_2";
    public static String REVERSE = "REVERSE";
    public static String SKIP = "SKIP";
    public static String WILD = "WILD";
    public static String WILD_DRAW_4 = "WILD_DRAW_4";

    // Wild color is the default color for wilds, before they are played. 
    public static String[] COLORS = {RED, GREEN, BLUE, YELLOW, WILD}; 
    public static String[] VALUES = {ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, 
        DRAW_2, REVERSE, SKIP, WILD, WILD_DRAW_4};

    private String color;
    private String value;

    public Card(String color, String value) {
        this.color = color;
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }

    public boolean trySetColor(String newColor) {
        if ((this.value.equals(WILD) || this.value.equals(WILD_DRAW_4)) && isValidColor(newColor) && !newColor.equals(WILD)) {
            this.color = newColor;
            return true;
        }
        return false;
    }

    private boolean isValidColor(String color) {
        return color != null && (color.equals(RED) || color.equals(GREEN) || color.equals(BLUE) || color.equals(YELLOW));
    }

    public boolean canPlayOn(Card other) {
        if (other == null) {
            return false;
        }
        // Wild cards can be played on anything
        if (this.value.equals(WILD) || this.value.equals(WILD_DRAW_4)) {
            return true;
        }
        // Same color
        if (this.color.equals(other.color)) {
            return true;
        }
        // Same value
        if (this.value.equals(other.value)) {
            return true;
        }
        // If other is wild and has color set (not WILD)
        if ((other.value.equals(WILD) || other.value.equals(WILD_DRAW_4)) && !other.color.equals(WILD)) {
            return this.color.equals(other.color);
        }
        return false;
    }

}
