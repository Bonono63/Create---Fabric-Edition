package com.mrbonono63.create.foundation.ponder.content;

import com.mrbonono63.create.foundation.ponder.PonderLocalization;

public class SharedText {

	public static void gatherText() {
		// Add entries used across several ponder scenes (Safe for hotswap)

		add("sneak_and", "Sneak +");
		add("ctrl_and", "Ctrl +");

		add("rpm8", "8 RPM");
		add("rpm16", "16 RPM");
		add("rpm16_source", "Source: 16 RPM");
		add("rpm32", "32 RPM");

		add("movement_anchors", "With the help of Chassis or Super Glue, larger structures can be moved.");
		add("behaviour_modify_wrench", "This behaviour can be modified using a Wrench");
		add("storage_on_contraption", "Inventories attached to the Contraption will pick up their drops automatically");

	}

	public static String get(String key) {
		return PonderLocalization.getShared(key);
	}

	private static void add(String k, String v) {
		PonderLocalization.registerShared(k, v);
	}

}
