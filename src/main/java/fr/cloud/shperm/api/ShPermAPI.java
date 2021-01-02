package fr.cloud.shperm.api;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.dataholder.GlobalHolder;
import fr.cloud.shperm.objects.Group;
import fr.cloud.shperm.objects.User;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class ShPermAPI extends GlobalHolder {

    public ShPermAPI(ShPerm plugin) {
        super(plugin);
    }

    public final void addGroup(final Group group) {
        getGroups().add(group);
    }

    public final void removeGroup(final Group group) { getGroups().remove(group); }

    @Nullable
    public final Group getGroup(final String groupName) {
        return getGroups().parallelStream().filter(group -> group.getName().equals(groupName)).findFirst().orElse(null);
    }

    @Nullable
    public final User getUser(final UUID uuid) {
        return getUsers().parallelStream().filter(user -> user.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    @NotNull
    public final List<User> getUsersFromGroup(final Group group) {
        return getUsers().parallelStream().filter(user -> user.getGroup().equals(group)).collect(Collectors.toList());
    }

    @NotNull
    public final Group getDefaultGroup() {
        return getGroup(plugin.getGeneralConfig().getDefaultGroupName());
    }


}
