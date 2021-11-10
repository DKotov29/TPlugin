package ua.dkotov.tplugin;

import com.earth2me.essentials.Essentials;
import net.ess3.api.events.JailStatusChangeEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EssentialsEvent implements Listener {

    private final TPlugin main;

    public EssentialsEvent(TPlugin tPlugin) {
        this.main = tPlugin;
    }

    @EventHandler
    public void JailEvent(JailStatusChangeEvent event) {
        main.sql_exec("insert into tplugin values(" + event.getValue() + ", " + event.getAffected().getName() +");");
        TextComponent message = new TextComponent("Some text with url and hint");
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.google.com"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("hint")));
        main.getServer().getPlayer(event.getAffected().getName()).spigot().sendMessage(message);
    }
}
