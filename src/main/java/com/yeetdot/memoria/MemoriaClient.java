package com.yeetdot.memoria;

import com.yeetdot.memoria.block.entity.ModBlockEntities;
import com.yeetdot.memoria.block.entity.render.MnemonicInfuserBlockEntityRender;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class MemoriaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.PEDESTAL_BLOCK_ENTITY, MnemonicInfuserBlockEntityRender::new);
        BlockEntityRendererFactories.register(ModBlockEntities.PEDESTAL_BLOCK_ENTITY, MnemonicInfuserBlockEntityRender::new);
    }
}
