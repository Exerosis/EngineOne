package me.engineone.engine.displayables.alpha.menu;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.engineone.core.component.Component;
import me.engineone.engine.displayables.menu.Element;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.comphenix.protocol.ProtocolLibrary.getProtocolManager;
import static com.comphenix.protocol.wrappers.WrappedChatComponent.fromText;
import static me.engineone.engine.utilites.PacketUtils.sendSilently;

public class PageImplementation extends Component implements Page {
    private static final AtomicInteger WINDOW_ID = new AtomicInteger();
    private final List<Element> elements = new ArrayList<>();
    private final Player player;
    private final byte windowId;

    private WrappedChatComponent title = fromText("Container");
    private int rows = 1;

    public PageImplementation(Player player) {
        this.player = player;
        windowId = (byte) WINDOW_ID.getAndIncrement();
    }


    @Override
    public Element element() {
        return null;
    }

    @Override
    public Page setTitle(CharSequence title) {
        return setTitle(fromText(title.toString()));
    }

    @Override
    public Page setTitle(WrappedChatComponent title) {
        this.title = title;
        refresh();
        return this;
    }

    @Override
    public Page setRows(int rows) {
        this.rows = rows;
        refresh();
        return this;
    }

    private void refresh() {
        if (!isEnabled())
            return;
        PacketContainer packet = getProtocolManager().createPacket(PacketType.Play.Server.OPEN_WINDOW);
        packet.getBytes().write(0, windowId);
        packet.getStrings().write(0, "minecraft:container");
        packet.getChatComponents().write(0, title);
        packet.getBytes().write(1, (byte) (rows * 8));
        sendSilently(player, packet);
    }
}
