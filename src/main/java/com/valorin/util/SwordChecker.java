package com.valorin.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SwordChecker {
    public static boolean isHoldingSword(Player player) {
    	ItemStack itemInHand = player.getItemInHand();
    	if (itemInHand == null) { return false; }
    	Material type = itemInHand.getType();
    	if (type.equals(Material.AIR)) { return false; }
    	if (type.equals(Material.WOOD_SWORD) ||
    		type.equals(Material.STONE_SWORD) ||
    		type.equals(Material.GOLD_SWORD) ||
    		type.equals(Material.IRON_SWORD) ||
    		type.equals(Material.DIAMOND_SWORD)) { return true; }
		return false;
    }
}
