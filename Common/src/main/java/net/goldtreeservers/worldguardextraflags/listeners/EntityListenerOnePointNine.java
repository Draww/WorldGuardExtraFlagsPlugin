package net.goldtreeservers.worldguardextraflags.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.goldtreeservers.worldguardextraflags.WorldGuardExtraFlagsPlugin;
import net.goldtreeservers.worldguardextraflags.flags.Flags;
import net.goldtreeservers.worldguardextraflags.wg.WorldGuardUtils;

@RequiredArgsConstructor
public class EntityListenerOnePointNine implements Listener
{
	@Getter private final WorldGuardExtraFlagsPlugin plugin;
	
	@EventHandler(ignoreCancelled = true)
	public void onEntityToggleGlideEvent(EntityToggleGlideEvent event)
	{
		Entity entity = event.getEntity();
		if (entity instanceof Player)
		{
			Player player = (Player)entity;
			
			ApplicableRegionSet regions = this.plugin.getWorldGuardCommunicator().getRegionContainer().createQuery().getApplicableRegions(player.getLocation());

			State state = WorldGuardUtils.queryState(player, player.getWorld(), regions.getRegions(), Flags.GLIDE);
			if (state != null)
			{
				event.setCancelled(true);
				player.setGliding(state == State.ALLOW);
			}
		}
	}
}
