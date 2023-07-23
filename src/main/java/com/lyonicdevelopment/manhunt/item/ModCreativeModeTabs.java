package com.lyonicdevelopment.manhunt.item;

import com.lyonicdevelopment.manhunt.ManhuntMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ManhuntMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MANHUNT_TAB = CREATIVE_MODE_TABS.register("manhunt_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PLAYER_COMPASS.get())).title(Component.
                    translatable("creativetab.manhunt_tab")).displayItems((displayParameters, event) -> {
                        event.accept(ModItems.PLAYER_COMPASS.get());
            }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
