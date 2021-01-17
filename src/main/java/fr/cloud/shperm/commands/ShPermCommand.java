package fr.cloud.shperm.commands;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.commands.group.GroupSubCommand;
import fr.cloud.shperm.commands.subs.ReloadSubCommand;
import fr.cloud.shperm.commands.subs.SaveSubCommand;
import fr.cloud.shperm.commands.subs.VersionSubCommand;
import fr.cloud.shperm.commands.user.UserSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ShPermCommand extends CommandBase {

    public ShPermCommand(ShPerm plugin) {
        super(plugin);
        registerSubs();
    }

    private void registerSubs() {
        registerSubCommand("user", new UserSubCommand(plugin));
        registerSubCommand("group", new GroupSubCommand(plugin));
        registerSubCommand("version", new VersionSubCommand(plugin));
        registerSubCommand("save", new SaveSubCommand(plugin));
        registerSubCommand("reload", new ReloadSubCommand(plugin));
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
        sender.sendMessage(plugin.getLangConfig().getNodeList("commands.help.main").toArray(new String[0]));
        return true;
    }

}
