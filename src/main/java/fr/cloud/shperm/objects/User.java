package fr.cloud.shperm.objects;

import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class User {

    private UUID uuid;
    private Group group;
    private String prefix, suffix;
    private boolean prefixUse, suffixUse;
    private final List<String> permissionNodes = new LinkedList<>();

    private Player player;

    public User(UUID uuid, Group group) {
        this.uuid = uuid;
        this.group = group;
    }

    public void addPermissionNode(String node) {
        permissionNodes.add(node);
    }

    public void removePermissionNode(String node) {
        permissionNodes.remove(node);
    }

    public List<String> getPermissionNodes() {
        return permissionNodes;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean isPrefixUse() {
        return prefixUse;
    }

    public void setPrefixUse(boolean prefixUse) {
        this.prefixUse = prefixUse;
    }

    public boolean isSuffixUse() {
        return suffixUse;
    }

    public void setSuffixUse(boolean suffixUse) {
        this.suffixUse = suffixUse;
    }
}
