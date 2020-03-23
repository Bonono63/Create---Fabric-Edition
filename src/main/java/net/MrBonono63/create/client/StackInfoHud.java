package net.MrBonono63.create.client;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class StackInfoHud {/*
    public static List<StackInfoElement> ELEMENTS = new ArrayList<>();
    private static MinecraftClient mc = MinecraftClient.getInstance();
    private int x = 2;
    private int y = 7;

        public void drawStackInfoHud(Window res) {
            PlayerEntity player = mc.player;
            if (player == null){
                return;
            }
            List<ItemStack> stacks = new ArrayList<>();
            stacks.add(player.getMainHandStack());
            stacks.add(player.getOffHandStack());

            for (ItemStack stack : player.getArmorItems()) {
                stacks.add(stack);
            }
            x = RebornCoreConfig.stackInfoX;

            if (RebornCoreConfig.stackInfoCorner == 2 || RebornCoreConfig.stackInfoCorner == 3) {
                stacks = Lists.reverse(stacks);
                // 20 for line height and additionally padding from configuration file
                y = res.getScaledHeight() - 20 - RebornCoreConfig.stackInfoY;
            } else {
                y = RebornCoreConfig.stackInfoY;
            }

            for (ItemStack stack : stacks) {
                addInfo(stack, res);
            }
        }

        private void addInfo(ItemStack stack, Window res) {
            if (stack == ItemStack.EMPTY) {
                return;
            }

            String text;
            if (stack.getItem() instanceof EnergyHolder) {

                double maxCharge = Energy.of(stack).getMaxStored();
                double currentCharge = Energy.of(stack).getEnergy();

                Formatting color = StringUtils.getPercentageColour(percentage(maxCharge, currentCharge));
                text = color + PowerSystem.getLocaliszedPowerFormattedNoSuffix(currentCharge)
                        + "/" + PowerSystem.getLocaliszedPowerFormattedNoSuffix(maxCharge) + " "
                        + PowerSystem.getDisplayPower().abbreviation + Formatting.GRAY;
                if (stack.getTag() != null && stack.getTag().contains("isActive")) {
                    if (stack.getTag().getBoolean("isActive")) {
                        text = text + Formatting.GOLD + " (" + StringUtils.t("reborncore.message.active")
                                + Formatting.GOLD + ")" + Formatting.GRAY;
                    } else {
                        text = text + Formatting.GOLD + " (" + StringUtils.t("reborncore.message.inactive")
                                + Formatting.GOLD + ")" + Formatting.GRAY;
                    }
                }

                if (RebornCoreConfig.stackInfoCorner == 1 || RebornCoreConfig.stackInfoCorner == 2) {
                    int strWidth = mc.textRenderer.getStringWidth(text);
                    // 18 for item icon and additionally padding from configuration file
                    x = res.getScaledWidth() - strWidth - 18 - RebornCoreConfig.stackInfoX;
                }

                renderStackForInfo(stack);
                mc.textRenderer.drawWithShadow(text, x + 18, y, 0);

                if (RebornCoreConfig.stackInfoCorner == 0 || RebornCoreConfig.stackInfoCorner == 1) {
                    y += 20;
                } else {
                    y -= 20;
                }
            }

            for (StackInfoElement element : ELEMENTS) {
                if (!element.getText(stack).equals("")) {
                    renderStackForInfo(stack);
                    mc.textRenderer.drawWithShadow(element.getText(stack), x + 18, y, 0);
                    y += 20;
                }
            }
        }

        private void renderStackForInfo(ItemStack stack) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(32826);
            DiffuseLighting.enable();
            if (stack.isEmpty()) {
                return;
            }
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            itemRenderer.renderGuiItem(stack, x, y - 5);

            GL11.glDisable(GL11.GL_LIGHTING);
        }


        private int percentage(double MaxValue, double CurrentValue) {
            if (CurrentValue == 0)
                return 0;
            return (int) ((CurrentValue * 100.0f) / MaxValue);
        }
    @Override
    public void onHudRender(float v) {


        public static void registerElement(StackInfoElement element) {
            ELEMENTS.add(element);
        }
    }*/
}
