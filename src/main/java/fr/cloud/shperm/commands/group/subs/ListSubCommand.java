package fr.cloud.shperm.commands.group.subs;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.Group;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListSubCommand implements TabExecutor {

    private ShPerm plugin;

    public ListSubCommand(ShPerm plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(String.format(plugin.getLangConfig().getNode("commands.group.list"), plugin.getShPermAPI().getGroups().stream().map(Group::getName).collect(Collectors.joining("§7, §a", "§a", ""))));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}