package com.valorin.data;

public class CommandTypeAmountData {
  private int amount;
  
  public CommandTypeAmountData() {
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
