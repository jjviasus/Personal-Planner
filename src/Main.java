import controller.IPlannerController;
import controller.database.DatabasePlannerController;
import controller.simple.SimplePlannerController;
import model.planner.IPlannerModel;
import model.factory.PlannerCreator;
import model.factory.PlannerCreator.PlannerType;


public final class Main {
  public static void main(String[] args) {
    PlannerType type;
    IPlannerController controller;

    switch (args[0]) {
      case "simple":
        type = PlannerType.SIMPLE;
        break;
      case "database":
        type = PlannerType.DATABASE;
        break;
      default: throw new IllegalArgumentException("Invalid planner type given");
    }

      IPlannerModel model = PlannerCreator.create(type);

    // should we get rid of this if else statement? do we need a controller factory? !!!
    if (type.equals(PlannerType.SIMPLE)) {
      controller = new SimplePlannerController();
    } else {
      controller = new DatabasePlannerController();
    }

      controller.usePlanner(model);
  }
}
