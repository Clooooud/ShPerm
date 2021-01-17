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

public class RemoveSubCommand implements TabExecutor {

    private ShPerm plugin;

    public RemoveSubCommand(ShPerm plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 2) {
            sender.sendMessage("§cUsage: /ShPerm user perm remove <target> <node>");
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

        boolean result = user.getPermissionNodes().remove(args[1].toLowerCase());
        user.markAsUpdated();
        user.applyPermissions(plugin);

        sender.sendMessage(plugin.getLangConfig().getNode("commands.user.perm.remove." + (result ? "success" : "notfound")));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1) {
            return Bukkit.getOnlinePlayers().parallelStream().map(Player::getDisplayName).filter(str -> str.startsWith(args[0])).collect(Collectors.toList());
        } else if (args.length == 2) {
            Player player = Bukkit.getPlayer(args[0]);

            if(player != null) {
                return plugin.getShPermAPI().getUser(player.getUniqueId()).getPermissionNodes().parallelStream().collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
