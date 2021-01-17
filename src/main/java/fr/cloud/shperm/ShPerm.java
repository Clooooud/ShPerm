package fr.cloud.shperm;

import fr.cloud.shperm.api.ShPermAPI;
import fr.cloud.shperm.commands.ShPermCommand;
import fr.cloud.shperm.config.GeneralConfig;
import fr.cloud.shperm.config.GroupConfig;
import fr.cloud.shperm.config.LangConfig;
import fr.cloud.shperm.data.DataManager;
import fr.cloud.shperm.data.FlatDataManager;
import fr.cloud.shperm.data.SQLDataManager;
import fr.cloud.shperm.events.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class ShPerm extends JavaPlugin {

    private GeneralConfig generalConfig;
    private GroupConfig groupConfig;
    private LangConfig langConfig;

    private DataManager dataManager;

    private ShPermAPI shPermAPI;

    private BukkitTask savingTask;

    @Override
    public final void onEnable() {

        initConfig();

        String defaultGroupName = getGeneralConfig().getDefaultGroupName();
        if (defaultGroupName == null || defaultGroupName.equals("")) {
            this.getLogger().severe(langConfig.getNode("console.error.nodefault"));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        shPermAPI = new ShPermAPI(this);
        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);

        dataManager = generalConfig.isUsingSQL() ? new SQLDataManager(this) : new FlatDataManager(this);

        load();

        getCommand("shperm").setExecutor(new ShPermCommand(this));

        launchSavingTask();

    }

    @Override
    public final void onDisable() {
        savingTask.cancel();
        saveAll();

        generalConfig = null;
        groupConfig = null;
        langConfig = null;
        shPermAPI = null;

        if(dataManager instanceof SQLDataManager) {
            ((SQLDataManager) dataManager).stopConnection();
        }
        dataManager = null;
    }

    @Override
    public final void reloadConfig() {
        langConfig.reload();
        groupConfig.reload();
        generalConfig.reload();
    }

    public final void load() {
        shPermAPI.loadGroups();
        dataManager.load();
    }

    public final void saveAll() {
        shPermAPI.saveGroups();
        dataManager.save();
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
        savingTask = Bukkit.getScheduler().runTaskTimer(this, this::saveAll, 15*60*20L, 15*60*20L);
    }
}
