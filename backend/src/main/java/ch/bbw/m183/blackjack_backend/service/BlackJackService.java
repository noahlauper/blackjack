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


  //Method to start a game of Black Jack and return the 2 card each player gets on the beginning
  public StartGameResponse startGame(Hand player1Hand, Hand player2Hand, boolean fixedGame) {
    // Create a new deck
    this.deck = new Deck();

    // Shuffle the deck before dealing cards
    Collections.shuffle(this.deck.getCards());
    // Deal initial cards
    if (fixedGame) {
      //Deal Cards so player one has direct black jack, other player gets 2 random cards
      dealCard(player1Hand, false);
      dealCard(player2Hand, true);
      dealCard(player2Hand, true);
    } else {
      //deal normal cards (2 Cards per player)
      dealCard(player1Hand, true);
      dealCard(player1Hand, true);
      dealCard(player2Hand, true);
      dealCard(player2Hand, true);
    }

    // Assign Hand with dealt cards
    this.hand1.setCards(player1Hand.getCards());
    this.hand2.setCards(player2Hand.getCards());
    //return GameStartResponse (Cards to CardTO for easier use in frontend)
    return new StartGameResponse(this.hand1.getCardsAsDTO(), this.hand2.getCardsAsDTO(), this.hand1.getHandValue(), this.hand2.getHandValue());
  }

  //deal card for a player, If a dealNormally is false the player automatically get 2 cards that combine give a black jack (always an ace and a king)
  public Hand dealCard(Hand hand, boolean dealNormally) {
    if (dealNormally) {
      //deal 1 random card from the deck
      hand.addCard(deck.dealCard());
      return hand;
    } else {
      // get the first Card with the rank of King in the deck using a stream and filter
      Optional<Card> kingCard = deck.getCards().stream()
          .filter(card -> card.getRank() == Rank.KING)
          .findFirst();  // Find the first King card

      if (kingCard.isPresent()) {
        hand.addCard(kingCard.get());  // Add the King card to the deck
        deck.getCards().remove(kingCard.get());  // Remove the dealt King from the deck
      }
      //get first ace of the deck using a stream and filter
      Optional<Card> aceCard = deck.getCards().stream()
          .filter(card -> card.getRank() == Rank.ACE)
          .findFirst();  // Find the first Ace card

      if (aceCard.isPresent()) {
        hand.addCard(aceCard.get());  // Add the Ace card to the hand
        deck.getCards().remove(aceCard.get());  // Remove the dealt Ace from the deck
      }

      return hand;
    }
  }

  //playerHit Method is for a player to hit and get an extra card
  public HitResponse playerHit(int playerNumber, boolean dealNormally) {
    Hand updatedHand;
    if (playerNumber == 1) {
      //adds card to player one hand
      updatedHand = dealCard(this.hand1, dealNormally);
    } else {
      //adds card to player 2 hand
      updatedHand = dealCard(this.hand2, dealNormally);
    }
    //return the whole deck of the player
    return new HitResponse(updatedHand.getCardsAsDTO(), updatedHand.getHandValue());
  }
}
