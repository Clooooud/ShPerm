package fr.cloud.shperm.commands.group.inheritage;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.commands.CommandBase;
import fr.cloud.shperm.commands.group.inheritage.subs.AddSubCommand;
import fr.cloud.shperm.commands.group.inheritage.subs.GetSubCommand;
import fr.cloud.shperm.commands.group.inheritage.subs.RemoveAllSubCommand;
import fr.cloud.shperm.commands.group.inheritage.subs.RemoveSubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class InheritageSubCommand extends CommandBase {

    public InheritageSubCommand(ShPerm plugin) {
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
        sender.sendMessage(plugin.getLangConfig().getNodeList("commands.help.groupinherit").toArray(new String[0]));
        return true;
    }
}
