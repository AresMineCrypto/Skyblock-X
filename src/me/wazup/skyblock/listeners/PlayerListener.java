package me.wazup.skyblock.listeners;

import me.wazup.skyblock.PlayerData;
import me.wazup.skyblock.Skyblock;
import me.wazup.skyblock.managers.Customization;
import me.wazup.skyblock.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player p = e.getPlayer();
        Skyblock.getInstance().playerData.put(p.getUniqueId(), new PlayerData(p));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        PlayerData data = Skyblock.getInstance().playerData.get(p.getUniqueId());
        data.saveData(p, true);

        if(Skyblock.getInstance().players.contains(p.getUniqueId())){
            Skyblock.getInstance().leave(p);
        }

        Skyblock.getInstance().playerData.remove(p.getUniqueId());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();

        if(Skyblock.getInstance().adminSelection.containsKey(p.getUniqueId()) && Utils.compareItem(p.getItemInHand(), Customization.getInstance().items.get("Wand"))){
            e.setCancelled(true);
            int id = e.getAction().equals(Action.LEFT_CLICK_BLOCK) ? 0 : e.getAction().equals(Action.RIGHT_CLICK_BLOCK) ? 1 : 2;
            if(id == 2) return;
            Location l = e.getClickedBlock().getLocation();
            Skyblock.getInstance().adminSelection.get(p.getUniqueId())[id] = l;
            p.sendMessage(Customization.getInstance().prefix + "You have set the " + ChatColor.LIGHT_PURPLE + "#" + (id + 1) + ChatColor.GRAY + " corner at " + Utils.getReadableLocationString(l, false));
            return;
        }

    }

}
