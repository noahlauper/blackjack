package ch.bbw.m183.blackjack_backend.model.response;

import java.util.List;

import ch.bbw.m183.blackjack_backend.model.CardDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HitResponse {
  private List<CardDTO> cardDTOS;
  private int handValue;
}
