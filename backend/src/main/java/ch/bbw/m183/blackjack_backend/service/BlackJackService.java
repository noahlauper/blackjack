package ch.bbw.m183.blackjack_backend.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ch.bbw.m183.blackjack_backend.model.Card;
import ch.bbw.m183.blackjack_backend.model.Deck;
import ch.bbw.m183.blackjack_backend.model.Rank;
import ch.bbw.m183.blackjack_backend.model.Hand;
import ch.bbw.m183.blackjack_backend.model.response.HitResponse;
import ch.bbw.m183.blackjack_backend.model.response.StartGameResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlackJackService {

  private Deck deck;
  private Hand hand1 = new Hand();
  private Hand hand2 = new Hand();


  public StartGameResponse startGame(Hand player1Hand, Hand player2Hand, boolean fixedGame) {
    // Create a new deck (within startGame for this example)
    this.deck = new Deck();
    // Shuffle the deck before dealing cards
    Collections.shuffle(this.deck.getCards());
    System.out.println();
    // Deal initial cards
    if (fixedGame) {
      dealCard(player1Hand, false);
      dealCard(player2Hand, true);
      dealCard(player2Hand, true);
    } else {
      dealCard(player1Hand, true);
      dealCard(player1Hand, true);
      dealCard(player2Hand, true);
      dealCard(player2Hand, true);
    }

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
      // Automatic win hand (King and Ace)
      Optional<Card> kingCard = deck.getCards().stream()
          .filter(card -> card.getRank() == Rank.KING)
          .findFirst();  // Find the first King card

      if (kingCard.isPresent()) {
        hand.addCard(kingCard.get());  // Add the King card from the stream
        deck.getCards().remove(kingCard.get());  // Remove the dealt King from the deck
      }

      Optional<Card> aceCard = deck.getCards().stream()
          .filter(card -> card.getRank() == Rank.ACE)
          .findFirst();  // Find the first Ace card

      if (aceCard.isPresent()) {
        hand.addCard(aceCard.get());  // Add the Ace card from the stream
        deck.getCards().remove(aceCard.get());  // Remove the dealt Ace from the deck
      }

      return hand;
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
