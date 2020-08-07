package com.valorin.api.event.arena;

import org.bukkit.entity.Player;

import com.valorin.api.event.ArenaEventAbs;
import com.valorin.arenas.Arena;

public class ArenaFinishEvent extends ArenaEventAbs{
  private Player winner;
  private Player loser;
  private Arena arena;
  /**
   * 某场比赛在非平局情况下结束时触发，不包括管理员强行结束的情况
   * @param winner 胜利的玩家
   * @param loser 战败的玩家
   * @param arena 所在的竞技场
   */
  public ArenaFinishEvent(Player winner,Player loser,Arena arena) {
	  this.winner = winner;
	  this.loser = loser;
	  this.arena = arena;
  }
  
  public Player getWinner() {
      return winner;
  }
  
  public Player getLoser() {
      return loser;
  }
  
  public Arena getArena() {
      return arena;
  }
}