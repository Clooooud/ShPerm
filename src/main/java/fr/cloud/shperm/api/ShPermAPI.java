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

public class ShPermAPI extends GlobalHolder {

    private ShPerm plugin;

    public ShPermAPI(ShPerm plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    public void addGroup(Group group) {
        getGroups().add(group);
    }

    @Nullable
    public Group getGroup(String groupName) {
        return getGroups().stream().parallel().filter(group -> group.getName().equals(groupName)).findFirst().orElse(null);
    }

    @Nullable
    public User getUser(UUID uuid) {
        return getUsers().stream().parallel().filter(user -> user.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    @NotNull
    public List<User> getUsersFromGroup(Group group) {
        return getUsers().stream().parallel().filter(user -> user.getGroup().equals(group)).collect(Collectors.toList());
    }


}
