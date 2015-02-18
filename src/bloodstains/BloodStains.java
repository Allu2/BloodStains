package bloodstains;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.entity.PlayerDeathEvent;
/**
 * Created by aleksi on 11.2.2015.
 */
public class BloodStains extends JavaPlugin {

    public static BloodStains plugin;
    public final ConfigAccessor data = new ConfigAccessor(this, "data.yml");
    public final Logger logger = Logger.getLogger("Minecraft");
    public final PlayerDeathListener playerDeathListener = new PlayerDeathListener(this);
    @Override
    public void onDisable() {
        PluginDescriptionFile pdffile = this.getDescription();
        this.logger.info(pdffile.getName() + " is now disabled");
    }
    @Override
    public void onEnable() {
        data.saveConfig();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(playerDeathListener, this);
        PluginDescriptionFile pdffile = this.getDescription();
        this.logger.info(pdffile.getName() + " version " + pdffile.getVersion() + " by Allyoutoo is Enabled");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(cmd.getName().equalsIgnoreCase("forgive")){ //Check for the command

            if(sender.hasPermission("bloodstains.forgive") && args.length>0){ //Check for permission node
                Player forgiven = (Player) Bukkit.getPlayer(args[0]);
                if(forgiven != null){
                    String name = forgiven.getDisplayName();
                    forgiven.setPlayerListName(name);
                    data.getConfig().set("uuid."+forgiven.getUniqueId().toString(), false);
                    data.saveConfig();
                    sender.sendMessage("Player "+ name + "'s hands have been washed from the blood.");
                }
                else{sender.sendMessage("Player is offline.");}
            return true;}
        }
        return false;
    }
}