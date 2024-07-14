package ch.bbw.m183.blackjack_backend.service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ch.bbw.m183.blackjack_backend.model.Card;
import ch.bbw.m183.blackjack_backend.model.Deck;
import ch.bbw.m183.blackjack_backend.model.request.GameConfiguration;
import ch.bbw.m183.blackjack_backend.model.Hand;
import ch.bbw.m183.blackjack_backend.model.response.HitResponse;
import ch.bbw.m183.blackjack_backend.model.response.StartGameResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlackJackService {

  private Deck deck;
  private GameConfiguration config;
  private Hand hand1 = new Hand();
  private Hand hand2 = new Hand();


  public StartGameResponse startGame(Hand player1Hand, Hand player2Hand, boolean fixHandForPlayer2) {
    // Create a new deck (within startGame for this example)
    this.deck = new Deck();
    this.config = new GameConfiguration(fixHandForPlayer2);
    // Shuffle the deck before dealing cards
    Collections.shuffle(this.deck.getCards());
    System.out.println(deck.getCards().toString());

    // Deal initial cards (consider configurable manipulation)
    dealCard(player1Hand, true);
    dealCard(player1Hand, true);
    dealCard(player2Hand, !config.isFixedHandForPlayer2());
    dealCard(player2Hand, !config.isFixedHandForPlayer2());

    // Create GameStatus object with initial player hands
    List<Card> player1Cards = player1Hand.getCards();
    List<Card> player2Cards = player2Hand.getCards();
    this.hand1.setCards(player1Cards);
    this.hand2.setCards(player2Cards);
    return new StartGameResponse(this.hand1.getCardsAsDTO(), this.hand2.getCardsAsDTO(), this.hand1.getHandValue(), this.hand2.getHandValue());
  }

  public Hand dealCard(Hand hand, boolean dealNormally) {
    if (dealNormally) {
      hand.addCard(deck.dealCard());
      return hand;
    } else {

      //player1 direct 21

      // Filter cards to deal only cards with value 2-6 (inclusive)
      List<Card> lowValueCards = deck.getCards().stream()
          .filter(card -> card.getValue() >= 2 && card.getValue() <= 6)
          .collect(Collectors.toList());

      // If there are low-value cards remaining, deal one from the filtered list
      if (!lowValueCards.isEmpty()) {
        int randomIndex = new Random().nextInt(lowValueCards.size());
        Card lowValueCard = lowValueCards.remove(randomIndex);
        hand.addCard(lowValueCard);
        // Remove the dealt card from the main deck to prevent duplicates
        deck.getCards().remove(lowValueCard);
        return hand;
      } else {
        // If no low-value cards are left, deal a random card from the remaining deck
        hand.addCard(deck.dealCard());
        return hand;
      }
    }
  }

  public HitResponse playerHit(int playerNumber, boolean dealNormally) {
    Hand updatedHand;
    if (playerNumber == 1) {
      updatedHand = dealCard(this.hand1, dealNormally);
    } else {
      updatedHand = dealCard(this.hand2, dealNormally);
    }
    return new HitResponse(updatedHand.getCardsAsDTO(), updatedHand.getHandValue());
  }
}
