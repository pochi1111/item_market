package san.kuroinu.item_market;

import org.bukkit.OfflinePlayer;


import static san.kuroinu.item_market.Item_market.econ;
import static san.kuroinu.item_market.Item_market.plugin;

public class vault {
    public boolean withdraw_vault(String uuid,int price){
        //priceだけの金額があるかどうか
        OfflinePlayer player = plugin.getServer().getOfflinePlayer(uuid);
        if (econ.getBalance(player) < price) return false;
        econ.withdrawPlayer(player,price);
        return true;
    }
    public boolean deposit_vault(String uuid,int price){
        OfflinePlayer player = plugin.getServer().getOfflinePlayer(uuid);
        econ.depositPlayer(player,price);
        return true;
    }
}
