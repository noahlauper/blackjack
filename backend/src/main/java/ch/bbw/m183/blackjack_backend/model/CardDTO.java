package ch.bbw.m183.blackjack_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardDTO {
  private String Rank;
  private Suit suit;
}
