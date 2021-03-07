package me.profelements.dynatech.items.tools;

import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import me.profelements.dynatech.items.electric.Tesseract;

public class TesseractingObject extends SlimefunItem {

    public TesseractingObject(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
        
        addItemHandler(getItemHandler());
    }

    public TesseractingObject(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, ItemStack recipeOutput) {
        super(category, item, recipeType, recipe, recipeOutput);

        addItemHandler(getItemHandler());
    }

    private ItemUseHandler getItemHandler() {
        return new ItemUseHandler() {

            @Override
            public void onRightClick(PlayerRightClickEvent e) {
               Optional<Block> b = e.getClickedBlock();

               if (b.isPresent() && !e.getPlayer().isSneaking()) {
                    Block block = b.get();
                    SlimefunItem sfItem = BlockStorage.check(block);
                    if (sfItem instanceof Tesseract) {
                        ItemStack item = e.getItem();
                        
                        updateItem(item, block, true);
                    }
               } else if (b.isPresent() && e.getPlayer().isSneaking()) {
                    Block block = b.get();
                    ItemStack item = e.getItem();

                    item.getItemMeta().getPersistentDataContainer().remove(Tesseract.TesseractingObjWorldKey);
                    item.getItemMeta().getPersistentDataContainer().remove(Tesseract.TesseractingObjLocationKey);

                    updateItem(item, block, false);
               }

            }
            
        };
    }

    protected void updateItem(ItemStack item, Block block, boolean isAdding) {
        ItemMeta im = item.getItemMeta();
        Location loc = block.getLocation();

        if (isAdding) {
            if (!im.hasLore()) {
                throw new IllegalArgumentException("This item does not have any lore!");
            }
    
            List<String> lore = im.getLore();
    
            PersistentDataAPI.setString(item.getItemMeta(), Tesseract.TesseractingObjWorldKey, String.valueOf(block.getWorld()));
            PersistentDataAPI.setIntArray(item.getItemMeta(), Tesseract.TesseractingObjLocationKey, new int[] {loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()});
    
            lore.add("Bound To Location: " + loc.getWorld() + " " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
    
            im.setLore(lore);
            item.setItemMeta(im);
        } else {
            if (!im.hasLore()) {
                throw new IllegalArgumentException("This item does not have any lore!");
            }
    
            List<String> lore = im.getLore();
    
            im.getPersistentDataContainer().remove(Tesseract.TesseractingObjWorldKey);
            im.getPersistentDataContainer().remove(Tesseract.TesseractingObjLocationKey);
    
            for (int line = 0; line < lore.size(); line++ ) {
                if (lore.get(line).contains("Bound To Location: ")) {
                    lore.remove(line);
                }
        
            }
    
            im.setLore(lore);
            item.setItemMeta(im);
        }
        
    }


    
}
