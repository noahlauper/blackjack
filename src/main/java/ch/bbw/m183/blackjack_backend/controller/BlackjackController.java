package ch.bbw.m183.blackjack_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.bbw.m183.blackjack_backend.model.GameConfiguration;
import ch.bbw.m183.blackjack_backend.model.GameStatus;
import ch.bbw.m183.blackjack_backend.model.Hand;
import ch.bbw.m183.blackjack_backend.model.PlayRequest;
import ch.bbw.m183.blackjack_backend.service.BlackJackService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BlackjackController {

  private final BlackJackService service;

  @GetMapping("/start/{fixedHandPlayer2}")
  public GameStatus startGame(
      @PathVariable boolean fixedHandPlayer2
  ) {
    Hand player1Hand = new Hand();
    Hand player2Hand = new Hand();
    return service.startGame(player1Hand, player2Hand, fixedHandPlayer2);
  }

  @PostMapping("/hit/{playerNumber}/{fixedHandPlayer}")
  public GameStatus playerHit(
      @PathVariable int playerNumber,
      @RequestBody PlayRequest playRequest,
      @PathVariable boolean fixedHandPlayer
  ) {
    if (playerNumber == 1) {
      service.playerHit(playRequest.getPlayer1Hand(), fixedHandPlayer);
    } else if (playerNumber == 2) {
      service.playerHit(playRequest.getPlayer2Hand(), fixedHandPlayer);
    } else {
      throw new IllegalArgumentException("Invalid player number");
    }
    return new GameStatus(playRequest.getPlayer1Hand().getCards(), playRequest.getPlayer2Hand().getCards());
  }
}
/*  @PostMapping("/stand/{playerNumber}")
  public GameStatus playerStand(
      @PathVariable int playerNumber,
      @RequestBody PlayRequest playRequest

  ) {
    if (playerNumber == 1) {
      // Player 1 stands, handle AI logic for Player 2 (hit until reaching a certain value)
      while (service.getHandValue(hand) < 17) {
        service.playerHit(hand);
      }
    } else if (playerNumber == 2) {
      // Handle player 2 stand action (similar to player 1)
    } else {
      throw new IllegalArgumentException("Invalid player number");
    }
    Hand opponentHand = new Hand(); // Placeholder for opponent's hand (replace with actual hand)
    return new GameStatus(hand.getCards(), opponentHand.getCards(), service.getWinner(hand, opponentHand));
  }*/