package com.mrbonono63.create.content.schematics.block;

import static com.mrbonono63.create.foundation.gui.AllGuiTextures.SCHEMATIC_TABLE;
import static com.mrbonono63.create.foundation.gui.AllGuiTextures.SCHEMATIC_TABLE_PROGRESS;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.CreateClient;
import com.mrbonono63.create.content.schematics.ClientSchematicLoader;
import com.mrbonono63.create.foundation.gui.AbstractSimiContainerScreen;
import com.mrbonono63.create.foundation.gui.AllGuiTextures;
import com.mrbonono63.create.foundation.gui.AllIcons;
import com.mrbonono63.create.foundation.gui.GuiGameElement;
import com.mrbonono63.create.foundation.gui.widgets.IconButton;
import com.mrbonono63.create.foundation.gui.widgets.Label;
import com.mrbonono63.create.foundation.gui.widgets.ScrollInput;
import com.mrbonono63.create.foundation.gui.widgets.SelectionScrollInput;
import com.mrbonono63.create.foundation.utility.Lang;

import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class SchematicTableScreen extends AbstractSimiContainerScreen<SchematicTableContainer>
	implements IHasContainer<SchematicTableContainer> {

	private ScrollInput schematicsArea;
	private IconButton confirmButton;
	private IconButton folderButton;
	private IconButton refreshButton;
	private Label schematicsLabel;
	private List<Rectangle2d> extraAreas;

	private final ITextComponent title = Lang.translate("gui.schematicTable.title");
	private final ITextComponent uploading = Lang.translate("gui.schematicTable.uploading");
	private final ITextComponent finished = Lang.translate("gui.schematicTable.finished");
	private final ITextComponent refresh = Lang.translate("gui.schematicTable.refresh");
	private final ITextComponent folder = Lang.translate("gui.schematicTable.open_folder");
	private final ITextComponent noSchematics = Lang.translate("gui.schematicTable.noSchematics");
	private final ITextComponent availableSchematicsTitle = Lang.translate("gui.schematicTable.availableSchematics");
	private final ItemStack renderedItem = AllBlocks.SCHEMATIC_TABLE.asStack();

	private float progress;
	private float chasingProgress;
	private float lastChasingProgress;

	public SchematicTableScreen(SchematicTableContainer container, PlayerInventory playerInventory,
		ITextComponent title) {
		super(container, playerInventory, title);
	}

	@Override
	protected void init() {
		setWindowSize(SCHEMATIC_TABLE.width, SCHEMATIC_TABLE.height + 50);
		super.init();
		widgets.clear();

		int mainLeft = guiLeft - 56;
		int mainTop = guiTop - 16;

		CreateClient.schematicSender.refresh();
		List<ITextComponent> availableSchematics = CreateClient.schematicSender.getAvailableSchematics();

		schematicsLabel = new Label(mainLeft + 49, mainTop + 26, StringTextComponent.EMPTY).withShadow();
		schematicsLabel.text = StringTextComponent.EMPTY;
		if (!availableSchematics.isEmpty()) {
			schematicsArea =
				new SelectionScrollInput(mainLeft + 45, mainTop + 21, 139, 18).forOptions(availableSchematics)
					.titled(availableSchematicsTitle.copy())
					.writingTo(schematicsLabel);
			widgets.add(schematicsArea);
			widgets.add(schematicsLabel);
		}

		confirmButton = new IconButton(mainLeft + 44, mainTop + 56, AllIcons.I_CONFIRM);

		folderButton = new IconButton(mainLeft + 21, mainTop + 21, AllIcons.I_OPEN_FOLDER);
		folderButton.setToolTip(folder);
		refreshButton = new IconButton(mainLeft + 207, mainTop + 21, AllIcons.I_REFRESH);
		refreshButton.setToolTip(refresh);

		widgets.add(confirmButton);
		widgets.add(folderButton);
		widgets.add(refreshButton);

		extraAreas = new ArrayList<>();
		extraAreas.add(new Rectangle2d(mainLeft, mainTop, SCHEMATIC_TABLE.width, SCHEMATIC_TABLE.height));
	}

	@Override
	protected void renderWindow(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

		int x = guiLeft + 20;
		int y = guiTop;

		int mainLeft = guiLeft - 56;
		int mainTop = guiTop - 16;

		AllGuiTextures.PLAYER_INVENTORY.draw(matrixStack, this, x - 16, y + 70 + 14);
		textRenderer.draw(matrixStack, playerInventory.getDisplayName(), x - 15 + 7, y + 64 + 26, 0x666666);

		SCHEMATIC_TABLE.draw(matrixStack, this, mainLeft, mainTop);

		if (container.getTileEntity().isUploading)
			textRenderer.drawWithShadow(matrixStack, uploading, mainLeft + 11, mainTop + 3, 0xffffff);
		else if (container.getSlot(1)
			.getHasStack())
			textRenderer.drawWithShadow(matrixStack, finished, mainLeft + 11, mainTop + 3, 0xffffff);
		else
			textRenderer.drawWithShadow(matrixStack, title, mainLeft + 11, mainTop + 3, 0xffffff);
		if (schematicsArea == null)
			textRenderer.drawWithShadow(matrixStack, noSchematics, mainLeft + 54, mainTop + 26, 0xd3d3d3);

		GuiGameElement.of(renderedItem)
			.at(mainLeft + 217, mainTop + 50, -150)
			.scale(3)
			.render(matrixStack);

		client.getTextureManager()
			.bindTexture(SCHEMATIC_TABLE_PROGRESS.location);
		int width = (int) (SCHEMATIC_TABLE_PROGRESS.width
			* MathHelper.lerp(partialTicks, lastChasingProgress, chasingProgress));
		int height = SCHEMATIC_TABLE_PROGRESS.height;
		RenderSystem.disableLighting();
		drawTexture(matrixStack, mainLeft + 70, mainTop + 57, SCHEMATIC_TABLE_PROGRESS.startX,
			SCHEMATIC_TABLE_PROGRESS.startY, width, height);
	}

	@Override
	public void tick() {
		super.tick();
		boolean finished = container.getSlot(1)
			.getHasStack();

		if (container.getTileEntity().isUploading || finished) {
			if (finished) {
				chasingProgress = lastChasingProgress = progress = 1;
			} else {
				lastChasingProgress = chasingProgress;
				progress = container.getTileEntity().uploadingProgress;
				chasingProgress += (progress - chasingProgress) * .5f;
			}
			confirmButton.active = false;

			if (schematicsLabel != null) {
				schematicsLabel.colored(0xCCDDFF);
				String uploadingSchematic = container.getTileEntity().uploadingSchematic;
				schematicsLabel.text = uploadingSchematic == null ? null : new StringTextComponent(uploadingSchematic);
			}
			if (schematicsArea != null)
				schematicsArea.visible = false;

		} else {
			progress = 0;
			chasingProgress = lastChasingProgress = 0;
			confirmButton.active = true;

			if (schematicsLabel != null)
				schematicsLabel.colored(0xFFFFFF);
			if (schematicsArea != null) {
				schematicsArea.writingTo(schematicsLabel);
				schematicsArea.visible = true;
			}
		}
	}

	@Override
	public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
		ClientSchematicLoader schematicSender = CreateClient.schematicSender;

		if (confirmButton.active && confirmButton.isHovered() && ((SchematicTableContainer) container).canWrite()
			&& schematicsArea != null) {

			lastChasingProgress = chasingProgress = progress = 0;
			List<ITextComponent> availableSchematics = schematicSender.getAvailableSchematics();
			ITextComponent schematic = availableSchematics.get(schematicsArea.getState());
			schematicSender.startNewUpload(schematic.getUnformattedComponentText());
		}

		if (folderButton.isHovered()) {
			Util.getOSType()
				.openFile(Paths.get("schematics/")
					.toFile());
		}

		if (refreshButton.isHovered()) {
			schematicSender.refresh();
			List<ITextComponent> availableSchematics = schematicSender.getAvailableSchematics();
			widgets.remove(schematicsArea);

			if (!availableSchematics.isEmpty()) {
				schematicsArea = new SelectionScrollInput(guiLeft - 56 + 33, guiTop - 16 + 23, 134, 14)
					.forOptions(availableSchematics)
					.titled(availableSchematicsTitle.copy())
					.writingTo(schematicsLabel);
				schematicsArea.onChanged();
				widgets.add(schematicsArea);
			} else {
				schematicsArea = null;
				schematicsLabel.text = StringTextComponent.EMPTY;
			}
		}

		return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
	}

	@Override
	public List<Rectangle2d> getExtraAreas() {
		return extraAreas;
	}
}
