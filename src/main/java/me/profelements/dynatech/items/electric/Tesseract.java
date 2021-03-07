package me.profelements.dynatech.items.electric;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import me.profelements.dynatech.DynaTech;
import me.profelements.dynatech.items.electric.abstracts.AMachine;
import me.profelements.dynatech.items.tools.TesseractingObject;


public class Tesseract extends AMachine {

    private static final int[] BORDER_IN = new int[] {9,10,11,12};
    private static final int[] BORDER_OUT = new int[] {14,15,16,17};
    private static final int[] BORDER = new int[] {0,2,3,5,6,8,13,22,30,39,48};
    private static final int[] INPUT_SLOTS = new int[] {18,19,20,21,26,27,28,29,35,36,37,38,44,45,46};
    private static final int[] OUTPUT_SLOTS = new int[] {22,23,24,25,31,32,33,34,40,41,42,43,48,49,50};
    private static final int[] PROGRESS_BAR_SLOT = new int[] {7};
    private static final int[] TESSERACTING_OBJ_SLOT = new int[] {1};
    private static final int[] POWER_INFO_SLOT = new int[] {4};

    public static final NamespacedKey TesseractingObjWorldKey = new NamespacedKey(DynaTech.getInstance(), "world-key");
    public static final NamespacedKey TesseractingObjLocationKey = new NamespacedKey(DynaTech.getInstance(), "location-key");

    public Tesseract(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    public Tesseract(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, ItemStack recipeOutput) {
        super(category, item, recipeType, recipe, recipeOutput);
    }


    private static final void connectTesseractingObject(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);
        ItemStack item = inv.getItemInSlot(TESSERACTING_OBJ_SLOT[0]);
        SlimefunItem sfItem = SlimefunItem.getByItem(item);

        if ( item != null && sfItem != null && sfItem instanceof TesseractingObject) {
            if (PersistentDataAPI.hasString(item.getItemMeta(), TesseractingObjWorldKey) && PersistentDataAPI.hasIntArray(item.getItemMeta(), TesseractingObjLocationKey)) {
                String world = PersistentDataAPI.getString(item.getItemMeta(), TesseractingObjWorldKey);
                int[] location = PersistentDataAPI.getIntArray(item.getItemMeta(), TesseractingObjLocationKey);

                BlockStorage.addBlockInfo(b, "world-key", world);
                BlockStorage.addBlockInfo(b, "location-key", String.valueOf(location));
            }
        }
    }

    @Override
    public String getMachineIdentifier() {
        return "TESSERACT";
    }

   

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.ENDER_PEARL);
    }

    @Override
    public List<int[]> getBorders() {
        final List<int[]> borders = new ArrayList<>();
        
        borders.add(BORDER);
        borders.add(BORDER_IN);
        borders.add(BORDER_OUT);
        
        return borders;
    }

    @Override
    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    public int getProgressBarSlot() {
        return PROGRESS_BAR_SLOT[0];
    }
    
}
