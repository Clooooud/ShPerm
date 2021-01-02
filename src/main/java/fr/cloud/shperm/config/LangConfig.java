package fr.cloud.shperm.config;

import fr.cloud.shperm.ShPerm;

import java.util.List;
import java.util.stream.Collectors;

public final class LangConfig extends BaseConfig {

    public LangConfig(final ShPerm plugin) {
        super("lang", plugin);
    }

    public final String getNode(final String node) {
        String s = getConfig().getString(node);
        if(s == null) {
            return null;
        }
        return s.replace("&", "§");
    }

    public final List<String> getNodeList(final String node) {
        return getConfig().getStringList(node).stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());
    }

    // TODO: Agir selon les différents langages

}
