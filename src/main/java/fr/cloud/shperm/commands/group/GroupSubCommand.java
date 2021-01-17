package fr.cloud.shperm.commands.group;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.commands.CommandBase;
import fr.cloud.shperm.commands.group.inheritage.InheritageSubCommand;
import fr.cloud.shperm.commands.group.perm.PermSubCommand;
import fr.cloud.shperm.commands.group.subs.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class GroupSubCommand extends CommandBase {

    public GroupSubCommand(ShPerm plugin) {
        super(plugin);
        registerSubs();
    }

    private void registerSubs() {
        registerSubCommand("perm", new PermSubCommand(plugin));
        registerSubCommand("create", new CreateSubCommand(plugin));
        registerSubCommand("delete", new DeleteSubCommand(plugin));
        registerSubCommand("list", new ListSubCommand(plugin));
        registerSubCommand("save", new SaveSubCommand(plugin));
        registerSubCommand("set", new SetSubCommand(plugin));
        registerSubCommand("inheritage", new InheritageSubCommand(plugin));
        registerSubCommand("info", new InfoSubCommand(plugin));
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
        sender.sendMessage(plugin.getLangConfig().getNodeList("commands.help.group").toArray(new String[0]));
        return true;
    }
}
