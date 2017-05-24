package me.engineone.example.games.runner.components;

import me.engineone.core.component.Component;
import me.engineone.core.holder.MutateHolder;
import me.engineone.engine.utilites.scheduler.SyncRunnable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by BinaryBench on 5/3/2017.
 */
@SuppressWarnings("deprecation")
public class RunnerComponent extends Component implements SyncRunnable {

    //not static in case I want to have it per-map at some point in the future

    private MaterialData[] dropBlockDatas = new MaterialData[]{
            new MaterialData(Material.STAINED_CLAY, (byte) 5),
            new MaterialData(Material.STAINED_CLAY, (byte) 4),
            new MaterialData(Material.STAINED_CLAY, (byte) 14)
    };

    private MutateHolder<Player> playerHolder;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture scheduledFuture;

    private Map<Block, Byte> blocks = new HashMap<>();

    public RunnerComponent(MutateHolder<Player> playerHolder, ScheduledExecutorService scheduler) {
        this.playerHolder = playerHolder;
        this.scheduler = scheduler;

        onEnable(() -> {
            this.scheduledFuture = getScheduler().scheduleAtFixedRate(this, 200, 200, TimeUnit.MILLISECONDS);
        });

        onDisable(() -> {
            this.scheduledFuture.cancel(true);
        });
    }

    @Override
    public void syncRun() {
        for (Player player : playerHolder) {
            dropBlocks(player.getLocation());
        }
        List<Block> remove = new ArrayList<>();

        for (Map.Entry<Block, Byte> entry : blocks.entrySet()) {
            Block block = entry.getKey();
            byte cycle = entry.getValue();

            if (cycle < dropBlockDatas.length) {
                //Change Block
                MaterialData newBlockData = dropBlockDatas[cycle];
                block.setType(newBlockData.getItemType());
                block.setData(newBlockData.getData());
                blocks.put(block, ++cycle);
            } else {
                FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), block.getType(), block.getData());
                fallingBlock.setDropItem(false);
                block.setType(Material.AIR);
                remove.add(block);
            }

        }
        for (Block block : remove)
            blocks.remove(block);

    }


    public void dropBlocks(Location location) {

        double dist = 0.32;

        World world = location.getWorld();

        /*
        if (!world.equals( #world ))
            return;
        */

        double x = location.getX();

        double y = location.getY() - 0.5;

        double z = location.getZ();

        HashSet<Block> blocks = new LinkedHashSet<>(

                Arrays.asList(

                        new Location(world, x + dist, y, z + dist).getBlock(),

                        new Location(world, x - dist, y, z + dist).getBlock(),

                        new Location(world, x - dist, y, z - dist).getBlock(),

                        new Location(world, x + dist, y, z - dist).getBlock()

                ));
        for (Block block : blocks) {
            addBlock(block);
        }
    }

    public void addBlock(Block block) {
        if ((block == null) || (block.getType().equals(Material.AIR)) || (block.isLiquid()))
            return;

        //if (!block.getRelative(BlockFace.UP).getType().equals(Material.AIR))
        //    return;


        blocks.putIfAbsent(block, (byte) 0);
    }


    public MaterialData[] getDropBlockDatas() {
        return dropBlockDatas;
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public ScheduledFuture getScheduledFuture() {
        return scheduledFuture;
    }
}