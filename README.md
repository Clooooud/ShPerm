# ShPerm #

## API ##

L'API fonctionne assez simplement, il faut juste récupérer la classe depuis Bukkit `PluginManager#getPlugin(String pluginName)` (Le nom du plugin étant "ShPerm") puis utiliser la fonction `ShPerm#getShPermAPI` de cette manière :

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

Je vous conseille d'ajouter le plugin à vos dépendances pour avoir accès aux fonctions plus facilement

##

### `ShPermAPI#addGroup(Group group)`
Ajoute le groupe `group` à la liste des groupes du plugin

##

### `ShPermAPI#removeGroup(Group group)`
Retire le groupe `group` de la liste des groupes du plugin

##

### `Group` `ShPermAPI#getGroup(String groupName)`
##### `@Nullable`
Retourne le groupe ayant le nom `groupName`

##

### `User` `ShPermAPI#getUser(UUID uuid)`
##### `@NotNull`
Retourne l'objet User de l'utilisateur ayant pour UUID `uuid`

##

### `List<User>` `ShPermAPI#getUsersFromGroup(Group group)`
##### `@NotNull`
Retourne une `List<User>` comportant tout les User du groupe spécifié

##

### `Group` `ShPermAPI#getDefaultGroup()`
##### `@NotNull`
Retourne le groupe par défaut défini dans les configs

##

### `List<Group>` `ShPermAPI#getGroups()`
##### `@NotNull`
Retourne la liste de tous les groupes

##

### `List<User>` `ShPermAPI#getUsers()`
##### `@NotNull`
Retourne la liste de tous les utilisateurs