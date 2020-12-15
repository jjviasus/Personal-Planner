package model.date;

/**
 * An IDate implementation that is to used for an IPlanner.
 */
public class PlannerDate implements IDate, Comparable<PlannerDate> {

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
  public PlannerDate(int month, int day, int year) {
    // check for valid dates

    // check year is valid
    if (year < 0) {
      throw new IllegalArgumentException("Invalid year given");
    }

    // dates with 31 days
    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
        || month == 10 || month == 12) {
      if (day > 31 || day <= 0) {
        throw new IllegalArgumentException("Invalid day given");
      }
    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
      if (day > 30 || day <= 0) {
        throw new IllegalArgumentException("Invalid day given");
      }
    } else if (month == 2) {
      // does not consider leap year which has 29 days.
      if (day > 28 || day <= 0) {
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

  @Override
  public int compareTo(PlannerDate o) {
    return (this.day + this.month * 100  + this.year * 100000) -
        (o.day + o.month * 100 + o.year * 100000);
  }
}
