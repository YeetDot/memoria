package com.yeetdot.memoria;

import com.yeetdot.memoria.block.ModBlocks;
import com.yeetdot.memoria.block.entity.ModBlockEntities;
import com.yeetdot.memoria.item.ModItemGroups;
import com.yeetdot.memoria.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Memoria implements ModInitializer {
	public static final String MOD_ID = "memoria";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		ModItems.registerItems();
		ModBlocks.registerBlocks();
		ModItemGroups.registerItemGroups();
		ModBlockEntities.registerBlockEntityTypes();
	}
}