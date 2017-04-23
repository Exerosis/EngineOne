package me.engineone.core.component;

import java.util.List;

/**
 * Created by BinaryBench on 4/23/2017.
 */
public class KeepInListComponent<T> extends Component {

    public KeepInListComponent(List<T> list, T element) {
        onEnable(() -> list.add(element));
        onDisable(() -> list.remove(element));
    }

}
