# ShPerm #

## API ##

This API works really simply, you just need to get the class from Bukkit `PluginManager#getPlugin(String pluginName)` (The name of the plugin being "ShPerm") then use the method `ShPerm#getShPermAPI` like this :

````java
import org.bukkit.plugin.java.JavaPlugin;
import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.api.ShPermAPI;

public class Main extends JavaPlugin {

    public void onEnable() {
        ShPerm perm = (ShPerm) Bukkit.getPluginManager().getPlugin("ShPerm");
        ShPermAPI permAPI = perm.getShPermAPI();
    }

}
````

You should add the plugin to your dependencies to have a faster access to the methods

##

### `ShPermAPI#addGroup(Group group)`
Add the group `group` to the list of groups of the plugin

##

### `ShPermAPI#removeGroup(Group group)`
Remove the group `group` from the list of groups of the plugin

##

### `Group` `ShPermAPI#getGroup(String groupName)`
##### `@Nullable`
Return the group having the name `groupName`

##

### `User` `ShPermAPI#getUser(UUID uuid)`
##### `@NotNull`
Return the User having the UUID `uuid`

##

### `List<User>` `ShPermAPI#getUsersFromGroup(Group group)`
##### `@NotNull`
Return a list `List<User>` with every user from the specified group

##

### `Group` `ShPermAPI#getDefaultGroup()`
##### `@NotNull`
Return the default group defined in the config

##

### `List<Group>` `ShPermAPI#getGroups()`
##### `@NotNull`
Return a list of every group

##

### `List<User>` `ShPermAPI#getUsers()`
##### `@NotNull`
Return a list of every user

##

### `ShPermAPI#loadGroups()`
Reload groups from file

##

### `ShPermAPI#saveGroups()`
Save groups to file
