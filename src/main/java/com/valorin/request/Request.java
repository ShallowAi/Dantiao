package com.valorin.request;

public class Request {
  private String sender;
  private String receiver;
  
  public Request(String sender,String receiver) {
	this.sender = sender;
	this.receiver = receiver;
  }
  
  public String getSender() {
	return sender;
  }
  
  public String getReceiver() {
	return receiver;
  }
}
