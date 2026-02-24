package u6pp;

import java.util.ArrayList;

public class Uno {
    private ArrayList<Player> players;
    private CardStack deck;
    private CardStack discard;
    private int currentPlayerIndex;
    private boolean reversed;
    private Player winner;

    public Uno(ArrayList<Player> players, CardStack deck, CardStack discard, int currentPlayerIndex, boolean reversed) {
        this.players = players;
        this.deck = deck;
        this.discard = discard;
        this.currentPlayerIndex = currentPlayerIndex;
        this.reversed = reversed;
        this.winner = null;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player getNextPlayer() {
        int nextIndex = currentPlayerIndex;
        if (reversed) {
            nextIndex = (nextIndex - 1 + players.size()) % players.size();
        } else {
            nextIndex = (nextIndex + 1) % players.size();
        }
        return players.get(nextIndex);
    }

    public Card getTopDiscard() {
        return discard.peek();
    }

    public Player getWinner() {
        return winner;
    }

    public boolean playCard(Card card, String color) {
        Player current = getCurrentPlayer();
        if (!current.getHand().contains(card)) {
            return false;
        }
        if (!card.canPlayOn(getTopDiscard())) {
            return false;
        }
        // Remove from hand
        current.getHand().remove(card);
        // Add to discard
        discard.push(card);
        // If wild, set color
        if (card.getValue().equals(Card.WILD) || card.getValue().equals(Card.WILD_DRAW_4)) {
            card.trySetColor(color);
        }
        // Check winner
        if (current.getHand().isEmpty()) {
            winner = current;
        }
        // Move to next player
        if (reversed) {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        } else {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
        return true;
    }
}