package fr.cloud.shperm.commands.group.subs;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.Group;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

public class CreateSubCommand implements TabExecutor {

    private ShPerm plugin;

    public CreateSubCommand(ShPerm plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 1) {
            sender.sendMessage("§cUsage: /ShPerm group create <name>");
            return true;
        }

        if(plugin.getShPermAPI().getGroup(args[0].toLowerCase()) != null)
            sender.sendMessage(plugin.getLangConfig().getNode("commands.group.create.alreadyone"));

        plugin.getShPermAPI().getGroups().add(new Group(args[0].toLowerCase()));
        sender.sendMessage(String.format(plugin.getLangConfig().getNode("commands.group.create.success"), args[0]));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
