package me.engineone.engine.utilites;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.function.Predicate;

/**
 * Durpped in to existence by Exerosis on 3/17/2016.
 */
@SuppressWarnings("ALL")
public class BlockUtilities {

    private BlockUtilities() {
    }

    public static ItemStack toItemStack(Block block) {
        return new ItemStack(block.getType(), 1, block.getData());
    }

    public static MaterialData toMaterialData(Block block) {
        return new MaterialData(block.getType(), block.getData());
    }

    public static void main(String[] args) {
        int distance = 3;
        do {
            System.out.println(distance);
        }
        while (--distance > 0);
    }

    public static Block directSearch(Location location, BlockFace direction, Predicate<Block> target) {
        return directSearch(location.getBlock(), direction, target);
    }

    public static Block directSearch(Block block, BlockFace direction, Predicate<Block> target) {
        do
            if (target.test(block))
                return block;
        while (!(block = block.getRelative(direction)).getType().isSolid());
        return null;
    }
}
