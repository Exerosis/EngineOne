package me.engineone.thraxpvpecon;

import com.vexsoftware.votifier.Votifier;
import com.vexsoftware.votifier.model.VotifierEvent;
import com.vexsoftware.votifier.net.VoteReceiver;
import me.engineone.core.component.ParentComponent;
import me.engineone.engine.components.scheduler.SchedulerComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.redisson.Redisson;
import org.redisson.api.RListMultimap;
import org.redisson.api.RedissonClient;

import java.util.Random;

import static me.engineone.engine.components.event.EventComponent.listen;
import static me.engineone.thraxpvpecon.EconController.msgFormat;

//VoteComponent: Listens and Registers Votes at the Player's Use

public class VoteComponent extends ParentComponent{

    //Redis
    private final RedissonClient redisson;
    private final RListMultimap playermoneyList;

    //Vote Loot
    private final ItemStack[] voteItems;
    private final ItemStack[] specialVoteItems;
    private final ItemStack lootKey;
    private final ItemMeta lootkeyMeta;

    //Main Initializer
    public VoteComponent(SchedulerComponent schedulerComponent){

        //Redis
        redisson = Redisson.create();
        playermoneyList = redisson.getListMultimap("player-econdata");

        //Define Vote Loot
        voteItems = new ItemStack[]{ new ItemStack(Material.DIAMOND, 3) };
        lootKey = new ItemStack(Material.TRIPWIRE_HOOK);
        lootkeyMeta = lootKey.getItemMeta();
        lootkeyMeta.setDisplayName("LootCrate Key");
        lootKey.setItemMeta(lootkeyMeta);
        specialVoteItems = new ItemStack[]{ lootKey };

        //Start Vote Registration
        onEnable(
            () -> {
                try {
                    new VoteReceiver(new Votifier(), "10.0.0.1", 30465);
                } catch (Exception e){ e.printStackTrace(); }
            }
        );

        //Send out Message Advertising Voting to all Players
        addChild(listen(PlayerJoinEvent.class,
            (PlayerJoinEvent playerJoinEvent) ->
                schedulerComponent.task(() ->
                    playerJoinEvent.getPlayer().sendMessage(msgFormat + "Vote everyday for great loot! Link: http://www.thraxpvp.com/vote")
                ,20.0)
            )
        );

        //Listen for Incoming Vote Registrations
        addChild(listen(VotifierEvent.class,
            (VotifierEvent voteEvent) -> {
                Player votingPlayer = Bukkit.getPlayer(voteEvent.getVote().getUsername());
                playermoneyList.get(votingPlayer.getUniqueId()).set(0,
                (double) playermoneyList.get(votingPlayer.getUniqueId()).get(0) + 100.0);
                int random = new Random().nextInt(2);
                if(random == 0)
                    votingPlayer.getInventory().addItem(voteItems);
                else
                    votingPlayer.getInventory().addItem(specialVoteItems);
                votingPlayer.sendMessage(msgFormat + "You voted and received your loot! ThraxPvP thanks you!");
            }
        ));

    }
}
