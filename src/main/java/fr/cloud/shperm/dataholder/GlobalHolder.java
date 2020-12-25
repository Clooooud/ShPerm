package fr.cloud.shperm.dataholder;

import com.sun.istack.internal.NotNull;
import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.Group;
import fr.cloud.shperm.objects.User;

import java.util.ArrayList;
import java.util.List;

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


}
