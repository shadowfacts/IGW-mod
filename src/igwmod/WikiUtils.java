package igwmod;

import igwmod.lib.IGWLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WikiUtils{
    private static HashMap<String, ItemStack> unlocMap;

    public static ItemStack getStackFromName(String name){
        if(unlocMap == null) {
            unlocMap = new HashMap<>();
            NonNullList<ItemStack> stackList = NonNullList.create();

            Iterator iterator = Item.REGISTRY.iterator();
            while(iterator.hasNext()) {
                Item item = (Item)iterator.next();

                if(item != null && item.getCreativeTab() != null) {
                    item.getSubItems(item, null, stackList);
                }
            }

            for(ItemStack stack : stackList) {
                if(stack.getItem() == null) continue;
                String itemName = stack.getUnlocalizedName().replace("tile.", "block/").replace("item.", "item/");//TODO improve
                unlocMap.put(itemName, stack);
                unlocMap.put(getOwningModId(stack) + ":" + itemName, stack);
            }
        }
        String[] splitName = name.contains("#") ? name.split("#") : new String[]{name};
        ItemStack stack = unlocMap.get(splitName[0]);
        if(stack != null) {
            stack = stack.copy();
            if(splitName.length > 1) stack.setCount(Integer.parseInt(splitName[1]));
            return stack;
        } else {
            return null;
        }
    }

    public static String getNameFromStack(ItemStack stack){
        return stack.getUnlocalizedName().replace("tile.", "block/").replace("item.", "item/");
    }

    public static String getOwningModId(ItemStack stack){
        String modid = "minecraft";
        if(stack.isEmpty()) {
            IGWLog.warning("Found an empty ItemStack! This isn't supposed to happen!");
        } else {
        	ResourceLocation id = Item.REGISTRY.getNameForObject(stack.getItem());
            if(id != null) modid = id.getResourceDomain().toLowerCase();
        }
        return modid;
    }
}
