package fr.cloud.shperm.data;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SQLDataManager implements DataManager {

    private final ShPerm plugin;

    private Connection connection;

    public SQLDataManager(final ShPerm plugin) {
        this.plugin = plugin;
        setupSQL();
    }

    @Override
    public final void save() {
        long time1 = new java.util.Date().getTime();

        plugin.getShPermAPI().getUsers().parallelStream().filter(User::isNeedingSave).forEach(this::saveUser);

        long time2 = new java.util.Date().getTime();
        System.out.println("The database (save) request took: " + (time2-time1) + "ms");
    }

    @Override
    public final void load() {
        plugin.getShPermAPI().getUsers().addAll(getAllUsersFromDB());
    }

    private List<User> getAllUsersFromDB() {
        long time1 = new java.util.Date().getTime();

        List<User> users = new ArrayList<>();
        try (
                Statement statement = connection.createStatement();
                PreparedStatement userNodeStatement = connection.prepareStatement("SELECT DISTINCT UP_Node FROM UsersPermissions WHERE UP_US_UUID = ?")
        ) {

            ResultSet usersQuery = statement.executeQuery("SELECT * FROM Users");
            while (usersQuery.next()) {

                UUID uuid = UUID.fromString(usersQuery.getString(1));
                String group = usersQuery.getString(2);
                String prefix = usersQuery.getString(3);
                String suffix = usersQuery.getString(4);
                boolean usingPrefix = usersQuery.getBoolean(5);
                boolean usingSuffix = usersQuery.getBoolean(6);

                userNodeStatement.setString(1, uuid.toString());
                ResultSet userNodesQuery = userNodeStatement.executeQuery();
                List<String> nodes = new ArrayList<>();
                while (userNodesQuery.next()) {

                    nodes.add(userNodesQuery.getString(1));

                }

                User user = new User(uuid, plugin.getShPermAPI().getGroup(group));
                user.setPrefix(prefix);
                user.setSuffix(suffix);
                user.setPrefixUse(usingPrefix);
                user.setSuffixUse(usingSuffix);
                user.getPermissionNodes().addAll(nodes);

                users.add(user);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        long time2 = new java.util.Date().getTime();
        System.out.println("The database (load) request took: " + (time2-time1) + "ms");

        return users;
    }

    public final void stopConnection() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void saveUser(User user) {
        try (
                PreparedStatement userInsertStatement = connection.prepareStatement("INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?)");
                PreparedStatement userPermissionsInsertStatement = connection.prepareStatement("INSERT INTO UsersPermissions VALUES (?, ?)");
                PreparedStatement userDeleteStatement = connection.prepareStatement("DELETE FROM Users WHERE US_UUID = ?");
                PreparedStatement userPermissionsDeleteStatement = connection.prepareStatement("DELETE FROM UsersPermissions WHERE US_UUID = ?")
        ) {

            userDeleteStatement.setString(1, user.getUUID().toString());
            userDeleteStatement.execute();
            userPermissionsDeleteStatement.setString(1, user.getUUID().toString());
            userPermissionsDeleteStatement.execute();

            userInsertStatement.setString(1, user.getUUID().toString());
            userInsertStatement.setString(2, user.getGroup().getName());
            userInsertStatement.setString(3, user.getPrefix());
            userInsertStatement.setString(4, user.getSuffix());
            userInsertStatement.setBoolean(5, user.isUsingPrefix());
            userInsertStatement.setBoolean(6, user.isUsingSuffix());
            userInsertStatement.execute();

            userPermissionsInsertStatement.setString(1, user.getUUID().toString());
            user.getPermissionNodes().parallelStream().forEach(node -> {
                try {
                    userPermissionsInsertStatement.setString(2, node);
                    userPermissionsInsertStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupSQL() {
        if(connection == null)
            openConnection();

        try (Statement statement = connection.createStatement()) {

            statement.execute(
                    "CREATE TABLE IF NOT EXISTS Users (" +
                    "US_UUID VARCHAR(36) NOT NULL," +
                    "US_Group VARCHAR(128) NOT NULL," +
                    "US_Prefix VARCHAR(128) NOT NULL," +
                    "US_Suffix VARCHAR(128) NOT NULL," +
                    "US_UsePrefix BOOLEAN NOT NULL," +
                    "US_UseSuffix BOOLEAN NOT NULL," +
                    "PRIMARY KEY(US_UUID)" +
                    ");"
            );
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS UsersPermissions (" +
                    "UP_US_UUID VARCHAR(36) NOT NULL," +
                    "UP_Node VARCHAR(96) NOT NULL," +
                    "FOREIGN KEY(UP_US_UUID) REFERENCES Users(US_UUID));"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openConnection() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(plugin.getGeneralConfig().getSQLAdress(), plugin.getGeneralConfig().getSQLUsername(), plugin.getGeneralConfig().getSQLPassword());
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
