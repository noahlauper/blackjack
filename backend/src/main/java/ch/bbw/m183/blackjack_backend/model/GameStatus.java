package ch.bbw.m183.blackjack_backend.model;

import java.util.List;

import lombok.Data;

@Data
public class GameStatus {

  private List<Card> player1Hand;
  private List<Card> player2Hand;
  private String winner;

  public GameStatus(List<Card> player1Hand, List<Card> player2Hand) {
    this.player1Hand = player1Hand;
    this.player2Hand = player2Hand;
    this.winner = "";
  }
}
