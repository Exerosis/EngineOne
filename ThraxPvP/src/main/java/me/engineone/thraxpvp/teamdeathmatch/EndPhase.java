package me.engineone.thraxpvp.teamdeathmatch;

import me.engineone.core.completeable.ParentPhase;
import me.engineone.engine.components.scheduler.Scheduler;

public class EndPhase extends ParentPhase {
    public EndPhase() {
        addChild(Scheduler.every().minute().synchronously().run(this::complete));
        onEnable(() -> {

        });
        onDisable(() -> {

        });
    }
}