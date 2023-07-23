package com.lyonicdevelopment.manhunt.item.custom;

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

public class PlayerCompass extends CompassItem {

    private ArrayList<Player> players = new ArrayList<>();
    private BlockPos lastKnownPos;
    private Player trackedPlayer;
    public static GlobalPos trackedPos;

    public PlayerCompass(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        //Cycle through listed players. Listed players should not include player holding the compass.

        if(pLevel.isClientSide()){

            if(players.size() == 0){
                pPlayer.sendSystemMessage(Component.literal("No trackable players found.").withStyle(ChatFormatting.RED));
            }else{

                if(trackedPlayer != null){
                    if(players.size() == (players.indexOf(trackedPlayer) + 1)){
                        trackedPlayer = players.get(0);
                    }else{
                        trackedPlayer = players.get(players.indexOf(trackedPlayer) + 1);
                    }
                }else{
                    trackedPlayer = players.get(0);
                }

                try{
                    pPlayer.sendSystemMessage(Component.literal("Now tracking " + trackedPlayer.getScoreboardName()).withStyle(ChatFormatting.AQUA));
                }catch(NullPointerException e){
                    pPlayer.sendSystemMessage(Component.literal("The player you're tracking has entered a different dimension.").withStyle(ChatFormatting.LIGHT_PURPLE));
                }
            }

        }


        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        if (pLevel.isClientSide() && pEntity instanceof Player player) {

            if(players != null && players.size() > 0){
                players.clear();
            }

            for (PlayerInfo playerInfo : Minecraft.getInstance().getConnection().getListedOnlinePlayers()) {
                if (!playerInfo.getProfile().getName().equals(pEntity.getScoreboardName())) {
                    players.add(player.level().getPlayerByUUID(playerInfo.getProfile().getId()));
                }
            }

            if(trackedPlayer == null){

                if(lastKnownPos != null){
                    trackedPos = GlobalPos.of(player.level().dimension(), lastKnownPos);
                }else{
                    trackedPos = GlobalPos.of(player.level().dimension(), new BlockPos(0, 0, 0));
                }

            }else if(trackedPlayer.level().dimension().equals(player.level().dimension())){

                if(trackedPlayer.isDeadOrDying()){
                    trackedPlayer = null;
                    return;
                }

                lastKnownPos = trackedPlayer.blockPosition();
                trackedPos = GlobalPos.of(trackedPlayer.level().dimension(), lastKnownPos);
            }else{
                trackedPos = GlobalPos.of(player.level().dimension(), lastKnownPos);
            }

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
