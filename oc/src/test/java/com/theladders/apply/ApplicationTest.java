package com.theladders.apply;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.theladders.TheLadders;
import com.theladders.employer.Employer;
import com.theladders.job.application.NotYourResume;
import com.theladders.job.ats.AtsJob;
import com.theladders.job.jreq.JReq;
import com.theladders.jobseeker.Jobseeker;
import com.theladders.jobseeker.Name;
import com.theladders.jobseeker.resume.Resume.ValidResume;
import com.theladders.jobseeker.resume.Title;
import com.theladders.reporting.format.CsvDisplay;
import com.theladders.reporting.format.HtmlDisplay;
import com.theladders.reporting.format.StringWriterDisplay;
import com.theladders.time.Clock;
import com.theladders.time.Clock.MockClock;

public class ApplicationTest
{
  private final TheLadders theLadders = new TheLadders();

  private MockClock        mockClock;

  @Before
  public void setup()
  {
    mockClock = new MockClock(yesterday());
    Clock.setDefaultTo(mockClock);
  }

  @Test
  public void applyingToJReq()
  {
    Employer employer = employerNamed("Employer 1");
    JReq job = employer.postJreqWith(title("Sweet job"));

    Jobseeker jobseeker = jobseekerNamed("Johnny");
    ValidResume resume = jobseeker.createResumeWith(new Title("Johnny's resume"));

    jobseeker.applyTo(job).with(resume);

    StringWriterDisplay display = new StringWriterDisplay();
    job.displayApplicationsOn(display);
    assertEquals(expectedApplicationFor(yesterday(), "Johnny", "Johnny's resume"), display.result());
  }

  @Test
  public void applyingToAtsJob()
  {
    Employer employer = employerNamed("Employer 1");
    AtsJob job = employer.postAtsJobWith(title("Sweet job"));

    jobseekerNamed("Johnny").applyTo(job);

    StringWriterDisplay display = new StringWriterDisplay();
    job.displayApplicationsOn(display);
    assertEquals(expectedApplicationFor(yesterday(), "Johnny", ""), display.result());
  }

  @Test
  public void jobseekersCanListSavedJobs()
  {
    Employer employer = employerNamed("Employer 1");
    AtsJob job = employer.postAtsJobWith(title("Sweet job"));

    Jobseeker jobseeker = jobseekerNamed("Johnny");
    jobseeker.save(job);

    StringWriterDisplay display = new StringWriterDisplay();
    jobseeker.displaySavedJobsOn(display);
    assertEquals(splitByNewlines(expectedJobDisplayFor("Employer 1", "Sweet job")), display.result());
  }

  @Test
  public void jobseekersCanListAppliedJobs()
  {
    Employer employer = employerNamed("Employer 1");
    JReq jreq = employer.postJreqWith(title("JReq"));
    AtsJob atsJob = employer.postAtsJobWith(title("ATS job"));

    Jobseeker jobseeker = jobseekerNamed("Johnny");
    ValidResume resume = jobseeker.createResumeWith(new Title("Johnny's resume"));

    jobseeker.applyTo(jreq).with(resume);
    jobseeker.applyTo(atsJob);

    StringWriterDisplay display = new StringWriterDisplay();
    jobseeker.displayAppliedJobsOn(display);
    assertEquals(splitByNewlines(expectedJobDisplayFor("Employer 1", "JReq"),
                                 expectedJobDisplayFor("Employer 1", "ATS job")),
                 display.result());
  }

  @Test
  public void employersCanListJobs()
  {
    Employer employer = employerNamed("Employer 1");

    employer.postJreqWith(title("JReq"));
    employer.postAtsJobWith(title("ATS job"));

    StringWriterDisplay display = new StringWriterDisplay();
    employer.displayJobsOn(display);
    assertEquals(splitByNewlines(expectedJobDisplayFor("Employer 1", "JReq"),
                                 expectedJobDisplayFor("Employer 1", "ATS job")),
                 display.result());
  }

  @Test(expected = NotYourResume.class)
  public void cantApplyWithSomeoneElsesResume()
  {
    Employer employer = employerNamed("Employer 1");
    JReq job = employer.postJreqWith(title("Sweet job"));

    Jobseeker johnny = jobseekerNamed("Johnny");
    Jobseeker bobby = jobseekerNamed("Bobby");

    ValidResume bobbysResume = bobby.createResumeWith(new Title("Bobby's resume"));
    johnny.applyTo(job).with(bobbysResume);
  }

  @Test(expected = NotYourResume.class)
  // this is verifying we can handle jobseekers with the same name by checking that it can
  // distinguish between their resumes
  public void canHandleJobseekersWithTheSameName()
  {
    Employer employer = employerNamed("Employer 1");
    JReq job = employer.postJreqWith(title("Sweet job"));

    Jobseeker johnny = jobseekerNamed("Johnny");
    Jobseeker anotherJohnny = jobseekerNamed("Johnny");

    ValidResume anotherJohnnysResume = anotherJohnny.createResumeWith(new Title("another jobseeker's resume"));
    johnny.applyTo(job).with(anotherJohnnysResume);
  }

