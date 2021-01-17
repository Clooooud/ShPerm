package fr.cloud.shperm.commands.group.subs;

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
        plugin.getShPermAPI().saveGroups();
        sender.sendMessage(plugin.getLangConfig().getNode("commands.group.save"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
