package ua.dkotov.tplugin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import ua.dkotov.tplugin.command.StickCommand;
import ua.dkotov.tplugin.command.TGUICommand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Plugin(name = "TPlugin", version = "1.0")
@Author("Dkotov")
@Description("Lorem ipsum dolor sit amen")
@Dependency("Essentials")
public class TPlugin extends JavaPlugin {

    private List<Integer> pendingInv;
    private Statement statement;
private Connection connection;

    @Override
    public void onEnable() {
        pendingInv = new ArrayList<>();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new InventoryHandler(this), this);
        getCommand("stick").setExecutor(new StickCommand(this));
        getCommand("tgui").setExecutor(new TGUICommand(this));


        if (getServer().getPluginManager().getPlugin("Essentials") != null) {
            try {
                connection = DriverManager.getConnection(getConfig().getString("db_url"),
                        getConfig().getString("db_user"), getConfig().getString("db_pass"));
                statement = connection.createStatement();
                statement.executeUpdate("create table if not exists tplugin(value int, player_name varchar(50));");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            getServer().getPluginManager().registerEvents(new SimpleHandler(this), this);
            getServer().getPluginManager().registerEvents(new EssentialsEvent(this), this);
        }
    }

    @Override
    public void onDisable() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getStringFromConfig(String path) {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString(path));
    }

    public List<Integer> getPendingInv() {
        return pendingInv;
    }

    public void sql_exec(String s){
        try {
            statement.executeUpdate(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

//todo
// + 1. Собственная реализация команды /stick выдача палки с зачарованиями
//
//2.  +Реализовать графический интерфейс в котором отдельно выполняются: heal, food, suicide и stick, а также комбо вариант heal+food
// -(3 и 4 пункты тоже засунуть в ГПИ)
//
//3.+ Выдать эффект игроку на 5 секунд (/stick, любой эффект)
//
//4. +Снять все эффекты через 2 секунды
//
//5.+ Сделать просто взаимодействие с базой MySQL: отловить ивент мута/кика/тюрьмы из ess и записать в таблицу
//
//6 +Вывести в чат интерактивное сообщение со ссылкой и подсказкой
//
//7 +При входе вывести Title «Привет %name%» с цветом и жирным форматированием
//
//8 за нахождение на сервере онлайн игроку раз в 30 секунд начислять опыт, и 50 баксов на баланс раз в 2 минуты