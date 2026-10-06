package poker;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Hand implements Comparable<Hand> {
    private final List<Card> cards;

    /**
     * Creates a poker hand containing exactly five cards.
     *
     * @param cards the cards in the hand
     * @throws IllegalArgumentException if the hand does not
     *         contain exactly five valid cards
     */
    public Hand(List<Card> cards) {
        if (cards == null || cards.size() != 5) {
            throw new IllegalArgumentException(
                    "A poker hand must contain exactly five cards."
            );
        }

        if (cards.contains(null)) {
            throw new IllegalArgumentException(
                    "Cards cannot be null."
            );
        }

        this.cards = new ArrayList<>(cards);
    }

    /**
     * Returns the cards in this hand.
     *
     * @return an unmodifiable view of the cards
     */
    public List<Card> getCards() {
        return java.util.Collections.unmodifiableList(cards);
    }

    /**
     * Counts how many times each rank occurs.
     *
     * @return a map of ranks and their frequencies
     */
    private Map<Rank, Integer> getRankCounts() {
        Map<Rank, Integer> counts = new HashMap<>();

        for (Card card : cards) {
            counts.put(
                    card.getRank(),
                    counts.getOrDefault(card.getRank(), 0) + 1
            );
        }

        return counts;
    }

    /**
     * Checks whether all five cards have the same suit.
     *
     * @return true if the hand is a flush
     */
    private boolean isFlush() {
        Suit firstSuit = cards.get(0).getSuit();

        for (Card card : cards) {
            if (card.getSuit() != firstSuit) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether the hand contains five consecutive ranks.
     * Ace is only treated as low in this version.
     *
     * @return true if the hand is a straight
     */
    private boolean isStraight() {
        Set<Rank> ranks = new HashSet<>();

        for (Card card : cards) {
            ranks.add(card.getRank());
        }

        // A straight must contain five different ranks.
        if (ranks.size() != 5) {
            return false;
        }

        List<Integer> values = new ArrayList<>();

        for (Rank rank : ranks) {
            values.add(rank.ordinal());
        }

        values.sort(Integer::compareTo);

        // Five consecutive ranks have a difference of four.
        return values.get(4) - values.get(0) == 4;
    }

    /**
     * Determines the poker category of this hand.
     *
     * @return the strongest applicable poker hand type
     */
    public PokerHandType getPokerHandType() {
        Map<Rank, Integer> counts = getRankCounts();

        List<Integer> frequencies =
                new ArrayList<>(counts.values());

        frequencies.sort(
                (a, b) -> Integer.compare(b, a)
        );

        boolean straight = isStraight();
        boolean flush = isFlush();

        if (straight && flush) {
            return PokerHandType.STRAIGHT_FLUSH;
        }

        if (frequencies.get(0) == 4) {
            return PokerHandType.FOUR_OF_A_KIND;
        }

        if (frequencies.get(0) == 3
                && frequencies.get(1) == 2) {
            return PokerHandType.FULL_HOUSE;
        }

        if (flush) {
            return PokerHandType.FLUSH;
        }

        if (straight) {
            return PokerHandType.STRAIGHT;
        }

        if (frequencies.get(0) == 3) {
            return PokerHandType.THREE_OF_A_KIND;
        }

        if (frequencies.get(0) == 2
                && frequencies.get(1) == 2) {
            return PokerHandType.TWO_PAIR;
        }

        if (frequencies.get(0) == 2) {
            return PokerHandType.ONE_PAIR;
        }

        return PokerHandType.HIGH_CARD;
    }

    /**
     * Compares two hands by poker category only.
     * Hands of the same category are considered equal
     * in this simplified version.
     *
     * @param other the hand to compare with
     * @return a negative number, zero, or a positive number
     */
    @Override
    public int compareTo(Hand other) {
        if (other == null) {
            throw new IllegalArgumentException(
                    "Hand cannot be null."
            );
        }

        return Integer.compare(
                this.getPokerHandType().ordinal(),
                other.getPokerHandType().ordinal()
        );
    }

    @Override
    public String toString() {
        return cards.toString()
                + " - " + getPokerHandType();
    }
}
