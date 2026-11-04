package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntitiesDataGenerator implements IDataGenerator {

    @Override
    public String getDataName() {
        return "blockEntities";
    }

    @Override
    public JsonArray generateDataJson() {
        final JsonArray resultArray = new JsonArray();
        final RegistryAccess registryAccess = DGU.getWorld().registryAccess();
        final Registry<BlockEntityType<?>> blockEntityRegistry = registryAccess.lookupOrThrow(Registries.BLOCK_ENTITY_TYPE);
        final Registry<Block> blockRegistry = registryAccess.lookupOrThrow(Registries.BLOCK);

        for (final BlockEntityType<?> type : blockEntityRegistry) {
            final JsonObject typeDesc = new JsonObject();
            typeDesc.addProperty("id", blockEntityRegistry.getId(type));
            typeDesc.addProperty("name", blockEntityRegistry.getKey(type).getPath());

            final JsonArray blocksArray = new JsonArray();
            for (final Block block : blockRegistry) {
                if (type.isValid(block.defaultBlockState())) {
                    blocksArray.add(blockRegistry.getKey(block).getPath());
                }
            }
            typeDesc.add("blocks", blocksArray);
            resultArray.add(typeDesc);
        }
        return resultArray;
    }
}
