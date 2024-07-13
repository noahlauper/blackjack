package ch.bbw.m183.blackjack_backend.model;

import java.util.List;

import lombok.Data;

@Data
public class PlayRequest {

  private Hand player1Hand;

  private Hand player2Hand;

}
