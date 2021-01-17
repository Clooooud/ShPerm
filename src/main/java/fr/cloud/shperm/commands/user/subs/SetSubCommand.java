package fr.cloud.shperm.commands.user.subs;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.Group;
import fr.cloud.shperm.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetSubCommand implements TabExecutor {

    private ShPerm plugin;

    private static final List<String> parameters = Arrays.asList("prefix", "suffix", "prefixuse", "suffixuse", "group");

    public SetSubCommand(ShPerm plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 2) {
            sender.sendMessage("§cUsage: /ShPerm user set <target> <parameter> <value>");
            return true;
        } else if (args.length == 2) {
            sender.sendMessage(String.format(plugin.getLangConfig().getNode("commands.user.set.parameters"), parameters.stream().collect(Collectors.joining("§7, §a", "§a", ""))));
            return true;
        }

        OfflinePlayer target = Stream.of(Bukkit.getOfflinePlayers())
                .filter(op -> Objects.nonNull(op.getName()))
                .filter(op -> op.getName().equalsIgnoreCase(args[0]))
                .findFirst().orElse(null);
        if(target == null) {
            sender.sendMessage(plugin.getLangConfig().getNode("commands.usernotfound"));
            return true;
        }

        User user = plugin.getShPermAPI().getUser(target.getUniqueId());

        switch (args[1].toLowerCase()) {
            case "group":
                Group group = plugin.getShPermAPI().getGroup(args[2]);
                if(group == null) {
                    sender.sendMessage(plugin.getLangConfig().getNode("commands.user.set.incorrectvalue"));
                    return true;
                }
                user.setGroup(group);
                user.applyPermissions(plugin);
                break;
            case "prefix":
                {
                    StringBuilder s1 = new StringBuilder();
                    for(int i = 2; i < args.length; i++){
                        if(!s1.toString().equals("")) s1.append(" ");
                        s1.append(args[i]);
                    }
                    user.setPrefix(s1.toString());
                }
                break;
            case "suffix":
                {
                    StringBuilder s1 = new StringBuilder();
                    for(int i = 2; i < args.length; i++){
                        if(!s1.toString().equals("")) s1.append(" ");
                        s1.append(args[i]);
                    }
                    user.setSuffix(s1.toString());
                }
                break;
            case "prefixuse":
                if(!Arrays.asList("true", "false").contains(args[2].toLowerCase())) {
                    sender.sendMessage(plugin.getLangConfig().getNode("commands.user.set.incorrectvalue"));
                    return true;
                }
                user.setPrefixUse(Boolean.parseBoolean(args[2]));
                break;
            case "suffixuse":
                if(!Arrays.asList("true", "false").contains(args[2].toLowerCase())) {
                    sender.sendMessage(plugin.getLangConfig().getNode("commands.user.set.incorrectvalue"));
                    return true;
                }
                user.setSuffixUse(Boolean.parseBoolean(args[2]));
                break;
            default:
                sender.sendMessage(String.format(plugin.getLangConfig().getNode("commands.user.set.parameters"), String.join(", ", parameters)));
                return true;
        }

        user.markAsUpdated();
        sender.sendMessage(plugin.getLangConfig().getNode("commands.user.set.success"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1:
                return Stream.of(Bukkit.getOfflinePlayers()).parallel().map(OfflinePlayer::getName).filter(Objects::nonNull).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
            case 2:
                return parameters;
            case 3:
                if (Arrays.asList("prefixuse", "suffixuse").contains(args[1].toLowerCase())) {
                    return Arrays.asList("true", "false");
                } else if (args[1].equalsIgnoreCase("group")) {
                    return plugin.getShPermAPI().getGroups().parallelStream().map(Group::getName).collect(Collectors.toList());
                }
        }
        return Collections.emptyList();
    }
}
