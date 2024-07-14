package ch.bbw.m183.blackjack_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.bbw.m183.blackjack_backend.model.Hand;
import ch.bbw.m183.blackjack_backend.model.response.HitResponse;
import ch.bbw.m183.blackjack_backend.model.response.StartGameResponse;
import ch.bbw.m183.blackjack_backend.service.BlackJackService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BlackjackController {

  private final BlackJackService service;

  @GetMapping("/start/{fixedHandPlayer2}")
  public StartGameResponse startGame(
      @PathVariable boolean fixedHandPlayer2
  ) {
    Hand player1Hand = new Hand();
    Hand player2Hand = new Hand();
    return service.startGame(player1Hand, player2Hand, fixedHandPlayer2);
  }

  @GetMapping("/hit/{playerNumber}/{fixedHandPlayer}")
  public HitResponse playerHit(
      @PathVariable int playerNumber,
      @PathVariable boolean fixedHandPlayer
  ) {
    if (playerNumber == 1) {
      return service.playerHit(playerNumber, fixedHandPlayer);
   } else if (playerNumber == 2) {
      return service.playerHit(playerNumber, fixedHandPlayer);
    } else {
      throw new IllegalArgumentException("Invalid player number");
    }
  }
}