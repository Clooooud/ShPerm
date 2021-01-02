package fr.cloud.shperm.config;

import fr.cloud.shperm.ShPerm;

public final class GeneralConfig extends BaseConfig {

    public GeneralConfig(final ShPerm plugin) {
        super("config", plugin);
    }

    public final String getDefaultGroupName() {
        return getConfig().getString("default-group");
    }

    public final boolean isUsingSQL() {
        return getConfig().getString("save-system") != null && getConfig().getString("save-system").equalsIgnoreCase("sql");
    }

    public final String getSQLAdress() {
        return String.format("jdbc:mysql://%1$s:%2$s/%3$s", getConfig().getString("host"), getConfig().getString("port"), getConfig().getString("database")) + "?autoReconnect=true&useSSL=false";
    }

    public final String getSQLUsername() {
        return getConfig().getString("username");
    }

    public final String getSQLPassword() {
        return getConfig().getString("password");
    }

    public final String getLanguage() {
        return getConfig().getString("lang");
    }

}
