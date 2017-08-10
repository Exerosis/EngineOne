package me.engineone.thraxpvp;

import me.engineone.engine.Arena;
import me.engineone.engine.components.scheduler.Scheduler;

public class EndPhase extends Arena {
    public EndPhase() {
        addChild(Scheduler.every().minute().synchronously().run(this::complete));
        onEnable(() -> {

        });
        onDisable(() -> {

        });
    }
}