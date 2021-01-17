package fr.cloud.shperm.dataholder;

import com.sun.istack.internal.NotNull;
import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.Group;
import fr.cloud.shperm.objects.User;

import java.util.*;
import java.util.stream.Collectors;

public class GlobalHolder {

    protected ShPerm plugin;

    public GlobalHolder(final ShPerm plugin) {
        this.plugin = plugin;
    }

    private final List<Group> groups = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    @NotNull
    public final List<Group> getGroups() {
        return groups;
    }

    @NotNull
    public final List<User> getUsers() {
        return users;
    }

    public final void saveGroups() {
        plugin.getGroupConfig().clearFile();
        this.groups.parallelStream().forEach(plugin.getGroupConfig()::addGroupToConfig);
        plugin.getGroupConfig().save();
    }

    public final void loadGroups() {
        this.groups.clear();
        this.groups.addAll(plugin.getGroupConfig().getEveryGroups());
        this.groups.parallelStream().forEach(this::replaceAllTemporaryGroups);
    }

    private void replaceAllTemporaryGroups(final Group group) {
        List<Group> toAdd = group.getInheritants(false).parallelStream().map(this::replaceTemporaryGroup).filter(Objects::nonNull).collect(Collectors.toList());
        group.getInheritants(false).clear();
        group.getInheritants(false).addAll(toAdd);
    }

    private Group replaceTemporaryGroup(final Group temporary) {
        Group groupToReturn = this.groups.parallelStream().filter(group -> group.getName().equals(temporary.getName()) && temporary.isTemporary()).findAny().orElse(null);

        if (groupToReturn == null) {
            plugin.getLogger().warning("An inheritance called \"" + temporary.getName() + "\" couldn't be recognized");
        }

        return groupToReturn;
    }
}
