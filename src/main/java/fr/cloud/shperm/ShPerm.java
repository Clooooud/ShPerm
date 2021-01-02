package fr.cloud.shperm;

import fr.cloud.shperm.api.ShPermAPI;
import fr.cloud.shperm.config.GeneralConfig;
import fr.cloud.shperm.config.GroupConfig;
import fr.cloud.shperm.config.LangConfig;
import fr.cloud.shperm.data.DataManager;
import fr.cloud.shperm.data.FlatDataManager;
import fr.cloud.shperm.data.SQLDataManager;
import fr.cloud.shperm.events.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShPerm extends JavaPlugin {

    private GeneralConfig generalConfig;
    private GroupConfig groupConfig;
    private LangConfig langConfig;

    private DataManager dataManager;

    private ShPermAPI shPermAPI;

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

        dataManager = generalConfig.isUsingSQL() ? new SQLDataManager(this) : new FlatDataManager(this);

        shPermAPI.loadGroups();
        dataManager.load();

        launchSavingTask();

        // Testing
        getShPermAPI().getUsers().forEach(System.out::println);
        getShPermAPI().getGroups().forEach(System.out::println);

    }

    public final void saveAll() {
        shPermAPI.saveGroups();
        dataManager.save();
    }

    @Override
    public final void onDisable() {
        generalConfig = null;
        groupConfig = null;
        langConfig = null;
        shPermAPI = null;

        if(dataManager instanceof SQLDataManager) {
            ((SQLDataManager) dataManager).stopConnection();
        }
        dataManager = null;
    }

    private void initConfig() {
        generalConfig = new GeneralConfig(this);
        groupConfig = new GroupConfig(this);
        langConfig = new LangConfig(this, generalConfig.getLanguage());
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

    public final DataManager getDataManager() {
        return dataManager;
    }

    private void launchSavingTask() {
        Bukkit.getScheduler().runTaskTimer(this, this::saveAll, 15*60*20L, 15*60*20L);
    }
}
