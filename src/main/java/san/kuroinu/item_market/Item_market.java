package san.kuroinu.item_market;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Item_market extends JavaPlugin {
    public static Economy econ = null;
    public static String prefix = "["+ChatColor.DARK_AQUA+"ItemMarket"+ChatColor.RESET+"]";
    public static JavaPlugin plugin;
    public static HikariDataSource ds;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin.saveDefaultConfig();
        // コネクションプールを作成
        HikariConfig conf = new HikariConfig();
        conf.setJdbcUrl(plugin.getConfig().getString("mysql.url"));
        conf.setUsername(plugin.getConfig().getString("mysql.user"));
        conf.setPassword(plugin.getConfig().getString("mysql.password"));
        HikariDataSource ds = new HikariDataSource(conf);
        //開始
        super.onEnable();
        plugin = this;
        //vault check
        if (!setupEconomy() ) {
            //ログを流す
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "Vault not found, disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        super.onEnable();
        ds.close();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        // Set the name of the item
        meta.setDisplayName(name);
        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}
