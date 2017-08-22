package me.engineone.thraxpvpecon;

//EconController: Handles ThraxPvP Economy

import me.engineone.core.component.ParentComponent;
import me.engineone.engine.components.scheduler.SchedulerComponent;
import me.engineone.engine.utilites.color.Palettes;
import org.bukkit.ChatColor;

public class EconController extends ParentComponent{

    //Chat Messaging Format for Component's Chat Functions
    public static final String msgFormat = Palettes.secondary(Palettes.RED.accent(ChatColor.RED, true)) + Palettes.bold()
    + "[ThraxPvP]" + Palettes.reset() + Palettes.lightShade(Palettes.YELLOW.accent(ChatColor.GOLD, true)) + " ";

    //Scheduler
    private final SchedulerComponent schedulerComponent;

    //Initializes the Economy System
    public EconController(){

        //Scheduler (For Certain Use)
        schedulerComponent = addChild(new SchedulerComponent());

        //Init Voting
//        addChild(new VoteComponent(schedulerComponent));

        //Init Loot
        addChild(new LootComponent());

        //Init Class Purchasing
        addChild(new ClassComponent());

        //Init Shop
        addChild(new ShopComponent());
    }
}
