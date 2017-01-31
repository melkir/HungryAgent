package styles;

import agents.Agent;
import agents.Consumer;
import repast.simphony.visualizationOGL2D.DefaultStyleOGL2D;

import java.awt.*;

public class AgentStyle2D extends DefaultStyleOGL2D {
    @Override
    public Color getColor(Object o) {
        return ((Agent) o).getType();
    }

    @Override
    public float getScale(Object o) {
        if (o instanceof Consumer) return 2f;
        return 1f;
    }
}