package fr.cloud.shperm.commands.group.subs;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.Group;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteSubCommand implements TabExecutor {

    private ShPerm plugin;

    public DeleteSubCommand(ShPerm plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 1) {
            sender.sendMessage("§cUsage: /ShPerm group create <name>");
            return true;
        }

        Group group = plugin.getShPermAPI().getGroup(args[0].toLowerCase());
        if (group == null) {
            sender.sendMessage(plugin.getLangConfig().getNode("commands.groupnotfound"));
            return true;
        }

        if (group.equals(plugin.getShPermAPI().getDefaultGroup())) {
            sender.sendMessage(plugin.getLangConfig().getNode("commands.group.delete.defaultgroup"));
            return true;
        }

        plugin.getShPermAPI().getUsersFromGroup(group).forEach(user -> {
            user.setGroup(plugin.getShPermAPI().getDefaultGroup());
            user.markAsUpdated();
            user.applyPermissions(plugin);
        });
        plugin.getShPermAPI().getGroups().remove(group);
        sender.sendMessage(String.format(plugin.getLangConfig().getNode("commands.group.delete.success"), args[0]));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1)
            return plugin.getShPermAPI().getGroups().parallelStream().map(Group::getName).collect(Collectors.toList());
        return Collections.emptyList();
    }
}
