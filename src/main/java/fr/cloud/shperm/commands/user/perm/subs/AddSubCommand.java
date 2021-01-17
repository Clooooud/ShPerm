package fr.cloud.shperm.commands.user.perm.subs;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddSubCommand implements TabExecutor {

    private ShPerm plugin;

    public AddSubCommand(ShPerm plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 2) {
            sender.sendMessage("§cUsage: /ShPerm user perm add <target> <node>");
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

        user.getPermissionNodes().add(args[1].toLowerCase());
        user.markAsUpdated();
        user.applyPermissions(plugin);
        sender.sendMessage(plugin.getLangConfig().getNode("commands.user.perm.add.success"));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1) {
            return Bukkit.getOnlinePlayers().parallelStream().map(Player::getDisplayName).filter(str -> str.startsWith(args[0])).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
