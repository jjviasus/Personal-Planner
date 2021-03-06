package model.date;

/**
 * An interface that keeps track of date data.
 */
public interface IDate {
  /**
   * Gets the day of this date object.
   * @returns the day as an int
   */
  public int getDay();

  /**
   * Gets the month of this date object.
   * @returns the month as an int
   */
  public int getMonth();

  /**
   * Gets the year of this date object.
   * @returns the year as an int
   */
  public int getYear();

  /**
   * Adds one day to the current date and returns the new date.
   */
  public void addDay();

  /**
   * Subtracts one day from the current date.
   */
  public void subtractDay();
}
