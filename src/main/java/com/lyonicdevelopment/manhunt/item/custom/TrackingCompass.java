package com.lyonicdevelopment.manhunt.item.custom;

import com.lyonicdevelopment.manhunt.ManhuntMod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class TrackingCompass extends CompassItem {

    private Collection<PlayerInfo> playerInfo;
    public static PlayerInfo selectedPlayerInfo;

    public TrackingCompass(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if(pLevel.isClientSide()) {
            playerInfo = Minecraft.getInstance().getConnection().getListedOnlinePlayers();
            selectedPlayerInfo = playerInfo.iterator().next();


            if(selectedPlayerInfo == null || pPlayer.getScoreboardName().equals(selectedPlayerInfo.getProfile().getName())){
                if (playerInfo.iterator().hasNext()) {
                    selectedPlayerInfo = playerInfo.iterator().next();
                } else {
                    playerInfo = Minecraft.getInstance().getConnection().getListedOnlinePlayers();
                    selectedPlayerInfo = playerInfo.iterator().next();

                    if (selectedPlayerInfo == null) {
                        pPlayer.sendSystemMessage(Component.literal("No other players exist on the server.").withStyle(ChatFormatting.DARK_RED));
                        return super.use(pLevel, pPlayer, pUsedHand);
                    }
                }
            }

            if (pPlayer.getScoreboardName().equals(selectedPlayerInfo.getProfile().getName())) {
                pPlayer.sendSystemMessage(Component.literal("No other players exist on the server.").withStyle(ChatFormatting.DARK_RED));
                return super.use(pLevel, pPlayer, pUsedHand);
            }

            pPlayer.sendSystemMessage(Component.literal("Now tracking " + selectedPlayerInfo.getProfile().getName()));
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if(Screen.hasShiftDown()){
            pTooltipComponents.add(Component.literal("A special compass meant for tracking players.").withStyle(ChatFormatting.AQUA));
        }else{
            pTooltipComponents.add(Component.literal("Hold SHIFT for more info").withStyle(ChatFormatting.YELLOW));
        }

    }

}
