package com.lyonicdevelopment.manhunt.event;

import com.lyonicdevelopment.manhunt.ManhuntMod;
import com.lyonicdevelopment.manhunt.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ManhuntMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void entityKilled(LivingDeathEvent event){
        if(event.getEntity() instanceof EnderDragon){
            Entity damageSource = event.getSource().getEntity();

            event.getSource().getEntity().level().playSeededSound(null, damageSource.getX(), damageSource.getY(), damageSource.getZ(),
                    ModSounds.MANHUNT_VICTORY.get(), SoundSource.AMBIENT, 1f, 1f, 0);
        }
    }

}
