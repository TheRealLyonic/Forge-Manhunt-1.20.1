package com.lyonicdevelopment.manhunt.item.custom;

import com.lyonicdevelopment.manhunt.ManhuntMod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
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

import java.util.ArrayList;
import java.util.List;

public class TrackingCompass extends CompassItem {

    private ArrayList<PlayerInfo> playerInfoList;
    public static PlayerInfo selectedPlayerInfo = null;
    public static GlobalPos trackedPos;

    public TrackingCompass(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if(pLevel.isClientSide()){

            if(playerInfoList == null){
                refreshPlayerInfoList();
            }

            if(selectedPlayerInfo != null){
                for(int i = playerInfoList.indexOf(selectedPlayerInfo); i < playerInfoList.size(); i++){
                    if(!playerInfoList.get(i).getProfile().getName().equals(pPlayer.getScoreboardName()) && playerInfoList.get(i) != null){
                        selectedPlayerInfo = playerInfoList.get(i);
                        pPlayer.sendSystemMessage(Component.literal("Now tracking " + playerInfoList.get(i).getProfile().getName()).withStyle(ChatFormatting.AQUA));
                        return super.use(pLevel, pPlayer, pUsedHand);
                    }
                }
            }else{
                for(PlayerInfo playerInfo : playerInfoList){
                    if(!playerInfo.getProfile().getName().equals(pPlayer.getScoreboardName()) && playerInfo != null){
                        selectedPlayerInfo = playerInfo;
                        pPlayer.sendSystemMessage(Component.literal("Now tracking " + playerInfo.getProfile().getName()).withStyle(ChatFormatting.AQUA));
                        return super.use(pLevel, pPlayer, pUsedHand);
                    }
                }
            }



            pPlayer.sendSystemMessage(Component.literal("No trackable players found.").withStyle(ChatFormatting.DARK_RED));
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {

        if(pLevel.isClientSide()){

            if(selectedPlayerInfo != null){
                trackedPos = GlobalPos.of(pLevel.dimension(), new BlockPos(
                        (int) pLevel.getPlayerByUUID(selectedPlayerInfo.getProfile().getId()).getX(),
                        (int) pLevel.getPlayerByUUID(selectedPlayerInfo.getProfile().getId()).getY(),
                        (int) pLevel.getPlayerByUUID(selectedPlayerInfo.getProfile().getId()).getZ()
                ));
            }else{
                trackedPos = GlobalPos.of(pLevel.dimension(), new BlockPos(0, 0, 0));
            }

        }

    }

    private void refreshPlayerInfoList(){
        playerInfoList = new ArrayList<>(Minecraft.getInstance().getConnection().getOnlinePlayers());

        for(int i = 0; i < playerInfoList.size(); i++){
            ManhuntMod.LOGGER.debug("PLAYER AT [" + i + "]: " + playerInfoList.get(i).getProfile().getName());
        }

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
