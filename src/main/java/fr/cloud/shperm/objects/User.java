package fr.cloud.shperm.objects;

import com.sun.istack.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public final class User {

    private final UUID uuid;
    private Group group;
    private String prefix = "", suffix = "";
    private boolean prefixUse, suffixUse;
    private final Set<String> permissionNodes = new HashSet<>();

    public User(final UUID uuid, final Group group) {
        this.uuid = uuid;
        this.group = group;
    }

    @Nullable
    public final Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public final void addPermissionNode(final String node) {
        permissionNodes.add(node);
    }

    public final void removePermissionNode(final String node) {
        permissionNodes.remove(node);
    }

    public final Set<String> getPermissionNodes() {
        return permissionNodes;
    }

    public final UUID getUuid() {
        return uuid;
    }

    public final Group getGroup() {
        return group;
    }

    public final void setGroup(final Group group) {
        this.group = group;
    }

    public final String getPrefix() {
        return prefix;
    }

    public final void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public final String getSuffix() {
        return suffix;
    }

    public final void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    public final boolean isUsingPrefix() {
        return prefixUse;
    }

    public final void setPrefixUse(final boolean prefixUse) {
        this.prefixUse = prefixUse;
    }

    public final boolean isUsingSuffix() {
        return suffixUse;
    }

    public final void setSuffixUse(final boolean suffixUse) {
        this.suffixUse = suffixUse;
    }

    @Override
    public final String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", group='" + group.getName() + "'" +
                ", prefix='" + prefix + '\'' +
                ", suffix='" + suffix + '\'' +
                ", prefixUse=" + prefixUse +
                ", suffixUse=" + suffixUse +
                ", permissionNodes=" + permissionNodes +
                '}';
    }
}
