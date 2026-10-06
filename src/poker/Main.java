package poker;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        // Hand 1: One Pair
        Hand hand1 = new Hand(Arrays.asList(
                new Card(Rank.ACE, Suit.SPADES),
                new Card(Rank.ACE, Suit.HEARTS),
                new Card(Rank.SEVEN, Suit.CLUBS),
                new Card(Rank.FIVE, Suit.DIAMONDS),
                new Card(Rank.TWO, Suit.SPADES)
        ));

        // Hand 2: Straight A-2-3-4-5
        Hand hand2 = new Hand(Arrays.asList(
                new Card(Rank.ACE, Suit.CLUBS),
                new Card(Rank.TWO, Suit.DIAMONDS),
                new Card(Rank.THREE, Suit.HEARTS),
                new Card(Rank.FOUR, Suit.SPADES),
                new Card(Rank.FIVE, Suit.CLUBS)
        ));

        System.out.println("=== POKER HAND ===");

        System.out.println(
                "Hand 1: " + hand1.getPokerHandType()
        );

        System.out.println(
                "Hand 2: " + hand2.getPokerHandType()
        );

        System.out.println("\n=== COMPARISON ===");

        int result = hand1.compareTo(hand2);

        if (result < 0) {
            System.out.println("Hand 2 is stronger.");
        } else if (result > 0) {
            System.out.println("Hand 1 is stronger.");
        } else {
            System.out.println("The two hands are equal.");
        }
    }
}