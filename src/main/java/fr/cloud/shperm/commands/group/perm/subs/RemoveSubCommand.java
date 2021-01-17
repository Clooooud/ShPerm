package fr.cloud.shperm.commands.group.perm.subs;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.Group;
import fr.cloud.shperm.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

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
            sender.sendMessage("§cUsage: /ShPerm group perm remove <group> <node>");
            return true;
        }

        Group group = plugin.getShPermAPI().getGroup(args[0].toLowerCase());
        if (group == null) {
            sender.sendMessage(plugin.getLangConfig().getNode("commands.groupnotfound"));
            return true;
        }

        boolean result = group.getPermissionNodes().remove(args[1].toLowerCase());
        sender.sendMessage(plugin.getLangConfig().getNode("commands.group.perm.remove." + (result ? "success" : "notfound")));
        if (result) {
            plugin.getShPermAPI().getUsersFromGroup(group).forEach(user -> user.applyPermissions(plugin));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1) {
            return plugin.getShPermAPI().getGroups().parallelStream().map(Group::getName).collect(Collectors.toList());
        } else if (args.length == 2) {
            Group group = plugin.getShPermAPI().getGroup(args[0]);

            if(group != null) {
                return group.getPermissionNodes();
            }
        }
        return Collections.emptyList();
    }
}
