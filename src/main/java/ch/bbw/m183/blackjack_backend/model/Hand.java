package ch.bbw.m183.blackjack_backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

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

  public boolean isBust() {
    return getHandValue() > 21;
  }
}
