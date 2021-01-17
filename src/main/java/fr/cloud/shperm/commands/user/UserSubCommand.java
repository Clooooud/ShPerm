package fr.cloud.shperm.commands.user;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.commands.CommandBase;
import fr.cloud.shperm.commands.user.perm.PermSubCommand;
import fr.cloud.shperm.commands.user.subs.InfoSubCommand;
import fr.cloud.shperm.commands.user.subs.SaveSubCommand;
import fr.cloud.shperm.commands.user.subs.SetSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class UserSubCommand extends CommandBase {

    public UserSubCommand(ShPerm plugin) {
        super(plugin);
        registerSubs();
    }

    private void registerSubs() {
        registerSubCommand("info", new InfoSubCommand(plugin));
        registerSubCommand("perm", new PermSubCommand(plugin));
        registerSubCommand("save", new SaveSubCommand(plugin));
        registerSubCommand("set", new SetSubCommand(plugin));
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
        sender.sendMessage(plugin.getLangConfig().getNodeList("commands.help.user").toArray(new String[0]));
        return true;
    }
}
