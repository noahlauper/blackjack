package ch.bbw.m183.blackjack_backend.service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ch.bbw.m183.blackjack_backend.model.Card;
import ch.bbw.m183.blackjack_backend.model.Deck;
import ch.bbw.m183.blackjack_backend.model.GameConfiguration;
import ch.bbw.m183.blackjack_backend.model.GameStatus;
import ch.bbw.m183.blackjack_backend.model.Hand;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlackJackService {

  private Deck deck;
  private GameConfiguration config;
  private Hand hand1;
  private Hand hand2;


  public GameStatus startGame(Hand player1Hand, Hand player2Hand, boolean fixHandForPlayer2) {
    // Create a new deck (within startGame for this example)
    this.deck = new Deck();
    this.config = new GameConfiguration(fixHandForPlayer2);
    System.out.println(deck.getCards().toString());
    // Shuffle the deck before dealing cards
    Collections.shuffle(this.deck.getCards());

    // Deal initial cards (consider configurable manipulation)
    dealCard(player1Hand, !config.isFixedHandForPlayer1());
    dealCard(player1Hand, !config.isFixedHandForPlayer1());
    dealCard(player2Hand, !config.isFixedHandForPlayer2());
    dealCard(player2Hand, !config.isFixedHandForPlayer2());

    // Create GameStatus object with initial player hands
    List<Card> player1Cards = player1Hand.getCards();
    List<Card> player2Cards = player2Hand.getCards();
    this.hand1.setCards(player1Cards);
    this.hand2.setCards(player2Cards);
    return new GameStatus(player1Cards, player2Cards);
  }

  public Hand dealCard(Hand hand, boolean dealNormally) {
    if (dealNormally) {
      hand.addCard(deck.dealCard());
      return hand;
    } else {
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

  public Hand playerHit(int playerNumber, boolean dealNormally) {
    if (playerNumber == 1) {
      return dealCard(this.hand1, dealNormally);
    } else {
      return dealCard(this.hand2, dealNormally);
    }
  }

  public String getWinner(Hand player1Hand, Hand player2Hand) {
    if (player1Hand.isBust()) {
      return "Player 2 Wins";
    } else if (player2Hand.isBust()) {
      return "Player 1 Wins";
    } else if (player1Hand.getHandValue() > player2Hand.getHandValue()) {
      return "Player 1 Wins";
    } else if (player1Hand.getHandValue() < player2Hand.getHandValue()) {
      return "Player 2 Wins";
    } else {
      return "Push (Tie)";
    }
  }
}
