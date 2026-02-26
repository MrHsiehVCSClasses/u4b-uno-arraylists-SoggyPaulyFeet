package u6pp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardStack {
    private List<Card> cards;
//defining and making the card stack class which includes all the methods for the game.
    public CardStack() {
        cards = new ArrayList<>();
    }

    public void push(Card card) {
        cards.add(card);
    }

    public Card pop() {
        if (isEmpty()) {
            return null;
        }
        return cards.remove(cards.size() - 1);
    }

    public Card peek() {
        if (isEmpty()) {
            return null;
        }
        return cards.get(cards.size() - 1);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int getSize() {
        return cards.size();
    }

    public void clear() {
        cards.clear();
    }
// adds all the cards from the other stack to this stack, emptying other stacks.
    public void addAll(CardStack other) {
        List<Card> temp = new ArrayList<>();
        while (!other.isEmpty()) {
            temp.add(other.pop());
        }
        // temp has the cards in reverse order (top to bottom)
        // but we want to add bottom to top, so reverse again? No.
        // other had bottom to top: greenNine, greenEight
        // temp: greenEight, greenNine (since pop greenEight first, then greenNine)
        // To add to this stack, we want to push greenNine first, then greenEight, so top is greenEight
        Collections.reverse(temp);
        for (Card card : temp) {
            this.push(card);
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}