package snow.cgmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import snow.cgmod.CountriesGameMod;


public class WorkComputerScreen extends AbstractContainerScreen<WorkComputerMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CountriesGameMod.MODID,"textures/gui/work_computer_gui");

    public WorkComputerScreen(WorkComputerMenu menu, Inventory inv, Component component) {
        super(menu, inv, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        int job = 0;
        boolean direction;
        if (x > 42) {
            if (x < 60) {
                job = 1;
            } else if (x < 78) {
                job = 2;
            } else if (x < 96) {
                job = 3;
            } else if (x < 114) {
                job = 4;
            } else if (x < 132) {
                job = 5;
            }
        }
        if (y < 29) {
            direction = true;
        } else if (y > 40) {
            direction = false;
        } else {
            return false;
        }
        if (job == 0) {
            return false;
        } else {
            menu.blockEntity.changeByIndex(job, direction);
        }
        return super.mouseClicked(x, y, button);
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
            labelData = new int[6];
        }
    }


    @Override
    protected void containerTick() {
        super.containerTick();
    }

}
