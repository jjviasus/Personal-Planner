package model.factory;

import model.planner.DatabasePlannerModel;
import model.planner.IPlannerModel;
import model.planner.SimplePlannerModel;

/**
 * A class that offers a factory method to return an instance of a subclass of
 * IPlannerModel. It offers two different planner types: simple and database.
 */
public class PlannerCreator {

  /**
   * Represents the planner type to be used. Can be one of two values: SIMPLE or DATABASE.
   */
  public enum PlannerType {
    SIMPLE,
    DATABASE
  }

  /**
   * Returns a new instance of the given type of planner.
   *
   * @param type the desired type of planner
   * @return a new instance of the specified type
   * @throws IllegalArgumentException if the give type is invalid
   */
  public static IPlannerModel create(PlannerType type) {
    switch (type) {
      case SIMPLE:
        return new SimplePlannerModel();
      case DATABASE:
        return new DatabasePlannerModel();
      default:
        throw new IllegalArgumentException("Invalid planner type given");
    }
  }
}
