package com.theladders.apply;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

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

public class ApplicationTest
{
  private static final String JOHNNYS_RESUME                     = "Johnny's resume";
  private static final String JOHNNYS_NAME                       = "Johnny";
  private static final String JOHNNYS_APPLICATION_WITH_RESUME    = applicationFor(today(), JOHNNYS_NAME, JOHNNYS_RESUME);
  private static final String JOHNNYS_APPLICATION_WITHOUT_RESUME = applicationFor(today(), JOHNNYS_NAME, "");

  private final TheLadders    theLadders                         = new TheLadders();

  @Test
  public void applyingToJReq()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    JReq job = employer.createJreqWith(new com.theladders.job.Title("Sweet job"));

    ValidResume resume = jobseeker.createResumeWith(new Title(JOHNNYS_RESUME));
    jobseeker.applyTo(job).with(resume);

    StringWriterDisplay display = new StringWriterDisplay();
    job.displayApplicationsOn(display);
    assertEquals(JOHNNYS_APPLICATION_WITH_RESUME, display.result());
  }

  @Test
  public void applyingToAtsJob()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    AtsJob job = employer.createAtsJobWith(new com.theladders.job.Title("Sweet job"));

    jobseeker.applyTo(job);

    StringWriterDisplay display = new StringWriterDisplay();
    job.displayApplicationsOn(display);
    assertEquals(JOHNNYS_APPLICATION_WITHOUT_RESUME, display.result());
  }

  @Test
  public void jobseekersCanListSavedJobs()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    AtsJob job = employer.createAtsJobWith(new com.theladders.job.Title("Sweet job"));

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
    JReq jreq = employer.createJreqWith(new com.theladders.job.Title("JReq"));
    employer.post(jreq);
    AtsJob atsJob = employer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    employer.post(atsJob);

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

    JReq jreq = employer.createJreqWith(new com.theladders.job.Title("JReq"));
    employer.post(jreq);
    AtsJob atsJob = employer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    employer.post(atsJob);

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
    JReq job = employer.createJreqWith(new com.theladders.job.Title("Sweet job"));

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
    JReq job = employer.createJreqWith(new com.theladders.job.Title("Sweet job"));

    ValidResume anotherJohnnysResume = anotherJohnny.createResumeWith(new Title("another jobseeker's resume"));
    johnny.applyTo(job).with(anotherJohnnysResume);
  }

  @Test
  public void canSeeApplicationNumbersByJob()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = createEmployerWith("Employer 1");
    JReq jreq = employer.createJreqWith(new com.theladders.job.Title("JReq"));
    employer.post(jreq);
    AtsJob atsJob = employer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    employer.post(atsJob);

    ValidResume resume = jobseeker.createResumeWith(new Title(JOHNNYS_RESUME));

    jobseeker.applyTo(jreq).with(resume);
    jobseeker.applyTo(atsJob);

    Employer anotherEmployer = createEmployerWith("Employer 2");
    AtsJob anotherEmployersAtsJob = anotherEmployer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    anotherEmployer.post(anotherEmployersAtsJob);

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
    JReq jreq = employer.createJreqWith(new com.theladders.job.Title("JReq"));
    employer.post(jreq);
    AtsJob atsJob = employer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    employer.post(atsJob);

    ValidResume resume = jobseeker.createResumeWith(new Title(JOHNNYS_RESUME));

    jobseeker.applyTo(jreq).with(resume);
    jobseeker.applyTo(atsJob);

    Employer anotherEmployer = createEmployerWith("Employer 2");
    AtsJob anotherEmployersAtsJob = anotherEmployer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    anotherEmployer.post(anotherEmployersAtsJob);

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
    JReq jreq = employer.createJreqWith(new com.theladders.job.Title("JReq"));
    employer.post(jreq);
    AtsJob atsJob = employer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    employer.post(atsJob);

    ValidResume resume = jobseeker.createResumeWith(new Title(JOHNNYS_RESUME));

    jobseeker.applyTo(jreq).with(resume);
    jobseeker.applyTo(atsJob);

    Employer anotherEmployer = createEmployerWith("Employer 2");
    AtsJob anotherEmployersAtsJob = anotherEmployer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    anotherEmployer.post(anotherEmployersAtsJob);

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
    JReq jreq = employer.createJreqWith(new com.theladders.job.Title("JReq"));
    employer.post(jreq);
    AtsJob atsJob = employer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    employer.post(atsJob);

    ValidResume resume = jobseeker.createResumeWith(new Title(JOHNNYS_RESUME));

    jobseeker.applyTo(jreq).with(resume);
    jobseeker.applyTo(atsJob);

    Employer anotherEmployer = createEmployerWith("Employer 2");
    AtsJob anotherEmployersAtsJob = anotherEmployer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    anotherEmployer.post(anotherEmployersAtsJob);

    Jobseeker anotherJobseeker = new Jobseeker(new Name("Bobby"));
    anotherJobseeker.applyTo(anotherEmployersAtsJob);
    anotherJobseeker.applyTo(atsJob);

    HtmlDisplay display = new HtmlDisplay();
    theLadders.reportApplicationsByEmployerOn(display);

    String expected = "<table><tr><td>Employer 1</td><td>3</td></tr><tr><td>Employer 2</td><td>1</td></tr></table>";
    assertEquals(expected, display.result());
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

  private static Date today()
  {
    return new Date();
  }
}
