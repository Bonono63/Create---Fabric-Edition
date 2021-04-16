package com.mrbonono63.create.content.contraptions.components.press;

import static com.mrbonono63.create.foundation.tileEntity.behaviour.belt.BeltProcessingBehaviour.ProcessingResult.HOLD;
import static com.mrbonono63.create.foundation.tileEntity.behaviour.belt.BeltProcessingBehaviour.ProcessingResult.PASS;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.simibubi.create.Create;
import com.mrbonono63.create.content.contraptions.components.press.MechanicalPressTileEntity.Mode;
import com.mrbonono63.create.content.contraptions.relays.belt.BeltHelper;
import com.mrbonono63.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.mrbonono63.create.content.logistics.InWorldProcessing;
import com.mrbonono63.create.foundation.tileEntity.behaviour.belt.BeltProcessingBehaviour.ProcessingResult;
import com.mrbonono63.create.foundation.tileEntity.behaviour.belt.TransportedItemStackHandlerBehaviour;
import com.mrbonono63.create.foundation.tileEntity.behaviour.belt.TransportedItemStackHandlerBehaviour.TransportedResult;

import net.minecraftforge.items.ItemHandlerHelper;

public class BeltPressingCallbacks {

	static ProcessingResult onItemReceived(TransportedItemStack transported,
		TransportedItemStackHandlerBehaviour handler, MechanicalPressTileEntity press) {
		if (press.getSpeed() == 0)
			return PASS;
		if (press.running)
			return HOLD;
		if (!press.getRecipe(transported.stack)
			.isPresent())
			return PASS;

		press.start(Mode.BELT);
		return HOLD;
	}

	static ProcessingResult whenItemHeld(TransportedItemStack transported, TransportedItemStackHandlerBehaviour handler,
		MechanicalPressTileEntity pressTe) {

		if (pressTe.getSpeed() == 0)
			return PASS;
		if (!pressTe.running)
			return PASS;
		if (pressTe.runningTicks != MechanicalPressTileEntity.CYCLE / 2)
			return HOLD;

		Optional<PressingRecipe> recipe = pressTe.getRecipe(transported.stack);
		pressTe.pressedItems.clear();
		pressTe.pressedItems.add(transported.stack);

		if (!recipe.isPresent())
			return PASS;

		boolean bulk = MechanicalPressTileEntity.canProcessInBulk() || transported.stack.getCount() == 1;

		List<TransportedItemStack> collect = InWorldProcessing
			.applyRecipeOn(bulk ? transported.stack : ItemHandlerHelper.copyStackWithSize(transported.stack, 1),
				recipe.get())
			.stream()
			.map(stack -> {
				TransportedItemStack copy = transported.copy();
				boolean centered = BeltHelper.isItemUpright(stack);
				copy.stack = stack;
				copy.locked = true;
				copy.angle = centered ? 180 : Create.random.nextInt(360);
				return copy;
			})
			.collect(Collectors.toList());

		if (bulk) {
			if (collect.isEmpty())
				handler.handleProcessingOnItem(transported, TransportedResult.removeItem());
			else
				handler.handleProcessingOnItem(transported, TransportedResult.convertTo(collect));

		} else {
			TransportedItemStack left = transported.copy();
			left.stack.shrink(1);

			if (collect.isEmpty())
				handler.handleProcessingOnItem(transported, TransportedResult.convertTo(left));
			else
				handler.handleProcessingOnItem(transported, TransportedResult.convertToAndLeaveHeld(collect, left));
		}

		pressTe.sendData();
		return HOLD;
	}

}
