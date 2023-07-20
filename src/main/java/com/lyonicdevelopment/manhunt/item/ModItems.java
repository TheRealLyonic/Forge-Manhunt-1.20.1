package com.lyonicdevelopment.manhunt.item;

import com.lyonicdevelopment.manhunt.ManhuntMod;
import com.lyonicdevelopment.manhunt.item.custom.TrackingCompass;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ManhuntMod.MOD_ID);

    public static final RegistryObject<Item> TRACKING_COMPASS = ITEMS.register("tracking_compass",
            () -> new TrackingCompass(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
