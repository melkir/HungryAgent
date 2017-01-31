package agents;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;

import java.awt.*;

public class Agent {
    protected Color type;

    public Agent() {
        double r = Math.random();
        if (r < 0.25) type = Color.BLUE;
        else if (r < 0.5) type = Color.RED;
        else if (r < 0.75) type = Color.YELLOW;
        else type = Color.BLACK;
    }

    @ScheduledMethod(start = 1, interval = 1, priority = 2, shuffle = true)
    public void step() {
        // override by subclasses
    }

    public void randomMove() {
        Context<Agent> context = ContextUtils.getContext(this);
        Grid<Agent> grid = (Grid<Agent>) context.getProjection("Simple Grid");
        GridPoint point = grid.getLocation(this);
        double x = point.getX();
        double y = point.getY();
        int deltaX = (int) Math.round(Math.random() * 2) - 1; // valeur dans{-1, 0, 1}
        int deltaY = (int) Math.round(Math.random() * 2) - 1;
        x += deltaX;
        y += deltaY;
        grid.moveTo(this, (int) x, (int) y);
    }

    public void die() {
        Context<Agent> context = ContextUtils.getContext(this);
        if (context.size() > 1) context.remove(this);
        else RunEnvironment.getInstance().endRun();
    }


    public Color getType() {
        return type;
    }

    public void setType(Color type) {
        this.type = type;
    }
}