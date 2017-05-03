package me.engineone.engine.utilites.scheduler;

import me.engineone.engine.utilites.ServerUtil;
import org.bukkit.Bukkit;

/**
 * Created by BinaryBench on 4/28/2017.
 */
@FunctionalInterface
public interface SyncRunnable extends Runnable {

    @Override
    default void run() {
        Bukkit.getScheduler().runTask(ServerUtil.getPlugin(), this::syncRun);
    }

    void syncRun();
}
