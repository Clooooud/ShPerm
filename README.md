# ShPerm #

## API ##

L'API fonctionne assez simplement, il faut juste récupérer la classe depuis Bukkit `PluginManager#getPlugin(String pluginName)` (Le nom du plugin étant "ShPerm") puis utiliser la fonction `ShPerm#getShPermAPI`

Je vous conseille d'ajouter le plugin à vos dépendances pour avoir accès aux fonctions plus facilement

##

### `ShPermAPI#addGroup(Group group)`
Ajoute le groupe `group` à la liste des groupes du plugin

##

### `Group` `ShPermAPI#getGroup(String groupName)`
##### `@Nullable`
Retourne le groupe ayant le nom `groupName`

##

### `User` `ShPermAPI#getUser(UUID uuid)`
##### `@Nullable`
Retourne l'objet User de l'utilisateur ayant pour UUID `uuid`

##

### `List<User>` `ShPermAPI#getUsersFromGroup(Group group)`
##### `@NotNull`
Retourne une `List<User>` comportant tout les User du groupe spécifié

