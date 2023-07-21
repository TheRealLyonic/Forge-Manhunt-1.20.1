package com.lyonicdevelopment.manhunt.sound;

import com.lyonicdevelopment.manhunt.ManhuntMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ManhuntMod.MOD_ID);

    public static final RegistryObject<SoundEvent> MANHUNT_VICTORY = SOUND_EVENTS.register("manhunt_victory",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ManhuntMod.MOD_ID, "manhunt_victory")));

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }

}
