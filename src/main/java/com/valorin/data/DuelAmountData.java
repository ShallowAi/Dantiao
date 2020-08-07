package com.valorin.data;

public class DuelAmountData {
  private int amount;
  
  public DuelAmountData() {
	amount = 0;
  }
  
  public void add() {
	amount++;
  }
  
  public int getAndClear() {
	int amount = this.amount;
	this.amount = 0;
	return amount;
  }
}
