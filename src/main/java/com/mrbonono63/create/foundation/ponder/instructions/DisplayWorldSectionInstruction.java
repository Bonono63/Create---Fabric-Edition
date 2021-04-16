package com.mrbonono63.create.foundation.ponder.instructions;

import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.mrbonono63.create.content.contraptions.components.structureMovement.glue.SuperGlueItem;
import com.mrbonono63.create.foundation.ponder.PonderScene;
import com.mrbonono63.create.foundation.ponder.Selection;
import com.mrbonono63.create.foundation.ponder.elements.WorldSectionElement;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class DisplayWorldSectionInstruction extends FadeIntoSceneInstruction<WorldSectionElement> {

	private Selection initialSelection;
	private Optional<Supplier<WorldSectionElement>> mergeOnto;
	private BlockPos glue;

	public DisplayWorldSectionInstruction(int fadeInTicks, Direction fadeInFrom, Selection selection,
		Optional<Supplier<WorldSectionElement>> mergeOnto) {
		this(fadeInTicks, fadeInFrom, selection, mergeOnto, null);
	}

	public DisplayWorldSectionInstruction(int fadeInTicks, Direction fadeInFrom, Selection selection,
		Optional<Supplier<WorldSectionElement>> mergeOnto, @Nullable BlockPos glue) {
		super(fadeInTicks, fadeInFrom, new WorldSectionElement(selection));
		initialSelection = selection;
		this.mergeOnto = mergeOnto;
		this.glue = glue;
	}

	@Override
	protected void firstTick(PonderScene scene) {
		super.firstTick(scene);
		mergeOnto.ifPresent(wse -> element.setAnimatedOffset(wse.get()
			.getAnimatedOffset(), true));
		element.set(initialSelection);
		element.setVisible(true);
	}

	@Override
	public void tick(PonderScene scene) {
		super.tick(scene);
		if (remainingTicks > 0)
			return;
		mergeOnto.ifPresent(c -> element.mergeOnto(c.get()));
		if (glue != null)
			SuperGlueItem.spawnParticles(scene.getWorld(), glue, fadeInFrom, true);
	}

	@Override
	protected Class<WorldSectionElement> getElementClass() {
		return WorldSectionElement.class;
	}

}
