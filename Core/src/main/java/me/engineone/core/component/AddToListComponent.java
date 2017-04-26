package me.engineone.core.component;

import java.util.List;

/**
 * Created by BinaryBench on 4/23/2017.
 */
public class AddToListComponent<T> extends Component {

    public AddToListComponent(List<T> list, T element) {
        onEnable(() -> list.add(element));
        onDisable(() -> list.remove(element));
    }

    public AddToListComponent(List<T> list, int index, T element) {
        onEnable(() -> list.add(index, element));
        onDisable(() -> list.remove(element));
    }

}
