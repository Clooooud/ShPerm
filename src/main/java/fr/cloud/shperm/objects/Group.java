package fr.cloud.shperm.objects;

import java.util.*;
import java.util.stream.Collectors;

public final class Group {

    private String name;
    private String prefix = "", suffix = "";
    private final Set<Group> inheritances = new HashSet<>();
    private final List<String> permissionNodes = new LinkedList<>();
    private final boolean temporary;

    public Group(final String name) {
        this(name, false);
    }

    public Group(final String name, final boolean temporary) {
        this.name = name;
        this.temporary = temporary;
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

    public Set<Group> getInheritants(boolean deep) {
        if(deep)
            return getInheritants(this);
        return this.inheritances;
    }

    private Set<Group> getInheritants(Group group) {
        Set<Group> groups = new HashSet<>();

        group.inheritances.parallelStream().filter(gro -> !groups.contains(gro)).forEach(gro -> groups.addAll(getInheritants(gro)));

        groups.addAll(this.inheritances);
        return groups;
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

    public final boolean isTemporary() {
        return temporary;
    }

    @Override
    public final String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", prefix='" + prefix + '\'' +
                ", suffix='" + suffix + '\'' +
                ", inheritances=" + inheritances.stream().map(Group::getName).collect(Collectors.toList()) +
                ", permissionNodes=" + permissionNodes +
                ", isTemporary=" + temporary +
                '}';
    }
}
