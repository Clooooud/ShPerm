package fr.cloud.shperm.commands.subs;

import fr.cloud.shperm.ShPerm;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

public class VersionSubCommand implements TabExecutor {

    private ShPerm plugin;

    public VersionSubCommand(ShPerm plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§aShPerm §8version §a" + plugin.getDescription().getVersion());
        sender.sendMessage("§8By §a" + plugin.getDescription().getAuthors());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}

