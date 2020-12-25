package fr.cloud.shperm.objects;

import com.sun.istack.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public final class User {

    private UUID uuid;
    private Group group;
    private String prefix, suffix;
    private boolean prefixUse, suffixUse;
    private final List<String> permissionNodes = new LinkedList<>();

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

    public final List<String> getPermissionNodes() {
        return permissionNodes;
    }

    public final UUID getUuid() {
        return uuid;
    }

    public final void setUuid(final UUID uuid) {
        this.uuid = uuid;
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

    public final boolean isPrefixUse() {
        return prefixUse;
    }

    public final void setPrefixUse(final boolean prefixUse) {
        this.prefixUse = prefixUse;
    }

    public final boolean isSuffixUse() {
        return suffixUse;
    }

    public final void setSuffixUse(final boolean suffixUse) {
        this.suffixUse = suffixUse;
    }
}
