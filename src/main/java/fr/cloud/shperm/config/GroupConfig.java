package fr.cloud.shperm.config;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.Group;

import java.util.List;
import java.util.stream.Collectors;

public final class GroupConfig extends BaseConfig {

    public GroupConfig(final ShPerm plugin) {
        super("groups", plugin);
    }

    public final void addGroupToConfig(final Group group) {
        getConfig().set(group.getName() + ".prefix", group.getPrefix());
        getConfig().set(group.getName() + ".suffix", group.getSuffix());
        getConfig().set(group.getName() + ".permissions", group.getPermissionNodes().toArray(new String[0]));
        getConfig().set(group.getName() + ".inheritants", group.getInheritants(false).stream().parallel().map(Group::getName).toArray(String[]::new));
    }

    public final List<Group> getEveryGroups() {
        return getConfig().getKeys(false).stream().map(this::getGroup).collect(Collectors.toList());
    }

    public final Group getGroup(final String name) {
        return parseGroup(name);
    }

    private Group parseGroup(final String name) {
        final Group group = new Group(name);
        group.setPrefix(getConfig().getString(name + ".prefix"));
        group.setSuffix(getConfig().getString(name + ".suffix"));
        group.getPermissionNodes().addAll(getConfig().getStringList(name + ".permissions"));
        group.getInheritants(false).addAll(getConfig().getStringList(name + ".inheritants").stream().map(s -> new Group(s, true)).collect(Collectors.toList()));
        return group;
    }

}
