package com.yeetdot.memoria.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yeetdot.memoria.Memoria;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MnemonicInfuserScreen extends HandledScreen<MnemonicInfuserScreenHandler> {
    public static final Identifier GUI_TEXTURE = Identifier.of(Memoria.MOD_ID, "textures/gui/mnemonic_infuser/mnemonic_infuser.png");

    public MnemonicInfuserScreen(MnemonicInfuserScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        titleY = -22;
        playerInventoryTitleY = 106;
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - 220) / 2;

        context.drawTexture(RenderLayer::getGuiTextured, GUI_TEXTURE, x, y, 0f, 0f, backgroundWidth, 220, 256, 256);
    }
}
