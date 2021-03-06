package com.mrbonono63.create.content.logistics.block.redstone;

import java.util.List;

import com.mrbonono63.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.mrbonono63.create.foundation.gui.widgets.InterpolatedChasingValue;
import com.mrbonono63.create.foundation.render.backend.instancing.IInstanceRendered;
import com.mrbonono63.create.foundation.tileEntity.SmartTileEntity;
import com.mrbonono63.create.foundation.tileEntity.TileEntityBehaviour;
import com.mrbonono63.create.foundation.utility.Lang;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class AnalogLeverTileEntity extends SmartTileEntity implements IHaveGoggleInformation, IInstanceRendered {

	int state = 0;
	int lastChange;
	InterpolatedChasingValue clientState = new InterpolatedChasingValue().withSpeed(.2f);

	public AnalogLeverTileEntity(TileEntityType<? extends AnalogLeverTileEntity> type) {
		super(type);
	}

	@Override
	public void write(CompoundNBT compound, boolean clientPacket) {
		compound.putInt("State", state);
		compound.putInt("ChangeTimer", lastChange);
		super.write(compound, clientPacket);
	}

	@Override
	protected void fromTag(BlockState blockState, CompoundNBT compound, boolean clientPacket) {
		state = compound.getInt("State");
		lastChange = compound.getInt("ChangeTimer");
		clientState.target(state);
		super.fromTag(blockState, compound, clientPacket);
	}

	@Override
	public void tick() {
		super.tick();
		if (lastChange > 0) {
			lastChange--;
			if (lastChange == 0)
				updateOutput();
		}
		if (world.isRemote)
			clientState.tick();
	}

	@Override
	public void initialize() {
		super.initialize();

	}

	private void updateOutput() {
		AnalogLeverBlock.updateNeighbors(getBlockState(), world, pos);
	}

	@Override
	public void addBehaviours(List<TileEntityBehaviour> behaviours) {
	}

	public void changeState(boolean back) {
		int prevState = state;
		state += back ? -1 : 1;
		state = MathHelper.clamp(state, 0, 15);
		if (prevState != state)
			lastChange = 15;
		sendData();
	}

	@Override
	public boolean addToGoggleTooltip(List<ITextComponent> tooltip, boolean isPlayerSneaking) {
		tooltip.add(componentSpacing.copy().append(Lang.translate("tooltip.analogStrength", this.state)));

		return true;
	}

	public int getState() {
		return state;
	}
}
