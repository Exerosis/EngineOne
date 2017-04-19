package me.engineone.engine.components.barrier;

import me.engineone.engine.components.base.ListenerComponent;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Barriers
 *
 * Created by Nathan on 4/4/2017.
 */
public class BarrierComponent extends ListenerComponent{

    //Clock
    private final Timer timer = new Timer();

    //Timers
    private int msgtimer = 0;
    private int runtimer = 0;

    //Growth size
    private float changesize;

    //Rate
    private int ratemultiplyer;

    //Percent size
    private int percentsize;

    //Constructer Fields
    private final Iterable<Player> players;
    private float size;
    private int groworshrink;
    private int duration;
    private boolean warn;

    //Constructers
    public BarrierComponent(Iterable<Player> players, int size) {
        this.players = players;
        this.size = size;
    }

    public BarrierComponent(Iterable<Player> players, int size, int groworshrink, int duration){
        this.players = players;
        this.size = size;
        this.groworshrink = groworshrink;
        this.duration = duration;
    }

    public BarrierComponent(Iterable<Player> players, int size, int groworshrink, int duration, int ratemultiplyer){
        this.players = players;
        this.size = size;
        this.groworshrink = groworshrink;
        this.duration = duration;
        this.ratemultiplyer = ratemultiplyer;
    }

    public BarrierComponent(Iterable<Player> players, int size, int groworshrink, int duration, int ratemultiplyer, boolean warn){
        this.players = players;
        this.size = size;
        this.groworshrink = groworshrink;
        this.duration = duration;
        this.ratemultiplyer = ratemultiplyer;
        this.warn = warn;
    }

    //Component Initialization
    @Override
    public void enable(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateBarrier();
            }
        }
        , 0, 50);
    }

    @Override
    public void disable(){ timer.cancel(); }

    //Get & Set
    public float getSize(){ return this.size; }
    public void setSize(float size){ this.size = size; }

    public int getSizePercent(){ return this.percentsize; }
    public void setSizePercent(int i){ this.percentsize = i; setSize((getSize() * percentsize) / 100); }

    public int getGrowing(){ return this.groworshrink; }
    public void setGrowing(int i){ this.groworshrink = i; }

    public float getChangeSize(){ return this.changesize; }
    public void setChangeSize(float i){ this.changesize = i; }

    public int getRateMultiplyer(){ return ratemultiplyer; }
    public void setRateMultiplyer(int i){ ratemultiplyer = i; changesize = changesize * ratemultiplyer; }

    public int getDuration(){ return this.duration; }
    public void setDuration(int i){ this.duration = i; }

    public boolean getDoesWarn(){ return this.warn; }
    public void setDoesWarn(boolean b){ this.warn = b; }

    //Manual size change
    public void increment(float changesize){ setSize(size + changesize); }
    public void decrement(float changesize){ setSize(size - changesize); }

    //Enforces the barrier
    private void updateBarrier() {

        //Update barrier properties
        if(duration != 0) {

            //Keep track of uptime
            runtimer++;
            int systemtimeduration = duration * 20;

            //Disables barrier if uptime is completed
            if (runtimer == systemtimeduration) {
                runtimer = 0;
                disable();
            }

            //Set size change values
            changesize = size / systemtimeduration;

            //Control size growth/decay
            if (groworshrink == 1) {
                decrement(changesize);
            }
            if (groworshrink == 2) {
                increment(changesize);
            }
        }

        //Cycles through all affected players
        players.forEach(player -> {

                //Gets if the player is trespassing
                if (player != null) {
                    int actualx = (int) player.getLocation().getX();
                    int actualz = (int) player.getLocation().getZ();
                    int absx = Math.abs((int) player.getLocation().getX());
                    int absz = Math.abs((int) player.getLocation().getZ());
                    if (absx >= size || absz >= size) {
                        createbordereffect(player, absx, absz, actualx, actualz);
                    } else {
                        return;
                    }
                }
            }
        );
    }

    //Enforces the barrier limit
    private void createbordereffect(Player player, int xdist, int zdist, int actualx, int actualz) {

        //Send the player back to the limit line
        int limitline = (int) size;
        if (xdist > size) {
            if(actualx < 0){ limitline = (int) -size; }
            player.teleport(new Location(player.getWorld(), limitline, (double) player.getWorld().getHighestBlockYAt(limitline, actualz), actualz));
        }
        if(zdist > size){
            if(actualz < 0){ limitline = (int) -size; }
            player.teleport(new Location(player.getWorld(), actualx, (double) player.getWorld().getHighestBlockYAt(actualx, limitline), limitline));
        }

        //Send warn message...(On a shorter frequency than the main thread)
        if(warn) {
            msgtimer++;
            if (msgtimer == 15) {
                msgtimer = 0;
                player.sendMessage(ChatColor.RED + "This is the limit of the area...Turn back!");
            }
        }
    }
}
