package com.lyonicdevelopment.manhunt.item;

import com.lyonicdevelopment.manhunt.ManhuntMod;
import com.lyonicdevelopment.manhunt.item.custom.PlayerCompass;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {

    public static void addTrackingCompassProperties(){
        ItemProperties.register(ModItems.PLAYER_COMPASS.get(), new ResourceLocation(ManhuntMod.MOD_ID, "angle"),
                new CompassItemPropertyFunction( (pLevel, pStack, pEntity) -> PlayerCompass.trackedPos));
    }

}
