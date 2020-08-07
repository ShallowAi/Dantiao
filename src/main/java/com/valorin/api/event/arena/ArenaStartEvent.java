package com.valorin.api.event.arena;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.valorin.arenas.Arena;

public class ArenaStartEvent extends Event{
    private static final HandlerList handlers = new HandlerList();
    private Player player1;
    private Player player2;
    private Arena arena;
    /**
	 * 玩家在单挑积分商城购物时调用（前提是有足够的积分）
	 * @param player1 玩家1
	 * @param player2 玩家2
	 * @param arena 竞技场
	 */
    public ArenaStartEvent(Player player1,Player player2,Arena arena)
    {
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
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
