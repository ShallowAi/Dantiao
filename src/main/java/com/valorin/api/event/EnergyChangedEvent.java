package com.valorin.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EnergyChangedEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Player player;
    private double before;
    private double now;
    /**
	 * 精力值变更时调用，前提是服务器的精力值系统允许使用
	 * @param player 玩家
	 * @param d 变更前的精力值
	 * @param amount 变更后的精力值
	 */
    public EnergyChangedEvent(Player player,double d,double amount)
    {
      this.player = player;
      this.before = d;
      this.now = amount;
	}
    
    public Player getPlayer() {
    	return player;
    }
    
    public double getBefore() {
        return before;
    }
    
    public double getNow() {
    	return now;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
