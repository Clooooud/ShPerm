package fr.cloud.shperm.events;

import fr.cloud.shperm.ShPerm;
import fr.cloud.shperm.objects.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public final class EventListener implements Listener {

    private final ShPerm plugin;

    public EventListener(final ShPerm plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public final void onJoin(final PlayerJoinEvent e) {
        plugin.getShPermAPI().getUser(e.getPlayer().getUniqueId());
    }

    @EventHandler()
    public final void onChat(final AsyncPlayerChatEvent e) {
        User user = plugin.getShPermAPI().getUser(e.getPlayer().getUniqueId());

        e.setFormat(
                (user.isUsingPrefix() ? user.getPrefix() : user.getGroup().getPrefix()).replace("&", "§") +
                "%s" +
                (user.isUsingSuffix() ? user.getSuffix() : user.getGroup().getSuffix()).replace("&", "§") +
                "%s"
        );
    }
}
