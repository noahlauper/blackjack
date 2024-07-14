package ch.bbw.m183.blackjack_backend.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StartGameDTO {
  private List<CardDTO> player1Hand;
  private List<CardDTO> player2Hand;
}
