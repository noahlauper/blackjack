package ch.bbw.m183.blackjack_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card implements Comparable<Card>{
  private Suit suit;
  private Rank rank;

  //get Value Method to get value of cards.
  public int getValue() {
    return switch (rank) {
      case TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE -> rank.ordinal() + 1; // Ranks 2-9 have face value
      case TEN, JACK, QUEEN, KING -> 10; // Face cards are worth 10
      case ACE -> 1; // Ace can be 1 or 11 (handled elsewhere)
      default -> throw new IllegalStateException("Unexpected Rank value: " + rank);
    };
  }

  //CompareTo method to set default order
  @Override
  public int compareTo(Card otherCard) {
    return this.getValue() - otherCard.getValue();
  }
}
