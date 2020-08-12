package com.valorin.api.event.arena;

import org.bukkit.entity.Player;

import com.valorin.api.event.ArenaEventAbs;
import com.valorin.arenas.Arena;

public class ArenaDrawFinishEvent extends ArenaEventAbs{
  private Player player1;
  private Player player2;
  private Arena arena;
  /**
   * 某场决斗在平局情况下结束时触发，不包括管理员强行结束的情况
   * @param player1 玩家1
   * @param player2 玩家2
   * @param arena 所在的竞技场
   */
  public ArenaDrawFinishEvent(Player player1,Player player2,Arena arena) {
	  this.player1 = player1;
	  this.player2 = player2;
	  this.arena = arena;
  }
  
  public Player getPlayer1() {
      return player1;
  }
  
  public Player getPlayer2() {
      return player2;
  }
  
  public Arena getArena() {
      return arena;
  }
}