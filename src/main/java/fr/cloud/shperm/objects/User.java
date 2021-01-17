package fr.cloud.shperm.objects;

import com.sun.istack.internal.Nullable;
import fr.cloud.shperm.ShPerm;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;

public final class User {

    private static HashMap<Player, PermissionAttachment> attachments = new HashMap<>();

    private final UUID uuid;
    private final Set<String> permissionNodes = new HashSet<>();
    private boolean needSave = false;
    private Group group;
    private String prefix = "", suffix = "";
    private boolean prefixUse, suffixUse;

    public User(final UUID uuid, final Group group) {
        this.uuid = uuid;
        this.group = group;
    }

    public User(UUID uuid, Group group, boolean needSave) {
        this(uuid, group);
        this.needSave = needSave;
    }

    public final void markAsUpdated(boolean value) {
        needSave = value;
    }

    public final void markAsUpdated() {
        needSave = true;
    }

    public final boolean isNeedingSave() {
        return needSave;
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

    public final UUID getUUID() {
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

    public final void applyPermissions(ShPerm plugin) {
        Player player = this.getPlayer();
        if(player == null)
            return;

        if (attachments.containsKey(player)) {
            player.removeAttachment(attachments.get(player));
        }

        Set<String> permissions = new HashSet<>(this.group.getPermissionNodes());
        this.group.getInheritants(true).stream().map(Group::getPermissionNodes).forEach(permissions::addAll);
        permissions.addAll(this.permissionNodes);

        PermissionAttachment attachment = player.addAttachment(plugin);

        Arrays.asList("minecraft.command.me",
                "minecraft.command.help",
                "minecraft.command.msg",
                "bukkit.command.version",
                "bukkit.command.plugins",
                "bukkit.command.help").forEach(node -> attachment.setPermission(node, false));
        permissions.forEach(perm -> attachment.setPermission(perm, true));

        attachments.put(player, attachment);
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
