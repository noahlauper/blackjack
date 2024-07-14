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
    OptionalInt sum = OptionalInt.of(cards.stream().mapToInt(Card::getValue).sum());
    int value = sum.getAsInt();
    if (cards.stream().anyMatch(card -> card.getRank() == Rank.ACE) && value + 10 <= 21) {
      return value + 10;
    }
    return value;

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
