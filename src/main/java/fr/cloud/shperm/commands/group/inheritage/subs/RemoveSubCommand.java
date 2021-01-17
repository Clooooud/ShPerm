package fr.cloud.shperm.commands.group.inheritage.subs;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.Group;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveSubCommand implements TabExecutor {

    private ShPerm plugin;

    public RemoveSubCommand(ShPerm plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 2) {
            sender.sendMessage("§cUsage: /ShPerm group inheritage remove <group> <inheritant>");
            return true;
        }

        Group group = plugin.getShPermAPI().getGroup(args[0].toLowerCase());
        if (group == null) {
            sender.sendMessage(plugin.getLangConfig().getNode("commands.groupnotfound"));
            return true;
        }

        Group inheritant = plugin.getShPermAPI().getGroup(args[1].toLowerCase());
        if (inheritant == null) {
            sender.sendMessage(plugin.getLangConfig().getNode("commands.groupnotfound"));
            return true;
        }

        if (!group.getInheritants(false).contains(inheritant)) {
            sender.sendMessage(String.format(plugin.getLangConfig().getNode("commands.group.inheritage.remove.notinheritant"), group.getName()));
            return true;
        }

        group.getInheritants(false).remove(inheritant);
        plugin.getShPermAPI().getUsersFromGroup(group).forEach(user -> user.applyPermissions(plugin));
        sender.sendMessage(String.format(plugin.getLangConfig().getNode("commands.group.inheritage.remove.success"), group.getName()));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1) {
            return plugin.getShPermAPI().getGroups().parallelStream().map(Group::getName).collect(Collectors.toList());
        } else if (args.length == 2) {
            Group group = plugin.getShPermAPI().getGroup(args[0]);

            if(group != null) {
                return group.getInheritants(false).stream().map(Group::getName).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
