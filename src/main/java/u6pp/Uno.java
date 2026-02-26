package u6pp;

import java.util.ArrayList;
//the main class for uno
public class Uno {
    private ArrayList<Player> players;
    private CardStack deck;
    private CardStack discard;
    private int currentPlayerIndex;
    private boolean reversed;
// the test constructor which takes in all parameters
    public Uno(ArrayList<Player> players, CardStack deck, CardStack discard, int currentPlayerIndex, boolean reversed) {
        this.players = players;
        this.deck = deck;
        this.discard = discard;
        this.currentPlayerIndex = currentPlayerIndex;
        this.reversed = reversed;
    }


//making the constructor that takes in the number of players and makes the player hands
    public Uno(int numPlayers) {
        players = new ArrayList<Player>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player("p " + (i + 1)));
        }
        deck = new CardStack();
        discard = new CardStack();
        currentPlayerIndex = 0;
        reversed = false;

        buildDeck(deck);
        deck.shuffle();

        for (int c = 0; c < 7; c++) {
            for (Player p : players) {
                p.getHand().add(drawOne());
            }
        }
        // Start discard pile with a non-action card
        discard.push(drawOne());
        if (discard.peek() == null) {
            discard.push(new Card(Card.RED, Card.ZERO));
        }
    }
//getting the amount of players
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player getNextPlayer() {
        return players.get(getNextPlayerIndex());
    }
    public Card getTopDiscard() {
        return discard.peek();
    }

    public Player getWinner() {
        for (Player p : players) {
            if (p.getHand().size() == 0) return p;
        }
        return null;
    }

    public boolean playCard(Card cardToPlay, String chosenColor) {
        
        if (cardToPlay == null) {
            getCurrentPlayer().getHand().add(drawOne());
            advanceTurn(1);
            return false;
        }

        if (!getCurrentPlayer().getHand().contains(cardToPlay)) {
            return false;
        }

        Card top = getTopDiscard();

        boolean canPlay = cardToPlay.canPlayOn(top);

        if (!canPlay) {            
            return false;
        }

        getCurrentPlayer().getHand().remove(cardToPlay);
        discard.push(cardToPlay);

        if (isWildValue(cardToPlay.getValue())) {
            cardToPlay.trySetColor(chosenColor);
        }

        String v = cardToPlay.getValue();

        if (Card.REVERSE.equals(v)) {
            reversed = !reversed;
            advanceTurn(1);
            return true;
        }

        if (Card.SKIP.equals(v)) {
            advanceTurn(2);
            return true;
        }

        if (Card.DRAW_2.equals(v)) {
            Player victim = getNextPlayer();
            victim.getHand().add(drawOne());
            victim.getHand().add(drawOne());
            advanceTurn(2);
            return true;
        }
        if (Card.WILD_DRAW_4.equals(v)) {
            Player victim = getNextPlayer();
            for (int i = 0; i < 4; i++) {
                victim.getHand().add(drawOne());
            }
            advanceTurn(2);
            return true;
        }

        advanceTurn(1);
        return true;


    }

// creating all the helper methods that we need in order for the game and instances to work at the top.
    private int getNextPlayerIndex() {
        int n = players.size();
        if (!reversed) {
            return (currentPlayerIndex + 1) % n;
        } else {
            return (currentPlayerIndex - 1 + n) % n;
        }
    }
    private void advanceTurn (int steps) {
        for (int i = 0; i < steps; i++) {
            currentPlayerIndex = getNextPlayerIndex();
        }
    }

    private static boolean isWildValue (String v) {
        return Card.WILD.equals(v) || Card.WILD_DRAW_4.equals(v);
    }

    private Card drawOne() {
        Card c = deck.pop();

        if (c == null) {
            refillDeckFromDiscard();
            c = deck.pop();
        }

        if (deck.isEmpty()) {
            refillDeckFromDiscard();
        }
        return c;
    }

    private void refillDeckFromDiscard() {
        if (discard.getSize() <= 1) return;
        Card topDiscard = discard.pop();

        while (!discard.isEmpty()) {
            deck.push(discard.pop());
        }

        deck.shuffle();
        discard.push(topDiscard);
    }

    //building the decks or uno for all colors, with power ups and wild cards.
    private static void buildDeck(CardStack d) {
        String [] colors = {Card.RED, Card.GREEN, Card.BLUE, Card.YELLOW};

        for (String c : colors) {
            d.push(new Card (c, Card.ZERO));
            d.push(new Card (c, Card.ONE));
            d.push(new Card (c, Card.TWO));
            d.push(new Card (c, Card.THREE));
            d.push(new Card (c, Card.FOUR));
            d.push(new Card (c, Card.FIVE));
            d.push(new Card (c, Card.SIX));
            d.push(new Card (c, Card.SEVEN));
            d.push(new Card (c, Card.EIGHT));
            d.push(new Card (c, Card.NINE));

            d.push(new Card (c, Card.SKIP));
            d.push(new Card (c, Card.REVERSE));
            d.push(new Card (c, Card.DRAW_2));

        }

        for (int i =0; i<4; i++) {
            d.push(new Card (Card.WILD, Card.WILD));
            d.push(new Card (Card.WILD, Card.WILD_DRAW_4));
        }
    }
}