package fr.cloud.shperm.objects;

import java.util.LinkedList;
import java.util.List;

public final class Group {

    private String name;
    private String prefix, suffix;
    private final List<Group> inheritances = new LinkedList<>();
    private final List<String> permissionNodes = new LinkedList<>();

    public Group(final String name) {
        this.name = name;
    }

    public final void addInheritance(final Group group) {
        inheritances.add(group);
    }

    public final void addPermissionNode(final String node) {
        permissionNodes.add(node);
    }

    public final void removePermissionNode(final String node) {
        permissionNodes.remove(node);
    }

    public final List<Group> getInheritances() {
        return inheritances;
    }

    public final List<String> getPermissionNodes() {
        return permissionNodes;
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

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }
}
