package com.yeetdot.memoria.screen;

import com.yeetdot.memoria.Memoria;
import com.yeetdot.memoria.screen.custom.MnemonicInfuserScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<MnemonicInfuserScreenHandler> MNEMONIC_INFUSER_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Memoria.MOD_ID, "mnemonic_infuser_screen_handler"), new ExtendedScreenHandlerType<>(MnemonicInfuserScreenHandler::new, BlockPos.PACKET_CODEC));

    public static void registerScreenHandlers() {}
}
