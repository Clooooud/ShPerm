package fr.cloud.shperm.data;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.config.BaseConfig;
import fr.cloud.shperm.objects.Group;
import fr.cloud.shperm.objects.User;

import java.util.UUID;
import java.util.stream.Collectors;

public final class FlatDataManager extends BaseConfig implements DataManager {

    private final ShPerm plugin;

    public FlatDataManager(final ShPerm plugin) {
        super("users", plugin);
        this.plugin = plugin;
    }

    @Override
    public final void save() {
        plugin.getShPermAPI().getUsers().forEach(this::saveUser);
        super.save();
    }

    private void saveUser(final User user) {
        getConfig().set(user.getUuid().toString() + ".prefix", user.getPrefix());
        getConfig().set(user.getUuid().toString() + ".suffix", user.getSuffix());
        getConfig().set(user.getUuid().toString() + ".prefixUse", user.isUsingPrefix());
        getConfig().set(user.getUuid().toString() + ".suffixUse", user.isUsingSuffix());
        getConfig().set(user.getUuid().toString() + ".permissions", user.getPermissionNodes());
        getConfig().set(user.getUuid().toString() + ".group", user.getGroup().getName());
    }

    @Override
    public final void load() {
        plugin.getShPermAPI().getUsers().addAll(getConfig().getKeys(false).stream().map(this::parseUser).collect(Collectors.toList()));
    }

    private User parseUser(final String uuid) {
        UUID userID = UUID.fromString(uuid);
        Group group = plugin.getShPermAPI().getGroup(getConfig().getString(uuid + ".group"));
        if (group == null) {
            plugin.getLogger().warning("Group for " + uuid + " could not be recognized, giving him the default rank.");
            group = plugin.getShPermAPI().getDefaultGroup();
        }
        User user = new User(userID, group);
        user.setPrefix(getConfig().getString(uuid + ".prefix"));
        user.setSuffix(getConfig().getString(uuid + ".suffix"));
        user.setPrefixUse(getConfig().getBoolean(uuid + ".prefixUse"));
        user.setSuffixUse(getConfig().getBoolean(uuid + ".suffixUse"));
        user.getPermissionNodes().addAll(getConfig().getStringList(uuid + ".permissions"));

        return user;
    }
}
