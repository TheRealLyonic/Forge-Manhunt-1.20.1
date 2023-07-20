package com.lyonicdevelopment.manhunt.item;

import com.lyonicdevelopment.manhunt.ManhuntMod;
import com.lyonicdevelopment.manhunt.item.custom.TrackingCompass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.WorldDimensions;

import java.util.Collection;

public class ModItemProperties {

    public static void addTrackingCompassProperties(){
        ItemProperties.register(ModItems.TRACKING_COMPASS.get(), new ResourceLocation(ManhuntMod.MOD_ID, "angle"),
                new CompassItemPropertyFunction( (pLevel, pStack, pEntity) -> {

                    if(pEntity instanceof Player currentPlayer){
                        Player trackedPlayer;

                        try{
                            trackedPlayer = pLevel.getPlayerByUUID(TrackingCompass.selectedPlayerInfo.getProfile().getId());
                        }catch(NullPointerException e){
                            trackedPlayer = null;
                        }


                        if(trackedPlayer == null){
                            return GlobalPos.of(pLevel.dimension(), new BlockPos(0, 0, 0));
                        }else{
                            return GlobalPos.of(trackedPlayer.level().dimension(),
                                    new BlockPos( (int) trackedPlayer.position().x, (int) trackedPlayer.position().y, (int) trackedPlayer.position().z));
                        }

                    }else{
                        return GlobalPos.of(pLevel.dimension(), new BlockPos(0, 0, 0));
                    }

                } ));
    }

}
