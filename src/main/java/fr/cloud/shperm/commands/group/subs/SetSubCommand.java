package fr.cloud.shperm.commands.group.subs;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.Group;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SetSubCommand implements TabExecutor {

    private ShPerm plugin;

    private static final List<String> parameters = Arrays.asList("prefix", "suffix");

    public SetSubCommand(ShPerm plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /ShPerm group set <group> <parameter> <value>");
            return true;
        } else if (args.length == 2) {
            sender.sendMessage(String.format(plugin.getLangConfig().getNode("commands.group.set.parameters"), parameters.stream().collect(Collectors.joining("§7, §a", "§a", ""))));
            return true;
        }

        Group group = plugin.getShPermAPI().getGroup(args[0].toLowerCase());
        if (group == null) {
            sender.sendMessage(plugin.getLangConfig().getNode("commands.groupnotfound"));
            return true;
        }

        StringBuilder s1 = new StringBuilder();
        for(int i = 2; i < args.length; i++){
            if(!s1.toString().equals("")) s1.append(" ");
            s1.append(args[i]);
        }

        switch (args[1].toLowerCase()) {
            case "prefix":
                group.setPrefix(s1.toString());
                break;
            case "suffix":
                group.setSuffix(s1.toString());
                break;
            default:
                sender.sendMessage(String.format(plugin.getLangConfig().getNode("commands.group.set.parameters"), parameters.stream().collect(Collectors.joining("§7, §a", "§a", ""))));
                return true;
        }

        sender.sendMessage(plugin.getLangConfig().getNode("commands.group.set.success"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1:
                return plugin.getShPermAPI().getGroups().parallelStream().map(Group::getName).collect(Collectors.toList());
            case 2:
                return parameters;
        }
        return Collections.emptyList();
    }
}
