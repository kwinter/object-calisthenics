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
  private static final String JOHNNYS_RESUME                     = "Johnny's resume";
  private static final String JOHNNYS_NAME                       = "Johnny";
  private static final String JOHNNYS_APPLICATION_WITH_RESUME    = applicationFor(yesterday(),
                                                                                  JOHNNYS_NAME,
                                                                                  JOHNNYS_RESUME);
  private static final String JOHNNYS_APPLICATION_WITHOUT_RESUME = applicationFor(yesterday(), JOHNNYS_NAME, "");

  private final TheLadders    theLadders                         = new TheLadders();

  private MockClock           mockClock;

  @Before
  public void setup()
  {
    mockClock = new MockClock(yesterday());
    Clock.setDefaultTo(mockClock);
  }

  @Test
  public void applyingToJReq()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    JReq job = employer.postJreqWith(new com.theladders.job.Title("Sweet job"));

    ValidResume resume = jobseeker.createResumeWith(new Title(JOHNNYS_RESUME));
    jobseeker.applyTo(job).with(resume);

    StringWriterDisplay display = new StringWriterDisplay();
    job.displayApplicationsOn(display);
    String result = display.result();
    System.out.println(result);
    assertEquals(JOHNNYS_APPLICATION_WITH_RESUME, result);
  }

  @Test
  public void applyingToAtsJob()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    AtsJob job = employer.postAtsJobWith(new com.theladders.job.Title("Sweet job"));

    jobseeker.applyTo(job);

    StringWriterDisplay display = new StringWriterDisplay();
    job.displayApplicationsOn(display);
    String result = display.result();
    System.out.println(result);
    assertEquals(JOHNNYS_APPLICATION_WITHOUT_RESUME, result);
  }

  @Test
  public void jobseekersCanListSavedJobs()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    AtsJob job = employer.postAtsJobWith(new com.theladders.job.Title("Sweet job"));

    jobseeker.save(job);

    StringWriterDisplay display = new StringWriterDisplay();
    jobseeker.displaySavedJobsOn(display);
    assertEquals(splitByNewlines(jobDisplayFor("Employer 1", "Sweet job")), display.result());
  }

  @Test
  public void jobseekersCanListAppliedJobs()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    JReq jreq = employer.postJreqWith(new com.theladders.job.Title("JReq"));
    AtsJob atsJob = employer.postAtsJobWith(new com.theladders.job.Title("ATS job"));

    ValidResume resume = jobseeker.createResumeWith(new Title(JOHNNYS_RESUME));

    jobseeker.applyTo(jreq).with(resume);
    jobseeker.applyTo(atsJob);

    StringWriterDisplay display = new StringWriterDisplay();
    jobseeker.displayAppliedJobsOn(display);
    assertEquals(splitByNewlines(jobDisplayFor("Employer 1", "JReq"), jobDisplayFor("Employer 1", "ATS job")),
                 display.result());
  }

  @Test
  public void employersCanListJobs()
  {
    Employer employer = createEmployerWith("Employer 1");

    employer.postJreqWith(new com.theladders.job.Title("JReq"));
    employer.postAtsJobWith(new com.theladders.job.Title("ATS job"));

    StringWriterDisplay display = new StringWriterDisplay();
    employer.displayJobsOn(display);
    assertEquals(splitByNewlines(jobDisplayFor("Employer 1", "JReq"), jobDisplayFor("Employer 1", "ATS job")),
                 display.result());
  }

  @Test(expected = NotYourResume.class)
  public void cantApplyWithSomeoneElsesResume()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Jobseeker anotherJobseeker = new Jobseeker(new Name("Bobby"));
    Employer employer = createEmployerWith("Employer 1");
    JReq job = employer.postJreqWith(new com.theladders.job.Title("Sweet job"));

    ValidResume anotherJobseekersResume = anotherJobseeker.createResumeWith(new Title("another jobseeker's resume"));
    jobseeker.applyTo(job).with(anotherJobseekersResume);
  }

  @Test(expected = NotYourResume.class)
  // this is verifying we can handle jobseekers with the same name by checking that it can
  // distinguish between their resumes
  public void canHandleJobseekersWithTheSameName()
  {
    Jobseeker johnny = new Jobseeker(new Name(JOHNNYS_NAME));
    Jobseeker anotherJohnny = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    JReq job = employer.postJreqWith(new com.theladders.job.Title("Sweet job"));

    ValidResume anotherJohnnysResume = anotherJohnny.createResumeWith(new Title("another jobseeker's resume"));
    johnny.applyTo(job).with(anotherJohnnysResume);
  }

  @Test
  public void canSeeApplicationNumbersByJob()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    JReq jreq = employer.postJreqWith(new com.theladders.job.Title("JReq"));
    AtsJob atsJob = employer.postAtsJobWith(new com.theladders.job.Title("ATS job"));

    ValidResume resume = jobseeker.createResumeWith(new Title(JOHNNYS_RESUME));

    jobseeker.applyTo(jreq).with(resume);
    jobseeker.applyTo(atsJob);

    Employer anotherEmployer = createEmployerWith("Employer 2");
    AtsJob anotherEmployersAtsJob = anotherEmployer.postAtsJobWith(new com.theladders.job.Title("ATS job"));

    Jobseeker anotherJobseeker = new Jobseeker(new Name("Bobby"));
    anotherJobseeker.applyTo(anotherEmployersAtsJob);
    anotherJobseeker.applyTo(atsJob);

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
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    JReq jreq = employer.postJreqWith(new com.theladders.job.Title("JReq"));
    AtsJob atsJob = employer.postAtsJobWith(new com.theladders.job.Title("ATS job"));

    ValidResume resume = jobseeker.createResumeWith(new Title(JOHNNYS_RESUME));

    jobseeker.applyTo(jreq).with(resume);
    jobseeker.applyTo(atsJob);

    Employer anotherEmployer = createEmployerWith("Employer 2");
    AtsJob anotherEmployersAtsJob = anotherEmployer.postAtsJobWith(new com.theladders.job.Title("ATS job"));

    Jobseeker anotherJobseeker = new Jobseeker(new Name("Bobby"));
    anotherJobseeker.applyTo(anotherEmployersAtsJob);
    anotherJobseeker.applyTo(atsJob);

    StringWriterDisplay display = new StringWriterDisplay();
    theLadders.reportApplicationsByEmployerOn(display);

    assertEquals(splitByNewlines(applicationCountDisplayFor("Employer 1", 3),
                                 applicationCountDisplayFor("Employer 2", 1)),
                 display.result());
  }

  @Test
  public void canSeeApplicationReportInCsv()
  {

    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    JReq jreq = employer.postJreqWith(new com.theladders.job.Title("JReq"));
    AtsJob atsJob = employer.postAtsJobWith(new com.theladders.job.Title("ATS job"));

    ValidResume resume = jobseeker.createResumeWith(new Title(JOHNNYS_RESUME));

    jobseeker.applyTo(jreq).with(resume);
    jobseeker.applyTo(atsJob);

    Employer anotherEmployer = createEmployerWith("Employer 2");
    AtsJob anotherEmployersAtsJob = anotherEmployer.postAtsJobWith(new com.theladders.job.Title("ATS job"));

    Jobseeker anotherJobseeker = new Jobseeker(new Name("Bobby"));
    anotherJobseeker.applyTo(anotherEmployersAtsJob);
    anotherJobseeker.applyTo(atsJob);

    CsvDisplay display = new CsvDisplay();
    theLadders.reportApplicationsByEmployerOn(display);

    assertEquals(splitByNewlines(csvApplicationCountDisplayFor("Employer 1", 3),
                                 csvApplicationCountDisplayFor("Employer 2", 1)),
                 display.result());
  }

  @Test
  public void canSeeApplicationReportInHtml()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    JReq jreq = employer.postJreqWith(new com.theladders.job.Title("JReq"));
    AtsJob atsJob = employer.postAtsJobWith(new com.theladders.job.Title("ATS job"));

    ValidResume resume = jobseeker.createResumeWith(new Title(JOHNNYS_RESUME));

    jobseeker.applyTo(jreq).with(resume);
    jobseeker.applyTo(atsJob);

    Employer anotherEmployer = createEmployerWith("Employer 2");
    AtsJob anotherEmployersAtsJob = anotherEmployer.postAtsJobWith(new com.theladders.job.Title("ATS job"));

    Jobseeker anotherJobseeker = new Jobseeker(new Name("Bobby"));
    anotherJobseeker.applyTo(anotherEmployersAtsJob);
    anotherJobseeker.applyTo(atsJob);

    HtmlDisplay display = new HtmlDisplay();
    theLadders.reportApplicationsByEmployerOn(display);

    String expected = "<table><tr><td>Employer 1</td><td>3</td></tr><tr><td>Employer 2</td><td>1</td></tr></table>";
    assertEquals(expected, display.result());
  }

  @Test
  public void canSeeWhichJobseekersAppliedByDay()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    AtsJob atsJob = employer.postAtsJobWith(new com.theladders.job.Title("ATS job"));

    jobseeker.applyTo(atsJob);

    mockClock.setTimeTo(twoDaysAgo());
    Jobseeker bobby = new Jobseeker(new Name("Bobby"));
    bobby.applyTo(atsJob);
    Jobseeker billy = new Jobseeker(new Name("Billy"));
    billy.applyTo(atsJob);

    StringWriterDisplay display = new StringWriterDisplay();
    theLadders.reportJobseekersThatAppliedOn(yesterday(), display);

    assertEquals(splitByNewlines(JOHNNYS_NAME), display.result());

    display = new StringWriterDisplay();
    theLadders.reportJobseekersThatAppliedOn(twoDaysAgo(), display);

    assertEquals(splitByNewlines("Bobby", "Billy"), display.result());
  }

  private Employer createEmployerWith(String name)
  {
    return theLadders.createEmployerWith(new com.theladders.employer.Name(name));
  }

  private static String applicationFor(Date date,
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
    return jobDisplayFor(employer, title) + StringWriterDisplay.DELIMITER + count;
  }

  private static String jobDisplayFor(String employer,
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
}
