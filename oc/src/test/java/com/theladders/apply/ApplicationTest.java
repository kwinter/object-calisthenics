package com.theladders.apply;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.theladders.employer.Employer;
import com.theladders.job.application.display.StringWriterDisplay;
import com.theladders.job.ats.AtsJob;
import com.theladders.job.jreq.JReq;
import com.theladders.jobseeker.Jobseeker;
import com.theladders.jobseeker.Name;
import com.theladders.jobseeker.resume.Resume;
import com.theladders.jobseeker.resume.Resume.ValidResume;
import com.theladders.jobseeker.resume.Title;

public class ApplicationTest
{
  private static final String JOHNNYS_RESUME                     = "Johnny's resume";
  private static final String JOHNNYS_NAME                       = "Johnny";
  private static final String JOHNNYS_APPLICATION_WITH_RESUME    = applicationFor(today(), JOHNNYS_NAME, JOHNNYS_RESUME);
  private static final String JOHNNYS_APPLICATION_WITHOUT_RESUME = applicationFor(today(), JOHNNYS_NAME, "");

  @Test
  public void applyingToJReq()
  {
    Resume resume = new ValidResume(new Title(JOHNNYS_RESUME));
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = new Employer(new com.theladders.employer.Name("Employer 1"));
    JReq job = employer.createJreqWith(new com.theladders.job.Title("Sweet job"));

    jobseeker.applyTo(job).with(resume);

    StringWriterDisplay display = new StringWriterDisplay();
    job.displayApplicationsOn(display);
    assertEquals(JOHNNYS_APPLICATION_WITH_RESUME, display.result());
  }

  @Test
  public void applyingToAtsJob()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = new Employer(new com.theladders.employer.Name("Employer 1"));
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
    Employer employer = new Employer(new com.theladders.employer.Name("Employer 1"));
    AtsJob job = employer.createAtsJobWith(new com.theladders.job.Title("Sweet job"));

    jobseeker.save(job);

    StringWriterDisplay display = new StringWriterDisplay();
    jobseeker.displaySavedJobsOn(display);
    assertEquals(jobsDisplayFor(jobDisplayFor("Employer 1", "Sweet job")), display.result());
  }

  @Test
  public void jobseekersCanListAppliedJobs()
  {
    Jobseeker jobseeker = new Jobseeker(new Name(JOHNNYS_NAME));
    Employer employer = new Employer(new com.theladders.employer.Name("Employer 1"));
    JReq jreq = employer.createJreqWith(new com.theladders.job.Title("JReq"));
    employer.post(jreq);
    AtsJob atsJob = employer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    employer.post(atsJob);

    Resume resume = new ValidResume(new Title(JOHNNYS_RESUME));

    jobseeker.applyTo(jreq).with(resume);
    jobseeker.applyTo(atsJob);

    StringWriterDisplay display = new StringWriterDisplay();
    jobseeker.displayAppliedJobsOn(display);
    assertEquals(jobsDisplayFor(jobDisplayFor("Employer 1", "JReq"), jobDisplayFor("Employer 1", "ATS job")),
                 display.result());
  }

  @Test
  public void employersCanListJobs()
  {
    Employer employer = new Employer(new com.theladders.employer.Name("Employer 1"));

    JReq jreq = employer.createJreqWith(new com.theladders.job.Title("JReq"));
    employer.post(jreq);
    AtsJob atsJob = employer.createAtsJobWith(new com.theladders.job.Title("ATS job"));
    employer.post(atsJob);

    StringWriterDisplay display = new StringWriterDisplay();
    employer.displayJobsOn(display);
    assertEquals(jobsDisplayFor(jobDisplayFor("Employer 1", "JReq"), jobDisplayFor("Employer 1", "ATS job")),
                 display.result());
  }

  private static String applicationFor(Date date,
                                       String name,
                                       String resumeTitle)
  {
    return SimpleDateFormat.getDateInstance().format(date) + StringWriterDisplay.DELIMITER
           + name
           + StringWriterDisplay.DELIMITER
           + resumeTitle
           + StringWriterDisplay.NEW_LINE;
  }

  private static String jobsDisplayFor(String... jobs)
  {
    StringBuilder builder = new StringBuilder();
    for (String job : jobs)
    {
      builder.append(job).append(StringWriterDisplay.NEW_LINE);
    }
    return builder.toString();
  }

  private static String jobDisplayFor(String employer,
                                      String title)
  {
    return employer + StringWriterDisplay.DELIMITER + title;
  }

  private static Date today() {
    return new Date();
  }
}
