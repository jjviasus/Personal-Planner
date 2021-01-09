import controller.IPlannerController;
import controller.SimplePlannerController;
import model.planner.DataBasePlannerModel;
import model.planner.IPlannerModel;
import model.planner.SimplePlannerModel;


public final class Main {
  public static void main(String[] args) {
      IPlannerModel model = new DataBasePlannerModel();
      IPlannerController controller = new SimplePlannerController();

      controller.createPlanner(model);
  }
}
