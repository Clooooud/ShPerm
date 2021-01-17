package fr.cloud.shperm.config;

import fr.cloud.shperm.ShPerm;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BaseConfig {

    protected File file;
    protected FileConfiguration config;
    protected String name;

    protected ShPerm plugin;

    protected BaseConfig(final String name, final ShPerm plugin) {
        this.plugin = plugin;
        this.name = name;
        this.file = new File(plugin.getDataFolder(), this.name + ".yml");
        checkConfig();
        reload();
    }

    public final void reload() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public final FileConfiguration getConfig() {
        if(config == null)
            reload();
        return this.config;
    }

    // Check if the actual config saved has the same amount of keys that the one contained inside the jar
    private void checkConfig() {

        if(this.file.exists()) {

            InputStream stream = this.plugin.getResource(this.name + ".yml");

            if(stream == null) {
                plugin.getLogger().severe(String.format(plugin.getLangConfig().getNode("console.error.noconfigfound"), this.name));
                return;
            }

            FileConfiguration tempConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(stream));

            if(getConfig().getKeys(true).containsAll(tempConfig.getKeys(true))) {
                return;
            }
        }

        this.plugin.saveResource(this.name + ".yml", true);
    }

    public void clearFile() {
        getConfig().getKeys(false).forEach(key -> getConfig().set(key, null));
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
