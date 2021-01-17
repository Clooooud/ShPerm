package fr.cloud.shperm.commands;

import fr.cloud.shperm.ShPerm;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class CommandBase implements TabExecutor {

    private final Map<String, TabExecutor> subCommands = new HashMap<>();
    protected final ShPerm plugin;


    public CommandBase(ShPerm plugin) {
        this.plugin = plugin;
    }

    public void registerSubCommand(String label, TabExecutor subCommand) {
        subCommands.put(label.toLowerCase(), subCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("shperm.admin") && !sender.isOp()) {
            if(subCommands.containsKey("version")){
                if(args[0].equalsIgnoreCase("version")){
                    subCommands.get("version").onCommand(sender, command, label, args);
                    return true;
                }
            }

            sender.sendMessage(plugin.getLangConfig().getNode("commands.noperm"));
            return true;
        }

        if (args.length > 0) {
            TabExecutor child = subCommands.get(args[0].toLowerCase());
            if (child != null) {
                label = args[0];
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, newArgs.length);
                return child.onCommand(sender, command, label, newArgs);
            }
        }
        return runCommand(sender, command, label, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("shperm.admin") && !sender.isOp()) {
            if(subCommands.containsKey("version") && args.length == 1) {
                return Collections.singletonList("version");
            }
            return Collections.emptyList();
        }

        if (args.length > 0) {
            TabExecutor child = subCommands.get(args[0].toLowerCase());
            if (child != null) {
                label = args[0];
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, newArgs.length);
                return child.onTabComplete(sender, command, label, newArgs);
            }
            return subCommands.keySet().parallelStream().filter(cmd -> cmd.startsWith(args[0])).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public abstract boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args);
}