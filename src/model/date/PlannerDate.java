package model.date;

/**
 * An IDate implementation that is to used for an IPlanner.
 */
public class PlannerDate implements IDate {
  int day;
  int month;
  int year;

  /**
   * Constructs a planner date with a day, month, and year as arguments.
   * @param day the day as an int
   * @param month the month as an int
   * @param year the year as an int
   */
  public PlannerDate(int day, int month, int year) {
    // check for valid dates
    this.day = day;
    this.month = month;
    this.year = year;
  }

  @Override
  public int getDay() {
    return this.day;
  }

  @Override
  public int getMonth() {
    return this.month;
  }

  @Override
  public int getYear() {
    return this.year;
  }
}
