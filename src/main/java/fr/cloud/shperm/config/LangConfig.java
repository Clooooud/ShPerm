package fr.cloud.shperm.config;

import fr.cloud.shperm.ShPerm;

import java.util.List;
import java.util.stream.Collectors;

public class LangConfig extends BaseConfig {

    public LangConfig(ShPerm plugin) {
        super("lang", plugin);
    }

    public String getNode(String node) {
        String s = getConfig().getString(node);
        if(s == null) {
            return null;
        }
        return s.replace("&", "§");
    }

    public List<String> getNodeList(String node) {
        return getConfig().getStringList(node).stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());
    }

}
