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
    int sum = cards.stream().mapToInt(card -> Integer.parseInt(convertCardToDTO(card).getRank())).sum();
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
    String rankString;
    switch (card.getRank()) {
      case TWO:
        rankString = "2";
        break;
      case THREE:
        rankString = "3";
        break;
      case FOUR:
        rankString = "4";
        break;
      case FIVE:
        rankString = "5";
        break;
      case SIX:
        rankString = "6";
        break;
      case SEVEN:
        rankString = "7";
        break;
      case EIGHT:
        rankString = "8";
        break;
      case NINE:
        rankString = "9";
        break;
      case TEN:
        rankString = "10";
        break;
      case JACK:
        rankString = "j";
        break;
      case QUEEN:
        rankString = "q";
        break;
      case KING:
        rankString = "k";
        break;
      case ACE:
        rankString = "a";
        break;
      default:
        throw new IllegalStateException("Unexpected Rank value: " + card.getRank());
    }
    return new CardDTO(rankString, card.getSuit());
  }

  public boolean isBust() {
    return getHandValue() > 21;
  }
}
