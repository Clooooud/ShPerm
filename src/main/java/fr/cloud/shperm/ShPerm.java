package fr.cloud.shperm;

import fr.cloud.shperm.api.ShPermAPI;
import fr.cloud.shperm.config.GeneralConfig;
import fr.cloud.shperm.config.GroupConfig;
import fr.cloud.shperm.config.LangConfig;
import fr.cloud.shperm.data.DataManager;
import fr.cloud.shperm.events.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShPerm extends JavaPlugin {

    private GeneralConfig generalConfig;
    private GroupConfig groupConfig;
    private LangConfig langConfig;

    private DataManager dataManager;

    private static ShPermAPI shPermAPI;

    @Override
    public final void onEnable() {

        initConfig();

        String defaultGroupName = getGeneralConfig().getDefaultGroupName();
        if (defaultGroupName == null || defaultGroupName.equals("")) {
            this.getLogger().severe("No default group found, disabling the plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        shPermAPI = new ShPermAPI(this);
        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);

        // TODO: Initialisation dataManager

    }

    @Override
    public final void onDisable() {
        generalConfig = null;
        groupConfig = null;
        langConfig = null;
        shPermAPI = null;
        dataManager = null;
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
