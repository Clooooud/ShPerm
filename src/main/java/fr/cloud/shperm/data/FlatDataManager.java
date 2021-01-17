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
        clearFile();
        plugin.getShPermAPI().getUsers().forEach(this::saveUser);
        super.save();
    }

    private void saveUser(final User user) {
        getConfig().set(user.getUUID().toString() + ".prefix", user.getPrefix());
        getConfig().set(user.getUUID().toString() + ".suffix", user.getSuffix());
        getConfig().set(user.getUUID().toString() + ".prefixUse", user.isUsingPrefix());
        getConfig().set(user.getUUID().toString() + ".suffixUse", user.isUsingSuffix());
        getConfig().set(user.getUUID().toString() + ".permissions", user.getPermissionNodes().toArray(new String[0]));
        getConfig().set(user.getUUID().toString() + ".group", user.getGroup().getName());
    }

    @Override
    public final void load() {
        plugin.getShPermAPI().getUsers().clear();
        plugin.getShPermAPI().getUsers().addAll(getConfig().getKeys(false).stream().map(this::parseUser).collect(Collectors.toList()));
    }

    private User parseUser(final String uuid) {
        UUID userID = UUID.fromString(uuid);
        Group group = plugin.getShPermAPI().getGroup(getConfig().getString(uuid + ".group"));
        if (group == null) {
            plugin.getLogger().warning(String.format(plugin.getLangConfig().getNode("console.warning.nogroupfoundforuser"), uuid));
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
