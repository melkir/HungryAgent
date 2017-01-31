import agents.Agent;
import agents.Consumer;
import agents.Resource;
import agents.SharedSpace;
import repast.simphony.context.Context;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.RandomGridAdder;

public class ContextManager implements ContextBuilder<Agent> {
    public Context<Agent> build(Context<Agent> context) {
        int xdim = 50;
        int ydim = 50;
        new NetworkBuilder<Agent>("Target network", context, true).buildNetwork();
        Grid<Agent> grid = GridFactoryFinder.createGridFactory(null).createGrid("Simple Grid", context,
                new GridBuilderParameters<Agent>(new repast.simphony.space.grid.WrapAroundBorders(),
                        new RandomGridAdder<Agent>(), true, xdim, ydim));
        SharedSpace<Agent> sSpace = new SharedSpace<>();
        Parameters p = RunEnvironment.getInstance().getParameters();
        int numConsumers = (Integer) p.getValue("nbconsumers");
        int numResources = (Integer) p.getValue("nbresources");
        for (int i = 0; i < numConsumers; i++) new Consumer(grid, sSpace, context);
        for (int i = 0; i < numResources; i++) new Resource(context);
        return context;
    }
}