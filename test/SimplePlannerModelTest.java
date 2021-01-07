import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Box.Filler;
import model.date.IDate;
import model.date.PlannerDate;
import model.planner.IPlannerModel;
import model.planner.SimplePlannerModel;
import model.task.ITask;
import model.task.PlannerTask;
import model.theme.ITheme;
import model.theme.PlannerTheme;
import org.junit.Test;

// TODO: make sure nothing returns a reference and always returns a copy in the classes, attempt modifying the data and ensure that it does not change

public class SimplePlannerModelTest {
  // addTask
  @Test
  public void addTask() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();
    // check model has no tasks
    assertTrue(model.getAllTasks().isEmpty());
    // add the task
    PlannerTask study = new PlannerTask("Study");
    model.addTask(study, new PlannerDate(11,11,2000));
    // check the model has the task
    assertEquals(Arrays.asList(study), model.getAllTasks());

    // add another task same date
    PlannerTask run = new PlannerTask("Run");
    model.addTask(run, new PlannerDate(11,11,2000));
    // check the model has the task
    assertEquals(Arrays.asList(study, run), model.getAllTasks());

    // add another task later date
    PlannerTask eat = new PlannerTask("Eat");
    model.addTask(eat, new PlannerDate(3,1,2020));
    // check the model has the task
    assertEquals(Arrays.asList(study, run, eat), model.getAllTasks());

