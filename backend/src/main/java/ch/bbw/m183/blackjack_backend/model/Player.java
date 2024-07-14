package ch.bbw.m183.blackjack_backend.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

  String name;
  List<Card> cards;

  public Player(String name) {
    this.name = name;
    this.cards = new ArrayList<>();
  }
}
