package com.valorin.dan;

public class Dan {
  private int num;
  private String editName;
  private String danName;
  private int exp;
	  
  public int getNum() {
	return num;
  }
	  
  public String getEditName() {
	return editName;
  }
	  
  public String getDanName() {
	return danName;
  }
	  
  public int getExp() {
	return exp;
  }
  
  public Dan(int num,String editName,String danName,int exp) {
	this.num = num;
	this.editName = editName;
	this.danName = danName;
	this.exp = exp;
  }
}