    // add another task earlier date
    PlannerTask sleep = new PlannerTask("Sleep");
    model.addTask(sleep, new PlannerDate(4,24,1990));
    assertEquals(Arrays.asList(sleep, study, run, eat), model.getAllTasks());
  }

  // addTask invalid
  @Test
  public void addTaskInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

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
      model.addTask(new PlannerTask("Run"), null);
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }
  }

  // getAllTasks
  @Test
  public void getAllTasks() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // get tasks on empty list of tasks
    assertTrue(model.getAllTasks().isEmpty());

    ITask run = new PlannerTask("Run");
    ITask runAgain = new PlannerTask("Run again");
    ITask sleep = new PlannerTask("Sleep");
    ITask sleepAgain = new PlannerTask("Sleep again");
    ITask roamTheFuture = new PlannerTask("Roam the future");
    ITask roamThePast = new PlannerTask("Roam the past");

    // add some tasks to varying days
    model.addTask(run, new PlannerDate(11,11,2000));
    model.addTask(runAgain, new PlannerDate(11,11,2000));
    model.addTask(sleep, new PlannerDate(1,11,2000));
    model.addTask(sleepAgain, new PlannerDate(1,12,2000));
    model.addTask(roamTheFuture, new PlannerDate(11,11,2030));
    model.addTask(roamThePast, new PlannerDate(6,1,1980));

    // get the tasks
    assertEquals(new ArrayList<>(Arrays.asList(roamThePast, sleep, sleepAgain, run, runAgain, roamTheFuture)), model.getAllTasks());
  }

  // removeTask
  @Test
  public void removeTask() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    ITask run = new PlannerTask("Run");

    // add a task
    model.addTask(run, new PlannerDate(11,11,2000));
    // check its there
    assertEquals(Arrays.asList(run), model.getAllTasks());
    // remove it
    model.removeTask(run, new PlannerDate(11,11,2000));
    // check it was removed
    assertTrue(model.getAllTasks().isEmpty());

    ITask runAgain = new PlannerTask("Run again");

    // add tasks different days
    model.addTask(run, new PlannerDate(11,11,2000));
    model.addTask(runAgain, new PlannerDate(1,12,2002));
    // check its there
    assertEquals(Arrays.asList(run, runAgain), model.getAllTasks());
    // remove it
    model.removeTask(run, new PlannerDate(11,11,2000));
    // check it was remove
    assertEquals(Arrays.asList(runAgain), model.getAllTasks());
  }

  // removeTask invalid
  @Test
  public void removeTaskInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

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
      model.removeTask(new PlannerTask("Does not exist"), null);
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // attempt to remove a task when the task list is empty
    try {
      model.removeTask(new PlannerTask("Does not exist"), new PlannerDate(11,11,2000));
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));

    // attempt to remove a task where the task doesn't exist
    try {
      model.removeTask(new PlannerTask("Does not exist"), new PlannerDate(11,11,2000));
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // attempt to remove a task where the date doesn't exist
    try {
      model.removeTask(new PlannerTask("Run"), new PlannerDate(11,12,2000));
      // exception not caught
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

  }

  // moveTask
  @Test
  public void moveTask() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    ITask run = new PlannerTask("Run");

    // move a task to a new date with no tasks in it
    model.addTask(run, new PlannerDate(11,11,2000));
    assertEquals(Arrays.asList(run), model.getTasksAtDate(new PlannerDate(11,11,2000)));
    model.moveTask(run, new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
    assertEquals(Arrays.asList(run), model.getTasksAtDate(new PlannerDate(11,12,2000)));
    assertTrue(model.getTasksAtDate(new PlannerDate(11,11,2000)).isEmpty());

    ITask study = new PlannerTask("Study");
    ITask work = new PlannerTask("Work");

    // move a task to a date that already has tasks in it
    model.addTask(study, new PlannerDate(11,11,2000));
    model.addTask(work, new PlannerDate(11,11,2000));
    model.moveTask(study, new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
    assertEquals(Arrays.asList(run, study), model.getTasksAtDate(new PlannerDate(11,12,2000)));
    assertEquals(Arrays.asList(work), model.getTasksAtDate(new PlannerDate(11,11,2000)));
  }

  // moveTask invalid
  @Test
  public void moveTaskInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();
    model.addTask(new PlannerTask("Run"), new PlannerDate(11,11,2000));
    model.addTask(new PlannerTask("Eat"), new PlannerDate(11,12,2000));

    // null task
    try {
      model.moveTask(null, new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // null initial date
    try {
      model.moveTask(new PlannerTask("Run"), null, new PlannerDate(11,12,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // null new date
    try {
      model.moveTask(new PlannerTask("Run"), new PlannerDate(11,11,2000), null);
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // initial date does not exist
    try {
      model.moveTask(new PlannerTask("Run"), new PlannerDate(11,13,2000), new PlannerDate(11,12,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // task does not exist
    try {
      model.moveTask(new PlannerTask("Does not exist"), new PlannerDate(11,11,2000), new PlannerDate(11,12,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }

    // same date
    try {
      model.moveTask(new PlannerTask("Run"), new PlannerDate(11,11,2000), new PlannerDate(11,11,2000));
      fail();
    } catch (IllegalArgumentException e) {
      // exception caught
    }
  }

  // getTotalPoints
  @Test
  public void getTotalPoints() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // zero points
    assertEquals(0, model.getTotalPoints());

    PlannerTask run = new PlannerTask("Run");
    PlannerTask eat = new PlannerTask("Eat");
    PlannerTask oldTask = new PlannerTask("Old task");

    // non-zero points
    model.addTask(run, new PlannerDate(11,11,2000));
    model.addTask(eat, new PlannerDate(11,11,2000));
    model.addTask(oldTask, new PlannerDate(11,11,1950));
    // check that the points have not been added
    assertEquals(0, model.getTotalPoints());
    // mark the old task as complete and check the points
    model.setTaskAsCompleted(oldTask, new PlannerDate(11,11,1950));
    assertEquals(true, model.tasksCompleteAtDate(new PlannerDate(11,11,1950)));
    assertEquals(10, model.getTotalPoints());
    // mark only one task on 11/11/2000 as complete, not both
    model.setTaskAsCompleted(run, new PlannerDate(11,11,2000));
    assertEquals(10, model.getTotalPoints());
    assertEquals(false, model.tasksCompleteAtDate(new PlannerDate(11,11,2000)));
    // mark both tasks on 11/11/2000 as complete
    model.setTaskAsCompleted(eat, new PlannerDate(11,11,2000));
    assertEquals(20, model.getTotalPoints());
    assertEquals(true, model.tasksCompleteAtDate(new PlannerDate(11,11,2000)));
  }

  // getUserName
  @Test
  public void getUserName() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // default user's name
    assertEquals("Enter your name!", model.getUserName());

    // set user's name
    model.setUserName("Justin");
    assertEquals("Justin", model.getUserName());
  }

  // setUserName
  @Test
  public void setUserName() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // set user's name
    assertEquals("Enter your name!", model.getUserName());
    model.setUserName("Justin");
    assertEquals("Justin", model.getUserName());

    // update it
    model.setUserName("Viasus");
    assertEquals("Viasus", model.getUserName());
  }

  // setUserName invalid
  @Test
  public void setUserNameInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // set user's name to null
    assertEquals("Enter your name!", model.getUserName());

    try {
      model.setUserName(null);
      fail();
      // exception not thrown
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

    assertEquals("Enter your name!", model.getUserName());
  }

  // getAllThemes
  @Test
  public void getAllThemes() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // dark theme
    ITheme light = new PlannerTheme("Light", "Times New Roman",
        new Color(30,30,30), 12, new Color(250,250,250),
        new Color(200,200,200), new Color(150,150,150), 0, 0);
    ITheme dark = new PlannerTheme("Dark", "Courier New",
        new Color(255,255,255), 12, new Color(30,30,30),
        new Color(50,50,50), new Color(75,75,75), 10, 1);
    // get themes
    assertTrue(model.getAllThemes().contains(light));
    assertEquals(new ArrayList(Arrays.asList(light, dark)), model.getAllThemes());

    // attempt to modify themes
    model.getAllThemes().remove(light);
    model.getAllThemes().remove(dark);
    // check that they haven't been modified
    assertTrue(model.getAllThemes().contains(light));
    assertTrue(model.getAllThemes().contains(dark));
    assertEquals(new ArrayList(Arrays.asList(light, dark)), model.getAllThemes());
  }

  // setCurrentTheme
  @Test
  public void setCurrentTheme() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    ITheme light = new PlannerTheme("Light", "Times New Roman",
        new Color(30,30,30), 12, new Color(250,250,250),
        new Color(200,200,200), new Color(150,150,150), 0, 0);

    assertEquals(light, model.getCurrentTheme());

    ITheme dark = new PlannerTheme("Dark", "Courier New",
        new Color(255,255,255), 12, new Color(30,30,30),
        new Color(50,50,50), new Color(75,75,75), 10, 1);

    ITask run = new PlannerTask("Run");
    IDate date = new PlannerDate(11,11,2000);

    model.addTask(run, date);
    model.setTaskAsCompleted(run, date);
    assertEquals(10, model.getTotalPoints());

    // change the current theme
    model.buyTheme(dark);
    model.setCurrentTheme(dark);
    assertEquals(dark, model.getCurrentTheme());

    // change it back to light
    model.setCurrentTheme(light);
    assertEquals(light, model.getCurrentTheme());

    // attempt setting the same theme that is already set
    // change it back to light
    model.setCurrentTheme(light);
    assertEquals(light, model.getCurrentTheme());
  }

  // setCurrentTheme invalid
  @Test
  public void setCurrentThemeInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // dark theme
    ITheme light = new PlannerTheme("Light", "Times New Roman",
        new Color(30,30,30), 12, new Color(250,250,250),
        new Color(200,200,200), new Color(150,150,150), 0, 0);
    ITheme dark = new PlannerTheme("Dark", "Courier New",
        new Color(255,255,255), 12, new Color(30,30,30),
        new Color(50,50,50), new Color(75,75,75), 10, 1);
    ITheme invalidTheme = new PlannerTheme("Does not exist", "Wrong",
        new Color(0,255,255), 20, new Color(30,30,30),
        new Color(50,50,50), new Color(75,75,75), 10, 2);

    // null
    try {
      model.setCurrentTheme(null);
      fail();
      // exception not thrown
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

    // theme has not been bought yet
    try {
      model.setCurrentTheme(dark);
      fail();
      // exception not thrown
    } catch (IllegalStateException e) {
      // exception thrown
    }

    // invalid theme
    try {
      model.setCurrentTheme(invalidTheme);
      fail();
      // exception not thrown
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

    ITask run = new PlannerTask("Run");
    IDate date = new PlannerDate(11,11,2000);
    IDate futureDate = new PlannerDate(11,11,2021);

    model.addTask(run, futureDate);
    model.setTaskAsCompleted(run, futureDate);
    // check that the future date being complete does not mean points are award
    // and can set the current theme to dark
    try {
      model.setCurrentTheme(dark);
      fail();
      // exception not thrown
    } catch (IllegalStateException e) {
      // exception thrown
    }
  }

  // getCurrentTheme
  @Test
  public void getCurrentTheme() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    ITheme light = new PlannerTheme("Light", "Times New Roman",
        new Color(30,30,30), 12, new Color(250,250,250),
        new Color(200,200,200), new Color(150,150,150), 0, 0);

    assertEquals(light, model.getCurrentTheme());

    ITheme dark = new PlannerTheme("Dark", "Courier New",
        new Color(255,255,255), 12, new Color(30,30,30),
        new Color(50,50,50), new Color(75,75,75), 10, 1);

    ITask run = new PlannerTask("Run");
    IDate date = new PlannerDate(11,11,2000);

    model.addTask(run, date);
    model.setTaskAsCompleted(run, date);
    assertEquals(10, model.getTotalPoints());

    // change the current theme
    model.buyTheme(dark);
    model.setCurrentTheme(dark);
    assertEquals(dark, model.getCurrentTheme());
  }

  // getTasksAtDate
  @Test
  public void getTasksAtDate() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // date that has no tasks
    assertEquals(new ArrayList<>(), model.getTasksAtDate(new PlannerDate(1,1,2000)));

    ITask run = new PlannerTask("Run");
    ITask eat = new PlannerTask("Eat");
    ITask eatAgain = new PlannerTask("Eat again");

    model.addTask(run, new PlannerDate(11,11,2000));
    model.addTask(eat, new PlannerDate(11,12,2000));
    model.addTask(eat, new PlannerDate(11,12,2000));
    model.addTask(eatAgain, new PlannerDate(11,12,2000));

    assertEquals(Arrays.asList(run), model.getTasksAtDate(new PlannerDate(11,11,2000)));
    assertEquals(Arrays.asList(eat, eat, eatAgain), model.getTasksAtDate(new PlannerDate(11,12,2000)));
  }

  // getTasksAtDate invalid
  @Test
  public void getTasksAtDateInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // null date
    try {
      model.getTasksAtDate(null);
      // exception not thrown
      fail();
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

/*    // a date that has no tasks
    try {
      model.getTasksAtDate(new PlannerDate(11,11,2000));
      // exception not thrown
      fail();
    } catch (IllegalArgumentException e) {
      // exception thrown
    }*/
  }

  // setTaskAsCompleted
  @Test
  public void setTaskAsCompleted() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    ITask run = new PlannerTask("Run");
    IDate date = new PlannerDate(11,11,2000);

    // add task
    model.addTask(run, date);
    // check it is not complete
    assertFalse(model.tasksCompleteAtDate(date));
    // mark it as complete
    model.setTaskAsCompleted(run, date);
    assertTrue(model.tasksCompleteAtDate(date));

    // mark an already complete task as complete
    model.setTaskAsCompleted(run, date);
    // check the completion status has not changed
    assertTrue(model.tasksCompleteAtDate(date));
  }

  // setTaskAsCompleted Invalid
  @Test
  public void setTaskAsCompletedInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    ITask run = new PlannerTask("Run");
    IDate date = new PlannerDate(11,11,2000);

    // null task
    try {
      model.setTaskAsCompleted(null, date);
      fail();
      // exception not thrown
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

    // null date
    try {
      model.setTaskAsCompleted(run, null);
      fail();
      // exception not thrown
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

    model.addTask(run, date);

    // date is not in tree map (has not tasks)
    try {
      model.setTaskAsCompleted(run, new PlannerDate(1,1,1900));
      fail();
      // exception not thrown
    } catch (IllegalStateException e) {
      // exception thrown
    }

    // task is not present at date
    try {
      model.setTaskAsCompleted(new PlannerTask("Not present"), date);
      fail();
      // exception not thrown
    } catch (IllegalStateException e) {
      // exception thrown
    }
  }

  // setTaskAsIncomplete
  @Test
  public void setTaskAsIncomplete() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    ITask run = new PlannerTask("Run");
    IDate date = new PlannerDate(11,11,2000);

    // add task
    model.addTask(run, date);
    // check it is not complete
    assertFalse(model.tasksCompleteAtDate(date));
    // mark it as complete
    model.setTaskAsCompleted(run, date);
    assertTrue(model.tasksCompleteAtDate(date));
    // mark it as incomplete
    model.setTaskAsIncomplete(run, date);
    // check it is not complete
    assertFalse(model.tasksCompleteAtDate(date));

    // mark an already incomplete task as incomplete
    model.setTaskAsIncomplete(run, date);
    // check the completion status has not changed
    assertFalse(model.tasksCompleteAtDate(date));
  }

  // setTaskAsIncomplete Invalid
  @Test
  public void setTaskAsIncompleteInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    ITask run = new PlannerTask("Run");
    IDate date = new PlannerDate(11,11,2000);

    // null task
    try {
      model.setTaskAsIncomplete(null, date);
      fail();
      // exception not thrown
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

    // null date
    try {
      model.setTaskAsIncomplete(run, null);
      fail();
      // exception not thrown
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

    model.addTask(run, date);

    // date is not in tree map (has not tasks)
    try {
      model.setTaskAsIncomplete(run, new PlannerDate(1,1,1900));
      fail();
      // exception not thrown
    } catch (IllegalStateException e) {
      // exception thrown
    }

    // task is not present at date
    try {
      model.setTaskAsIncomplete(new PlannerTask("Not present"), date);
      fail();
      // exception not thrown
    } catch (IllegalStateException e) {
      // exception thrown
    }
  }

  // buyTheme
  @Test
  public void buyTheme() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    ITheme light = new PlannerTheme("Light", "Times New Roman",
        new Color(30,30,30), 12, new Color(250,250,250),
        new Color(200,200,200), new Color(150,150,150), 0, 0);

    assertEquals(light, model.getCurrentTheme());

    ITheme dark = new PlannerTheme("Dark", "Courier New",
        new Color(255,255,255), 12, new Color(30,30,30),
        new Color(50,50,50), new Color(75,75,75), 10, 1);

    ITask run = new PlannerTask("Run");
    IDate date = new PlannerDate(11,11,2000);

    model.addTask(run, date);
    model.setTaskAsCompleted(run, date);
    assertEquals(10, model.getTotalPoints());

    // buy the dark theme
    model.buyTheme(dark);
    model.setCurrentTheme(dark);
    assertEquals(dark, model.getCurrentTheme());

    // attempt buying themes that have already been bought
    model.buyTheme(dark);
    model.buyTheme(light);
    // check that the current theme has not been changed
    assertEquals(dark, model.getCurrentTheme());
  }

  // buyTheme invalid
  @Test
  public void buyThemeInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    ITheme light = new PlannerTheme("Light", "Times New Roman",
        new Color(30,30,30), 12, new Color(250,250,250),
        new Color(200,200,200), new Color(150,150,150), 0, 0);

    assertEquals(light, model.getCurrentTheme());

    ITheme dark = new PlannerTheme("Dark", "Courier New",
        new Color(255,255,255), 12, new Color(30,30,30),
        new Color(50,50,50), new Color(75,75,75), 10, 1);

    ITask run = new PlannerTask("Run");
    IDate date = new PlannerDate(11,11,2000);

    // null theme
    try {
      model.buyTheme(null);
      fail();
      // exception not thrown
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

    ITheme invalidTheme = new PlannerTheme("Does not exist", "Wrong",
        new Color(0,255,255), 20, new Color(30,30,30),
        new Color(50,50,50), new Color(75,75,75), 10, 2);

    // theme that does not exist
    try {
      model.buyTheme(invalidTheme);
      fail();
      // exception not thrown
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

    // insufficient funds
    try {
      model.buyTheme(dark);
      fail();
      // exception not thrown
    } catch (IllegalStateException e) {
      // exception thrown
    }
  }

  // tasksCompleteAtDate
  @Test
  public void tasksCompleteAtDate() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();


  }

  // tasksCompleteAtDate Invalid
  @Test
  public void tasksCompleteAtDateInvalid() {
    IPlannerModel<ITheme, IDate, ITask> model = new SimplePlannerModel();

    // null date
    try {
      model.tasksCompleteAtDate(null);
      fail();
      // exception not thrown
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

    // date contains no tasks
    try {
      model.tasksCompleteAtDate(new PlannerDate(1,1,1));
      fail();
      //exception not thrown
    } catch (IllegalArgumentException e) {
      // exception thrown
    }

    // test adding a date with a task, then removing that its only task and checking if it
    // still throws an error
    ITask run = new PlannerTask("Run");
    IDate date = new PlannerDate(11,11,2000);

    model.addTask(run, date);
    model.removeTask(run, date);
    // check that the key got deleted from the dictionary
    try {
      model.tasksCompleteAtDate(date);
      fail();
      // exception not thrown
    } catch (IllegalArgumentException e) {
      // exception thrown
    }
  }

}