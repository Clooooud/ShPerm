package fr.cloud.shperm.commands.user.subs;

import fr.cloud.shperm.ShPerm;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

public class SaveSubCommand implements TabExecutor {

    private ShPerm plugin;

    public SaveSubCommand(ShPerm plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 0) {
            sender.sendMessage("§cUsage: /ShPerm user save");
            return true;
        }

        plugin.getDataManager().save();
        sender.sendMessage(plugin.getLangConfig().getNode("commands.user.saved"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
