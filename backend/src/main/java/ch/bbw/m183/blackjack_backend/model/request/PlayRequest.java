package ch.bbw.m183.blackjack_backend.model.request;

import ch.bbw.m183.blackjack_backend.model.Hand;

import lombok.Data;

@Data
public class PlayRequest {

  private Hand player1Hand;

  private Hand player2Hand;

}
