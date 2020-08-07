package com.valorin.effect;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.valorin.Dantiao;

public class WinFirework {
	public static void setFirework(Location loc) {
	  if (loc == null) {
		return;
	  }
	  new BukkitRunnable() {
	    @Override
		public void run() {
	      Firework firework= (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
	      FireworkMeta fm = firework.getFireworkMeta();
	      fm.addEffect(FireworkEffect
	      .builder()
		  .with(FireworkEffect.Type.BALL_LARGE)
		  .withFade(Color.PURPLE)
		  .withColor(Color.ORANGE)
		  .withColor(Color.YELLOW)
		  .withTrail()
		  .build());
		  fm.addEffect(FireworkEffect
	      .builder()
	      .with(FireworkEffect.Type.BALL)
	      .withFade(Color.AQUA)
	      .withColor(Color.ORANGE)
	      .withColor(Color.YELLOW)
	      .withTrail()
	      .build());
	      fm.setPower(2);
	      firework.setFireworkMeta(fm);
	    }
	  }.runTask(Dantiao.getInstance());
	}
}
