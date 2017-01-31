package agents;

import repast.simphony.context.Context;
import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.RepastEdge;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

public class Consumer extends Agent {
    Agent currentResource;
    private SharedSpace<Agent> space;
    private Grid<Agent> grid;
    private RepastEdge<Agent> myEdge;
    private Network<Agent> net;
    Context<Agent> context;

    class Behavior implements Runnable {
        private Consumer myAgent;

        Behavior(Consumer myAgent) {
            this.myAgent = myAgent;
        }

        @Override
        public void run() {
            Resource r = (Resource) space.take(myAgent);
            synchronized (myAgent.currentResource) {
                myAgent.currentResource = r;
            }
            myEdge = net.addEdge(myAgent, currentResource);
        }
    }

    public Consumer(Grid<Agent> grid, SharedSpace<Agent> space, Context<Agent> context) {
        this.context = context;
        net = (Network<Agent>) context.getProjection("Target network");
        this.space = space;
        this.grid = grid;
        context.add(this);
        new Thread(new Behavior(this)).start();
    }

    @Override
    public void step() {
        if (currentResource == null) randomMove();
        else moveToResource();
        GridPoint point = grid.getLocation(this);
        int x = point.getX();
        int y = point.getY();
        Resource resource = null;
        for (Object o : grid.getObjectsAt(x, y)) {
            if (o instanceof Resource) resource = (Resource) o;
        }
        if (resource != null) {
            System.out.println("Found a resource " + resource.getType());
            if (resource.equals(currentResource)) {
                if (myEdge != null) net.removeEdge(myEdge);
                myEdge = null;
                resource.die();
                currentResource = null;
                new Thread(new Behavior(this)).start();
            } else space.add(resource);
        }
    }

    public void moveToResource() {
        GridPoint point = grid.getLocation(this);
        int x = point.getX();
        int y = point.getY();
        point = grid.getLocation(currentResource);
        double x2 = point.getX();
        double y2 = point.getY();
        if (x2 > x) x++;
        if (x2 < x) x--;
        if (y2 > y) y++;
        if (y2 < y) y--;
        grid.moveTo(this, x, y);
    }
}
