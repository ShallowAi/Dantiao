package com.valorin.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import com.valorin.configuration.DataFile;

public class ShopEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Player buyer;
    private int page;
    private int row;
    private int column;
    /**
	 * 玩家在单挑积分商城购物时调用（前提是有足够的积分）
	 * @param buyer 玩家
	 * @param page 商品所在的页数
	 * @param row 商品所在的行数
	 * @param column 商品所在的列数
	 */
    public ShopEvent(Player buyer,int page,int row,int column)
    {
      this.buyer = buyer;
      this.page = page;
      this.row = row;
      this.column = column;
	}
    
    public Player getPlayer() {
    	return buyer;
    }
    
    public int getPage() {
        return page;
    }
    
    public int getRow() {
    	return row;
    }
    
    public int getColumn() {
    	return column;
    }
    
    public int getNum() {
        return (page - 1)*36+(row - 1)*9+column;
    }
    
    public ItemStack getItemStack() {
    	int num = getNum();
        return DataFile.shop.getItemStack((num-1)+".Item");
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
