package com.helger.maven.sch2xslt;

import java.io.File;

import org.junit.Test;

import com.helger.commons.io.resource.ClassPathResource;

import net.sf.saxon.Transform;

public class XlstSchematronTest
{

  private static final String TEST_XSLT_SCHEMATRON = "testXsltSchematron";

  /**
   * The classpath directory where the Schematron 2 XSLT files reside.
   */
  private static final String SCHEMATRON_DIRECTORY_XSLT2 = "schematron/20100414-xslt2/";

  /**
   * The class path to first XSLT to be applied.
   */
  private static final String XSLT2_STEP1 = SCHEMATRON_DIRECTORY_XSLT2 + "iso_dsdl_include.xsl";

  /**
   * The class path to second XSLT to be applied.
   */
  private static final String XSLT2_STEP2 = SCHEMATRON_DIRECTORY_XSLT2 + "iso_abstract_expand.xsl";

  /**
   * The class path to third and last XSLT to be applied.
   */
  private static final String XSLT2_STEP3 = SCHEMATRON_DIRECTORY_XSLT2 + "iso_svrl_for_xslt2.xsl";

  @Test
  public void testXsltSchematron () throws Exception
  {
    final Transform saxonTransform = new Transform ();

    final String sourcePath = new File ("src/test/resources/schematron/check-classifications.sch").getCanonicalPath ();
    final String step1Path = (new ClassPathResource (XSLT2_STEP1)).getAsFile ().getCanonicalPath ();
    final String step2Path = (new ClassPathResource (XSLT2_STEP2)).getAsFile ().getCanonicalPath ();
    final String step3Path = (new ClassPathResource (XSLT2_STEP3)).getAsFile ().getCanonicalPath ();
    final String outputPath = new File ("target/test/schematron-via-xslt/").getCanonicalPath ();

    final String [] step1Commands = { "-xsl:" + step1Path, "-s:" + sourcePath, "-o:" + outputPath + "/step1.sch" };
    final String [] step2Commands = { "-xsl:" +
                                      step2Path,
                                      "-s:" + outputPath + "/step1.sch",
                                      "-o:" + outputPath + "/step2.sch" };
    final String [] step3Commands = { "-xsl:" +
                                      step3Path,
                                      "-s:" + outputPath + "/step2.sch",
                                      "-o:" + outputPath + "/check-classifications.xslt" };

    saxonTransform.doTransform (step1Commands, TEST_XSLT_SCHEMATRON);
    saxonTransform.doTransform (step2Commands, TEST_XSLT_SCHEMATRON);
    saxonTransform.doTransform (step3Commands, TEST_XSLT_SCHEMATRON);
  }

}