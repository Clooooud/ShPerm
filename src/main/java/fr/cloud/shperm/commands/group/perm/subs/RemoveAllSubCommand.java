package fr.cloud.shperm.commands.group.perm.subs;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.Group;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveAllSubCommand implements TabExecutor {

    private ShPerm plugin;

    public RemoveAllSubCommand(ShPerm plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 1) {
            sender.sendMessage("§cUsage: /ShPerm group perm removeall <group>");
            return true;
        }

        Group group = plugin.getShPermAPI().getGroup(args[0].toLowerCase());
        if (group == null) {
            sender.sendMessage(plugin.getLangConfig().getNode("commands.groupnotfound"));
            return true;
        }

        group.getPermissionNodes().clear();
        plugin.getShPermAPI().getUsersFromGroup(group).forEach(user -> user.applyPermissions(plugin));
        sender.sendMessage(plugin.getLangConfig().getNode("commands.group.perm.removeall.success"));

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1) {
            return plugin.getShPermAPI().getGroups().parallelStream().map(Group::getName).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
