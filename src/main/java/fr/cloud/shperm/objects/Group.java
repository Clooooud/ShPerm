package fr.cloud.shperm.objects;

import java.util.LinkedList;
import java.util.List;

public class Group {

    private String name;
    private String prefix, suffix;
    private final List<Group> inheritances = new LinkedList<>();
    private final List<String> permissionNodes = new LinkedList<>();

    public Group(String name) {
        this.name = name;
    }

    public void addInheritance(Group group) {
        inheritances.add(group);
    }

    public void addPermissionNode(String node) {
        permissionNodes.add(node);
    }

    public void removePermissionNode(String node) {
        permissionNodes.remove(node);
    }

    public List<Group> getInheritances() {
        return inheritances;
    }

    public List<String> getPermissionNodes() {
        return permissionNodes;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
