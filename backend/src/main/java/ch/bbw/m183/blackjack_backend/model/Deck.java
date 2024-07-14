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
    Collections.shuffle(cards);

  }

  public List<Card> getCards() {
    return new ArrayList<>(cards); // Create a copy of the cards list
  }

  //Method to deal a card from the deck
  public Card dealCard() {
    if (cards.isEmpty()) {
      throw new IllegalStateException("Deck is empty, cannot deal cards");
    }
    //Remove card from the deck and return the card
    Card card = cards.remove(0);
    return card;
  }
}
