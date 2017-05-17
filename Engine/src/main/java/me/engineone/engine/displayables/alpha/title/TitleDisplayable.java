package me.engineone.engine.displayables.alpha.title;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.TitleAction;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.engineone.engine.alpha.TimeUnitBuilder;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import static java.lang.String.valueOf;

@SuppressWarnings("unchecked")
public class TitleDisplayable {
    private BiConsumer<Title, Player> description;
    private int time = 1;
    private TimeUnit unit = TimeUnit.SECONDS;

    public TitleDisplayable like(BiConsumer<Title, Player> description) {
        this.description = description;
        return this;
    }

    public TimeUnitBuilder<TitleDisplayable> fadeInFor(int time) {
        this.time = time;
        return new TimeUnitBuilder<>(this).onBuild(unit -> {
            this.unit = unit;
        });
    }

    public TimeUnitBuilder<TitleDisplayable> fadeOutFor(int time) {
        this.time = time;
        return new TimeUnitBuilder<>(this).onBuild(unit -> {
            this.unit = unit;
        });
    }

    public TimeUnitBuilder<TitleDisplayable> stayFor(int time) {
        this.time = time;
        return new TimeUnitBuilder<>(this).onBuild(unit -> {
            this.unit = unit;
        });
    }

    private void display(Player player) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        Title title = new Title() {
            @Override
            public Title setTitle(CharSequence text) {
                sendPacket(TitleAction.TITLE, valueOf(text));
                return this;
            }

            @Override
            public Title setSubtitle(CharSequence text) {
                sendPacket(TitleAction.SUBTITLE, valueOf(text));
                return this;
            }

            @Override
            public Title setFooter(CharSequence text) {
                PacketContainer packet = manager.createPacket(PacketType.Play.Server.CHAT);
                packet.getChatComponents().write(0, WrappedChatComponent.fromText(valueOf(text)));
                packet.getBytes().write(0, (byte) 2);
                try {
                    manager.sendServerPacket(player, packet);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return this;
            }

            private void sendPacket(TitleAction action, String text) {
                PacketContainer packet = manager.createPacket(PacketType.Play.Server.TITLE);
                packet.getTitleActions().write(0, TitleAction.SUBTITLE);
                packet.getStrings().write(0, valueOf(text));
                try {
                    manager.sendServerPacket(player, packet);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        };
        description.accept(title, player);

        //TODO Handle enable and disable of title down here :D
    }


}
