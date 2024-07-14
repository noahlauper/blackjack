package ch.bbw.m183.blackjack_backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.Getter;

@Data
public class Hand {
  private List<Card> cards = new ArrayList<>();

  public void addCard(Card card) {
    cards.add(card);
  }

  public List<Card> getCards() {
    return cards;
  }

  public int getHandValue() {
    int sum = cards.stream().mapToInt(card -> switch (convertCardToDTO(card).getRank()) {
      case "k", "q", "j" -> 10; // Face cards are worth 10
      case "a" -> 1;
      default -> Integer.parseInt(convertCardToDTO(card).getRank()); // Use card's getValue() for numeric ranks and Ace
    }).sum();

    System.out.println(sum);
    // Check for aces and potentially add 10 if it doesn't cause bust
    int numAces = (int) cards.stream().filter(card -> card.getRank() == Rank.ACE).count();
    if (numAces > 0 && sum + numAces * 10 <= 21) {
      return sum + numAces * 10; // Add 10 for each ace if it doesn't cause bust
    }
    return sum;
  }

  public List<CardDTO> getCardsAsDTO() {
    return cards.stream().map(card -> convertCardToDTO(card)).collect(Collectors.toList());
  }

  private CardDTO convertCardToDTO(Card card) {
    String rankString = switch (card.getRank()) {
      case TWO -> "2";
      case THREE -> "3";
      case FOUR -> "4";
      case FIVE -> "5";
      case SIX -> "6";
      case SEVEN -> "7";
      case EIGHT -> "8";
      case NINE -> "9";
      case TEN -> "10";
      case JACK -> "j";
      case QUEEN -> "q";
      case KING -> "k";
      case ACE -> "a";
      default -> throw new IllegalStateException("Unexpected Rank value: " + card.getRank());
    };
    return new CardDTO(rankString, card.getSuit());
  }

  public boolean isBust() {
    return getHandValue() > 21;
  }
}
