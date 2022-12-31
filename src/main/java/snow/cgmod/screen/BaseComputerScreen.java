package snow.cgmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import snow.cgmod.CountriesGameMod;

public class BaseComputerScreen extends AbstractContainerScreen<BaseComputerMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CountriesGameMod.MODID,"textures/gui/base_computer_gui.png");

    public BaseComputerScreen(BaseComputerMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack pose, int x, int y) {
        super.renderLabels(pose, x, y);
        int[] labelData = menu.getDisplayData();
        if (labelData == null) {
            labelData = new int[5];
        }
        this.font.draw(pose, "" + labelData[0], 68, 20, 0x404040);
        this.font.draw(pose, "" + labelData[1], 68, 41, 0x404040);
        this.font.draw(pose, "" + labelData[2], 68, 62, 0x404040);
        this.font.draw(pose, "" + labelData[3], 114, 20, 0x404040);
        this.font.draw(pose, "" + labelData[4], 114, 41, 0x404040);

    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }
}
