package me.engineone.engine.components.redis;

import me.engineone.core.component.Component;
import org.redisson.Redisson;

public class DataComponent extends Component {

    private final Redisson redisson;

    public DataComponent(Redisson redisson) {
        this.redisson = redisson;
    }

}
