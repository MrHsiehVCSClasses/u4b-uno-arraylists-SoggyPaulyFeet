package u6pp;

import java.util.ArrayList;

//Makes a Player in the game and has a name and a hand of cards
public class Player {
    private String name;
    private ArrayList<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    //gets the hand of the player
    public ArrayList<Card> getHand() {
        return hand;
    }
}