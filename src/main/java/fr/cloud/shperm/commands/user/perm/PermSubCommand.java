package fr.cloud.shperm.commands.user.perm;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.commands.CommandBase;
import fr.cloud.shperm.commands.user.perm.subs.AddSubCommand;
import fr.cloud.shperm.commands.user.perm.subs.GetSubCommand;
import fr.cloud.shperm.commands.user.perm.subs.RemoveAllSubCommand;
import fr.cloud.shperm.commands.user.perm.subs.RemoveSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class PermSubCommand extends CommandBase {

    public PermSubCommand(ShPerm plugin) {
        super(plugin);
        registerSubs();
    }

    private void registerSubs() {
        registerSubCommand("add", new AddSubCommand(plugin));
        registerSubCommand("remove", new RemoveSubCommand(plugin));
        registerSubCommand("removeall", new RemoveAllSubCommand(plugin));
        registerSubCommand("get", new GetSubCommand(plugin));
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
        sender.sendMessage(plugin.getLangConfig().getNodeList("commands.help.userperm").toArray(new String[0]));
        return true;
    }
}
