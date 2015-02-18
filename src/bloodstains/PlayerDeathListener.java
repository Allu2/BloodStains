package bloodstains;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Set;

/**
 * Created by aleksi on 11.2.2015.
 */
public final class PlayerDeathListener implements Listener {
    public static BloodStains plugin;

    FileConfiguration safe;
    public PlayerDeathListener(BloodStains plug){
        plugin = plug;
        safe = plugin.data.getConfig();

    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player killer = event.getEntity().getKiller();
        if(killer !=null) {
            if (!killer.hasPermission("bloodstains.exempt")) { //Check if killer has exempt permission.
                String displayname = killer.getDisplayName();
                killer.setPlayerListName(ChatColor.DARK_RED + displayname);
                safe.set("uuid."+(killer.getUniqueId().toString()), true);
                plugin.data.saveConfig();
                event.setDeathMessage(killer.getDisplayName()+" has"+ChatColor.DARK_RED +" bloodstained "+ChatColor.WHITE+"hands and corpse of "+event.getEntity().getDisplayName() + " nearby..");
            }
        }

    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        plugin.logger.info("Player joined");
        Player joiner = event.getPlayer();
        String uuid = joiner.getUniqueId().toString();
        Boolean isBloody = safe.getBoolean("uuid."+uuid);
        if(isBloody != null){
        if (isBloody){
            joiner.setPlayerListName(ChatColor.DARK_RED + joiner.getDisplayName());
        }}


    }
}
