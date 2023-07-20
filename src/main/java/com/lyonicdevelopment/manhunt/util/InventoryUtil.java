package com.lyonicdevelopment.manhunt.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class InventoryUtil {

    public static boolean playerHasStackInInventory(Player player, Item item){

        for(int i = 0; i < player.getInventory().getContainerSize(); i++){
            ItemStack currentStack = player.getInventory().getItem(i);

            if(!currentStack.isEmpty() && currentStack.is(item)){
                return true;
            }
        }

        return false;
    }

    public static int getFirstInventoryIndex(Player player, Item item){

        for(int i = 0; i < player.getInventory().getContainerSize(); i++){
            ItemStack currentStack = player.getInventory().getItem(i);

            if(!currentStack.isEmpty() && currentStack.is(item)){
                return i;
            }

        }

        return -1;
    }

    public static int getNextInventoryIndex(Player player, Item item, int startingIndex){

        for(int i = startingIndex; i < player.getInventory().getContainerSize(); i++){
            ItemStack currentStack = player.getInventory().getItem(i);

            if(!currentStack.isEmpty() && currentStack.is(item)){
                return i;
            }

        }

        return -1;
    }

}
