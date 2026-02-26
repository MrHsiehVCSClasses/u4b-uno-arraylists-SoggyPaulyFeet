package u6pp;

import java.util.Scanner;

public class UnoFrontend {
    private Uno uno;
    private Scanner scanner;

    public UnoFrontend() {
        scanner = new Scanner(System.in);
        uno = new Uno(3); // game strts with 3 people
    }

    public void play() {
        while (uno.getWinner() == null) {

            Player current = uno.getCurrentPlayer();

            System.out.println("\nTopCard: " + uno.getTopDiscard().getColor() + " " + uno.getTopDiscard().getValue());
            System.out.println("Current Player: " + current.getName());
            System.out.println("Your Hand:");

            for (int i = 0; i < current.getHand().size(); i++) {
                Card c = current.getHand().get(i);
                System.out.println(i + ": " + c.getColor() + " " + c.getValue());
            }
            System.out.println("Enter card index to play OR type 'draw' to draw: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("draw")) {
                uno.playCard(null, null);
                continue;
            }

            try {
                int index = Integer.parseInt(input);
                Card chosen = current.getHand().get(index);

                String chosenColor = null;

                if (Card.WILD.equals(chosen.getValue()) || Card.WILD_DRAW_4.equals(chosen.getValue())) {
                    System.out.println("Choose color (RED, GREEN, BLUE, YELLOW): ");
                    chosenColor = scanner.nextLine();

                }

                boolean success = uno.playCard(chosen, chosenColor);

                if (!success) {
                    System.out.println("Invalid play!");
                }

            } catch (Exception e) {
                System.out.println("Invalid Input! ");
            }
        }
        System.out.println("Winner is: " + uno.getWinner().getName());
    }
}
