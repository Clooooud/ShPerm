package fr.cloud.shperm;

import fr.cloud.shperm.api.ShPermAPI;
import fr.cloud.shperm.config.GeneralConfig;
import fr.cloud.shperm.config.GroupConfig;
import fr.cloud.shperm.config.LangConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShPerm extends JavaPlugin {

    private GeneralConfig generalConfig;
    private GroupConfig groupConfig;
    private LangConfig langConfig;

    private static ShPermAPI shPermAPI;

    @Override
    public final void onEnable() {

        initConfig();

        shPermAPI = new ShPermAPI(this);

    }

    @Override
    public final void onDisable() {

    }

    private void initConfig() {
        generalConfig = new GeneralConfig(this);
        groupConfig = new GroupConfig(this);
        langConfig = new LangConfig(this);
    }

    public final LangConfig getLangConfig() {
        return langConfig;
    }

    public final GroupConfig getGroupConfig() {
        return groupConfig;
    }

    public final GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    public final ShPermAPI getShPermAPI() {
        return shPermAPI;
    }
}