  @Test
  public void canSeeApplicationNumbersByJob()
  {
    setupComplexScenario();

    StringWriterDisplay display = new StringWriterDisplay();
    theLadders.reportApplicationsByJobOn(display);

    assertEquals(splitByNewlines(applicationCountDisplayFor("Employer 1", "JReq", 1),
                                 applicationCountDisplayFor("Employer 1", "ATS job", 2),
                                 applicationCountDisplayFor("Employer 2", "ATS job", 1)),
                 display.result());
  }

  @Test
  public void canSeeApplicationNumbersByEmployer()
  {
    setupComplexScenario();

    StringWriterDisplay display = new StringWriterDisplay();
    theLadders.reportApplicationsByEmployerOn(display);

    assertEquals(splitByNewlines(applicationCountDisplayFor("Employer 1", 3),
                                 applicationCountDisplayFor("Employer 2", 1)),
                 display.result());
  }

  @Test
  public void canSeeApplicationReportInCsv()
  {
    setupComplexScenario();

    CsvDisplay display = new CsvDisplay();
    theLadders.reportApplicationsByEmployerOn(display);

    assertEquals(splitByNewlines(csvApplicationCountDisplayFor("Employer 1", 3),
                                 csvApplicationCountDisplayFor("Employer 2", 1)),
                 display.result());
  }

  @Test
  public void canSeeApplicationReportInHtml()
  {
    setupComplexScenario();

    HtmlDisplay display = new HtmlDisplay();
    theLadders.reportApplicationsByEmployerOn(display);

    String expected = "<table><tr><td>Employer 1</td><td>3</td></tr><tr><td>Employer 2</td><td>1</td></tr></table>";
    assertEquals(expected, display.result());
  }

  @Test
  public void canSeeWhichJobseekersAppliedByDay()
  {
    Employer employer = employerNamed("Employer 1");
    AtsJob atsJob = employer.postAtsJobWith(title("ATS job"));

    Jobseeker johnny = jobseekerNamed("Johnny");

    johnny.applyTo(atsJob);

    mockClock.setTimeTo(twoDaysAgo());

    Jobseeker bobby = jobseekerNamed("Bobby");
    bobby.applyTo(atsJob);
    Jobseeker billy = jobseekerNamed("Billy");
    billy.applyTo(atsJob);

    thenReportSaysApplicantsOnDateAre(yesterday(), "Johnny");
    thenReportSaysApplicantsOnDateAre(twoDaysAgo(), "Bobby", "Billy");
  }

  private void setupComplexScenario()
  {
    Jobseeker jobseeker = jobseekerNamed("Johnny");
    Employer employer = employerNamed("Employer 1");
    JReq jreq = employer.postJreqWith(title("JReq"));
    AtsJob atsJob = employer.postAtsJobWith(title("ATS job"));

    ValidResume resume = jobseeker.createResumeWith(new Title("Johnny's resume"));

    jobseeker.applyTo(jreq).with(resume);
    jobseeker.applyTo(atsJob);

    Employer anotherEmployer = employerNamed("Employer 2");
    AtsJob anotherEmployersAtsJob = anotherEmployer.postAtsJobWith(title("ATS job"));

    Jobseeker anotherJobseeker = jobseekerNamed("Bobby");
    anotherJobseeker.applyTo(anotherEmployersAtsJob);
    anotherJobseeker.applyTo(atsJob);
  }

  private Employer employerNamed(String name)
  {
    return theLadders.createEmployerWith(new com.theladders.employer.Name(name));
  }

  private Jobseeker jobseekerNamed(String name)
  {
    return new Jobseeker(new Name(name));
  }

  private com.theladders.job.Title title(String title)
  {
    return new com.theladders.job.Title(title);
  }

  private static String expectedApplicationFor(Date date,
                                               String name,
                                               String resumeTitle)
  {
    return SimpleDateFormat.getDateInstance().format(date) + StringWriterDisplay.DELIMITER
           + name
           + StringWriterDisplay.DELIMITER
           + resumeTitle;
  }

  private static String splitByNewlines(String... lines)
  {
    StringBuilder builder = new StringBuilder();
    for (String line : lines)
    {
      builder.append(line).append(StringWriterDisplay.NEW_LINE);
    }
    return builder.toString();
  }

  private static String applicationCountDisplayFor(String employer,
                                                   String title,
                                                   int count)
  {
    return expectedJobDisplayFor(employer, title) + StringWriterDisplay.DELIMITER + count;
  }

  private static String expectedJobDisplayFor(String employer,
                                              String title)
  {
    return employer + StringWriterDisplay.DELIMITER + title;
  }

  private static String applicationCountDisplayFor(String employer,
                                                   int count)
  {
    return employer + StringWriterDisplay.DELIMITER + count;
  }

  private static String csvApplicationCountDisplayFor(String employer,
                                                      int count)
  {
    return employer + CsvDisplay.DELIMITER + count;
  }

  private static Date yesterday()
  {
    return daysAgo(1);
  }

  private static Date twoDaysAgo()
  {
    return daysAgo(2);
  }

  private static Date daysAgo(int numberOfDaysAgo)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -numberOfDaysAgo);
    return calendar.getTime();
  }

  private void thenReportSaysApplicantsOnDateAre(Date date,
                                                 String... expectedNames)
  {
    StringWriterDisplay display = new StringWriterDisplay();
    theLadders.reportJobseekersThatAppliedOn(date, display);
    assertEquals(splitByNewlines(expectedNames), display.result());
  }
}
