package ch.bbw.m183.blackjack_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  //Get Request for the start game
  @GetMapping("/start/{fixedGame}")
  public StartGameResponse startGame(
      @PathVariable boolean fixedGame
  ) {
    //create 2 hands
    Hand player1Hand = new Hand();
    Hand player2Hand = new Hand();
    //call start game method of the service and return the value to the frontend
    return service.startGame(player1Hand, player2Hand, fixedGame);
  }

  //get Request for a hit
  @GetMapping("/hit/{playerNumber}/{fixedHandPlayer}")
  public HitResponse playerHit(
      @PathVariable int playerNumber,
      @PathVariable boolean fixedHandPlayer
  ) {
    if (playerNumber == 1) {
      //hit a card for player 1 and call the service and return the value of the playerHit method
      return service.playerHit(playerNumber, fixedHandPlayer);
   } else if (playerNumber == 2) {
      //hit a card for player 2 and call the service and return the value of the playerHit method
      return service.playerHit(playerNumber, fixedHandPlayer);
    } else {
      //throw exeption if the playerNumber is not 1 or 2
      throw new IllegalArgumentException("Invalid player number");
    }
  }
}