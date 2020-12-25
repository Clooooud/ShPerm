package fr.cloud.shperm;

import fr.cloud.shperm.api.ShPermAPI;
import fr.cloud.shperm.config.GeneralConfig;
import fr.cloud.shperm.config.GroupConfig;
import fr.cloud.shperm.config.LangConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class ShPerm extends JavaPlugin {

    private GeneralConfig generalConfig;
    private GroupConfig groupConfig;
    private LangConfig langConfig;

    private static ShPermAPI shPermAPI;

    @Override
    public void onEnable() {

        initConfig();

        shPermAPI = new ShPermAPI(this);

    }

    @Override
    public void onDisable() {

    }

    private void initConfig() {
        generalConfig = new GeneralConfig(this);
        groupConfig = new GroupConfig(this);
        langConfig = new LangConfig(this);
    }

    public LangConfig getLangConfig() {
        return langConfig;
    }

    public GroupConfig getGroupConfig() {
        return groupConfig;
    }

    public GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    public static ShPermAPI getShPermAPI() {
        return shPermAPI;
    }
}
