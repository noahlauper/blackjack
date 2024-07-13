package ch.bbw.m183.blackjack_backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameConfiguration {

  private boolean fixedHandForPlayer1;
  private boolean fixedHandForPlayer2;

  public GameConfiguration(boolean fixedHandForPlayer2) {
    this.fixedHandForPlayer1 = true;
    this.fixedHandForPlayer2 = fixedHandForPlayer2;
  }

  public void setFixedHandForPlayer1(boolean fixedHandForPlayer1) {
    this.fixedHandForPlayer1 = fixedHandForPlayer1;
  }

  public void setFixedHandForPlayer2(boolean fixedHandForPlayer2) {
    this.fixedHandForPlayer2 = fixedHandForPlayer2;
  }
}
