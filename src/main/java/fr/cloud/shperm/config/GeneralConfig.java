package fr.cloud.shperm.config;

import fr.cloud.shperm.ShPerm;

public final class GeneralConfig extends BaseConfig {

    public GeneralConfig(final ShPerm plugin) {
        super("config", plugin);
    }

    public String getDefaultGroupName() {
        return getConfig().getString("default-group");
    }

    //TODO: Selectionner une langue

}
