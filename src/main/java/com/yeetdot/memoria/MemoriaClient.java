package com.yeetdot.memoria;

import com.yeetdot.memoria.block.entity.ModBlockEntities;
import com.yeetdot.memoria.block.entity.render.MnemonicInfuserBlockEntityRender;
import com.yeetdot.memoria.block.entity.render.MnemonicPedestalBlockEntityRender;
import com.yeetdot.memoria.screen.ModScreenHandlers;
import com.yeetdot.memoria.screen.custom.MnemonicInfuserScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class MemoriaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.MNEMONIC_INFUSER_BLOCK_ENTITY, MnemonicInfuserBlockEntityRender::new);
        BlockEntityRendererFactories.register(ModBlockEntities.PEDESTAL_BLOCK_ENTITY, MnemonicPedestalBlockEntityRender::new);

        HandledScreens.register(ModScreenHandlers.MNEMONIC_INFUSER_SCREEN_HANDLER, MnemonicInfuserScreen::new);
    }
}
