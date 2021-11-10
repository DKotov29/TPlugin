package ua.dkotov.tplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class InventoryHandler implements Listener {

    private final TPlugin main;
    private final Map<Integer, String[]> cmd_map;

    public InventoryHandler(TPlugin tPlugin) {
        this.main = tPlugin;
        cmd_map = new HashMap<>();
        for (String tgui_place : main.getConfig().getStringList("tgui_places")) {
            String[] poss = tgui_place.split(";;");
            cmd_map.put(Integer.parseInt(poss[0]), poss[4].split(","));
        }
    }

    @EventHandler
    public void InvClickEvent(InventoryClickEvent event) {
        int h = event.getClickedInventory().hashCode();
        if (main.getPendingInv().contains(h)) {
            if (cmd_map.containsKey(event.getSlot())) {
                String[] m = cmd_map.get(event.getSlot());
                if (m[0].equals("close")) {
                    event.getWhoClicked().closeInventory();
                    return;
                }
                for (String s : m) {
                    if (s.startsWith("effect")) {
                        s.replace("effect_", "");
                        event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.getByName(s), 100, 1));
                    } else if (s.equals("noeffect")) {
                        for (PotionEffect activePotionEffect : event.getWhoClicked().getActivePotionEffects()) {
                            event.getWhoClicked().removePotionEffect(activePotionEffect.getType());
                        }
                    } else if (!s.equals("")) {
                        main.getServer().dispatchCommand(Bukkit.getConsoleSender(), s);
                    }
                }

            }
        }
    }

    @EventHandler
    public void InvCloseEvent(InventoryCloseEvent event) {
        int h = event.getInventory().hashCode();
        if (main.getPendingInv().contains(h)) {
            main.getPendingInv().remove(h);
        }
    }
}
