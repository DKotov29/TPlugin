package ua.dkotov.tplugin;

import com.earth2me.essentials.Essentials;
import net.ess3.api.MaxMoneyException;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitTask;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SimpleHandler implements Listener {

    private final TPlugin main;
    private final Map<Player, Byte> player_stage;
    private final Essentials essentials;

    public SimpleHandler(TPlugin tPlugin) {
        this.main = tPlugin;
        player_stage = new HashMap<>();
        essentials = (Essentials) main.getServer().getPluginManager().getPlugin("Essentials");
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        BaseComponent[] tc = TextComponent.fromLegacyText("Привет " + p.getName());
        player_stage.put(p, (byte) 0);
        event.getPlayer().sendTitle("§l§6Привет " + event.getPlayer().getName(), "", 10, 70, 20);
        Bukkit.getScheduler().runTaskTimerAsynchronously(main, bukkitTask -> {
            if (player_stage.get(p) == 4){

                try {
                    essentials.getUser(p.getUniqueId()).giveMoney(BigDecimal.valueOf(50));
                } catch (MaxMoneyException e) {
                    p.sendMessage("увы, без денег, достигнут предел");
                }
                player_stage.remove(p);
                player_stage.put(p, (byte) 0);
            } else {
                byte b = player_stage.remove(p);
                player_stage.put(p,  b++);
            }
            p.giveExp(12);
        }, 600L, 600L);
    }
}
