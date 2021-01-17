package fr.cloud.shperm.config;

import fr.cloud.shperm.ShPerm;

import java.util.List;
import java.util.stream.Collectors;

public final class LangConfig extends BaseConfig {

    private final String language;

    public LangConfig(final ShPerm plugin, final String lang) {
        super("lang", plugin);
        this.language = lang;
    }

    public final String getNode(final String node) {
        final String s = getConfig().getString("lang." + language + "." + node);
        if(s == null) {
            return null;
        }
        return s.replace("&", "§");
    }

    public final List<String> getNodeList(final String node) {
        return getConfig().getStringList("lang." + language + "." + node).parallelStream().map(s -> s.replace("&", "§")).collect(Collectors.toList());
    }

}
