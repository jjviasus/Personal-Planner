package model.date;

/**
 * An IDate implementation that is to used for an IPlanner.
 */
public class PlannerDate implements IDate {

  private int day;
  private int month;
  private int year;

  /**
   * Constructs a planner date with a day, month, and year as arguments.
   *
   * @param day   the day as an int
   * @param month the month as an int
   * @param year  the year as an int
   */
  public PlannerDate(int day, int month, int year) {
    // check for valid dates

    // check year is valid
    if (this.year < 0) {
      throw new IllegalArgumentException("Invalid year given");
    }

    // dates with 31 days
    if (this.month == 1 || this.month == 3 || this.month == 5 || this.month == 7 || this.month == 8
        || this.month == 10 || this.month == 12) {
      if (this.day > 31 || this.day <= 0) {
        throw new IllegalArgumentException("Invalid day given");
      }
    } else if (this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11) {
      if (this.day > 30 || this.day <= 0) {
        throw new IllegalArgumentException("Invalid day given");
      }
    } else if (this.month == 2) {
      // does not consider leap year which has 29 days.
      if (this.day > 28 || this.day <= 0) {
        throw new IllegalArgumentException("Invalid day given");
      }
    } else {
      throw new IllegalArgumentException("Invalid month given");
    }

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
