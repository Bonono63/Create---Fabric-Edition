package com.mrbonono63.create.foundation.ponder.instructions;

import java.util.function.UnaryOperator;

import com.mrbonono63.create.foundation.ponder.PonderScene;
import com.mrbonono63.create.foundation.ponder.PonderWorld;
import com.mrbonono63.create.foundation.ponder.Selection;
import com.mrbonono63.create.foundation.tileEntity.SyncedTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDataInstruction extends WorldModifyInstruction {

	private boolean redraw;
	private UnaryOperator<CompoundNBT> data;
	private Class<? extends TileEntity> type;

	public TileEntityDataInstruction(Selection selection, Class<? extends TileEntity> type,
		UnaryOperator<CompoundNBT> data, boolean redraw) {
		super(selection);
		this.type = type;
		this.data = data;
		this.redraw = redraw;
	}

	@Override
	protected void runModification(Selection selection, PonderScene scene) {
		PonderWorld world = scene.getWorld();
		selection.forEach(pos -> {
			if (!world.getBounds()
				.isVecInside(pos))
				return;
			TileEntity tileEntity = world.getTileEntity(pos);
			if (!type.isInstance(tileEntity))
				return;
			CompoundNBT apply = data.apply(tileEntity.write(new CompoundNBT()));
			BlockState state = world.getBlockState(pos);
			if (tileEntity instanceof SyncedTileEntity)
				((SyncedTileEntity) tileEntity).readClientUpdate(state, apply);
			tileEntity.fromTag(state, apply);
		});
	}

	@Override
	protected boolean needsRedraw() {
		return redraw;
	}

}
