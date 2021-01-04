import controller.IPlannerController;
import controller.SimplePlannerController;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.date.PlannerDate;
import model.planner.IPlannerModel;
import model.planner.SimplePlannerModel;
import model.task.PlannerTask;
import view.IPlannerView;
import view.SimplePlannerView;

public final class Main {
  public static void main(String[] args) {
/*    try {
      // Set System L&F
      UIManager.setLookAndFeel(
          UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (UnsupportedLookAndFeelException e) {
      // handle exception
    }
    catch (ClassNotFoundException e) {
      // handle exception
    }
    catch (InstantiationException e) {
      // handle exception
    }
    catch (IllegalAccessException e) {
      // handle exception
    }*/

    //new SwingApplication(); //Create and show the GUI.


    IPlannerModel model = new SimplePlannerModel();
    model.addTask(new PlannerTask("Eat some cookies"), new PlannerDate(1,2,2021));
    model.addTask(new PlannerTask("Eat some cookies AGAIN"), new PlannerDate(1,2,2021));
    model.addTask(new PlannerTask("Eat MORE cookies"), new PlannerDate(1,4,2021));
    model.addTask(new PlannerTask("Eat MORE cookies"), new PlannerDate(1,1,2021));
    IPlannerController controller = new SimplePlannerController();

    controller.createPlanner(model);
  }
}
