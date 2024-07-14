package ch.bbw.m183.blackjack_backend.model.response;

import java.util.List;

import ch.bbw.m183.blackjack_backend.model.CardDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StartGameResponse {
  private List<CardDTO> player1Hand;
  private List<CardDTO> player2Hand;
  private int player1HandValue;
  private int player2HandValue;
}
