package san.kuroinu.item_market;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;

public class item_string_converter {
    public String item_to_text(ItemStack item){
        YamlConfiguration config = new YamlConfiguration();
        config.set("i", item);
        return DatatypeConverter.printBase64Binary(config.saveToString().getBytes(StandardCharsets.UTF_8));
    }

    public ItemStack text_to_item(String string) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(new String(DatatypeConverter.parseBase64Binary(string), StandardCharsets.UTF_8));
        } catch (IllegalArgumentException | InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        return config.getItemStack("i", null);
    }
}
