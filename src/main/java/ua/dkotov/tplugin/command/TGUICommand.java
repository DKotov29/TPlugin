package ua.dkotov.tplugin.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ua.dkotov.tplugin.TPlugin;

@org.bukkit.plugin.java.annotation.command.Command(name = "tgui")
public class TGUICommand implements CommandExecutor {

    private final TPlugin main;

    public TGUICommand(TPlugin tPlugin) {
        this.main = tPlugin;
    }

    @Override
    public boolean onCommand( CommandSender commandSender,Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        Inventory inv = main.getServer().createInventory(p, 3, main.getStringFromConfig("tgui_name"));
        main.getPendingInv().add(inv.hashCode());
        for (String tgui_place : main.getConfig().getStringList("tgui_places")) {
            String[] poss= tgui_place.split(";;");
            ItemStack iss = new ItemStack(Material.matchMaterial(poss[1]), Integer.parseInt(poss[2]));
            ItemMeta im = iss.getItemMeta();
            im.setDisplayName(poss[3]);
            iss.setItemMeta(im);
            inv.setItem(Integer.parseInt(poss[0]), iss);
        }
        p.openInventory(inv);
        return false;
    }
}
