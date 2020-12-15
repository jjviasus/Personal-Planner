import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.date.PlannerDate;
import model.planner.IPlannerModel;
import model.planner.SimplePlannerModel;
import model.theme.PlannerTheme;
import org.junit.Test;

public class SimplePlannerModelTest {
  // addTask
  @Test
  public void addTask() {
    IPlannerModel<PlannerTheme, PlannerDate> model = new SimplePlannerModel();
    // check model has no tasks
    assertTrue(model.getTasks().isEmpty());
    // add the task
    model.addTask("Study", new PlannerDate(11,11,2000));
    // check the model has the task
    assertEquals(Arrays.asList(Arrays.asList("Study")), model.getTasks());

    // add another task same date
    model.addTask("Run", new PlannerDate(11,11,2000));
    // check the model has the task
    assertEquals(Arrays.asList(Arrays.asList("Study", "Run")), model.getTasks());

    // add another task later date
    model.addTask("Eat", new PlannerDate(3,1,2020));
    // check the model has the task
    List<List<String>> output = new ArrayList<>(Arrays.asList(Arrays.asList("Study", "Run"),
        Arrays.asList("Eat")));
    assertEquals(output, model.getTasks());

    // add another task earlier date
    model.addTask("Sleep", new PlannerDate(4,24,1990));
    output.add(0, Arrays.asList("Sleep"));
    assertEquals(output, model.getTasks());
  }

  // addTask invalid
  @Test
  public void addTaskInvalid() {
    IPlannerModel<PlannerTheme, PlannerDate> model = new SimplePlannerModel();

    // null task
    try {
      model.addTask(null, new PlannerDate(11,11,2000));
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // null date
    try {
      model.addTask("Run", null);
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }
  }

  // getTasks
  @Test
  public void getTasks() {
    IPlannerModel<PlannerTheme, PlannerDate> model = new SimplePlannerModel();

    // get tasks on empty list of tasks
    assertTrue(model.getTasks().isEmpty());

    // add some tasks to varying days
    model.addTask("Run", new PlannerDate(11,11,2000));
    model.addTask("Run again", new PlannerDate(11,11,2000));
    model.addTask("Sleep", new PlannerDate(1,11,2000));
    model.addTask("Sleep again", new PlannerDate(1,12,2000));
    model.addTask("Roam the future", new PlannerDate(11,11,2030));
    model.addTask("Roam the past", new PlannerDate(6,1,1980));

    // get the tasks
    assertEquals(new ArrayList<>(Arrays.asList("Roam the past", "Sleep", "Sleep again", "Run", "Run again", "Roam the future")), model.getTasks());
  }

  // removeTask
  @Test
  public void removeTask() {
    IPlannerModel<PlannerTheme, PlannerDate> model = new SimplePlannerModel();

    // add a task
    model.addTask("Run", new PlannerDate(11,11,2000));
    // check its there
    assertEquals(Arrays.asList("Run"), model.getTasks());
    // remove it
    model.removeTask("Run", new PlannerDate(11,11,2000));
    // check it was remove
    assertTrue(model.getTasks().isEmpty());

    // add tasks different days
    model.addTask("Run", new PlannerDate(11,11,2000));
    model.addTask("Run again", new PlannerDate(1,12,2002));
    // check its there
    assertEquals(Arrays.asList("Run", "Run again"), model.getTasks());
    // remove it
    model.removeTask("Run", new PlannerDate(11,11,2000));
    // check it was remove
    assertEquals(Arrays.asList("Run again"), model.getTasks());
  }

  // removeTask invalid
  @Test
  public void removeTaskInvalid() {
    IPlannerModel<PlannerTheme, PlannerDate> model = new SimplePlannerModel();

    // attempt to remove null task
    try {
      model.removeTask(null, new PlannerDate(11,11,2000));
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // attempt to remove null date
    try {
      model.removeTask("Does not exist", null);
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // attempt to remove a task when the task list is empty
    try {
      model.removeTask("Does not exist", new PlannerDate(11,11,2000));
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    model.addTask("Run", new PlannerDate(11,11,2000));

    // attempt to remove a task where the task doesn't exist
    try {
      model.removeTask("Does not exist", new PlannerDate(11,11,2000));
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // attempt to remove a task where the date doesn't exist
    try {
      model.removeTask("Run", new PlannerDate(11,12,2000));
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

  }

  // moveTask
  @Test
  public void moveTask() {
    IPlannerModel<PlannerTheme, PlannerDate> model = new SimplePlannerModel();

    // move a task to a new date with no tasks in it
    model.addTask("Run", new PlannerDate(11,11,2000));
    assertEquals(Arrays.asList("Run"), model.getTasksInDay(new PlannerDate(11,11,2000)));
    model.moveTask("Run", new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
    assertEquals(Arrays.asList("Run"), model.getTasksInDay(new PlannerDate(11,12,2000)));
    assertTrue(model.getTasksInDay(new PlannerDate(11,11,2000)).isEmpty());

    // move a task to a date that already has tasks in it
    model.addTask("Study", new PlannerDate(11,11,2000));
    model.addTask("Work", new PlannerDate(11,11,2000));
    model.moveTask("Study", new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
    assertEquals(Arrays.asList("Run", "Study"), model.getTasksInDay(new PlannerDate(11,12,2000)));
    assertEquals(Arrays.asList("Work"), model.getTasksInDay(new PlannerDate(11,11,2000)));
  }

  // moveTask invalid
  @Test
  public void moveTaskInvalid() {
    IPlannerModel<PlannerTheme, PlannerDate> model = new SimplePlannerModel();
    model.addTask("Run", new PlannerDate(11,11,2000));
    model.addTask("Eat", new PlannerDate(11,12,2000));

    // null task
    try {
      model.moveTask(null, new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // null initial date
    try {
      model.moveTask("Run", null, new PlannerDate(11,12,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // null new date
    try {
      model.moveTask("Run", new PlannerDate(11,11,2000), null);
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // initial date does not exist
    try {
      model.moveTask("Run", new PlannerDate(11,13,2000), new PlannerDate(11,12,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // task does not exist
    try {
      model.moveTask("Does not exist", new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // same date
    try {
      model.moveTask("Run", new PlannerDate(11,11,2000), new PlannerDate(11,11,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }
  }

  // addPoints

  // addPoints invalid

  // getTotalPoints

  // removePoints

  // removePoints invalid

  // getUserName

  // setUserName

  // setUserName invalid

  // getThemes

  // addTheme

  // addTheme invalid

  // setTheme

  // setTheme invalid

  // getTasksInDay

  // getTasksInDay invalid
}