package ua.dkotov.tplugin.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.dkotov.tplugin.TPlugin;


@org.bukkit.plugin.java.annotation.command.Command(name = "stick")
public class StickCommand implements CommandExecutor {

    private final TPlugin main;

    public StickCommand(TPlugin tPlugin) {
        this.main = tPlugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s,String[] strings) {
        Player p = (Player) commandSender;
        ItemStack is = new ItemStack(Material.STICK);
        is.addEnchantment(Enchantment.KNOCKBACK, 1);
        p.getInventory().addItem(is);
        return false;
    }
}
