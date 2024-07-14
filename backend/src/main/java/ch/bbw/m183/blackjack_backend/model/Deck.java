package ch.bbw.m183.blackjack_backend.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Deck {
  private final List<Card> cards;

  public Deck() {
    cards = Stream.of(Suit.values())
        .flatMap(suit -> Stream.of(Rank.values())
            .map(rank -> new Card(suit, rank)))
        .collect(Collectors.toList());
  }

  public List<Card> getCards() {
    return new ArrayList<>(cards); // Create a copy of the cards list
  }

  public Card dealCard() {
    if (cards.isEmpty()) {
      throw new IllegalStateException("Deck is empty, cannot deal cards");
    }
    Card card = cards.remove(0);
    return card;
  }

  // Add methods for shuffling the deck (already done in BlackjackService)
  // Consider methods for resetting the deck if needed
}
