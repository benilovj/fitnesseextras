package com.objectmentor.fitnesse.releases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.FileUtil;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class ReleaseFileTest {
  private File file;
  private long actualLastModified;
  private long actualSize;

  @Before
  public void setUp() {
    FileUtil.createFile("testfile.txt", "some text");
    file = new File("testfile.txt");
    actualLastModified = file.lastModified();
    actualSize = file.length();
  }

  @After
  public void tearDown() {
    FileUtil.deleteFile("testfile.txt");
  }

  @Test
  public void construction() {
    ReleaseFile releaseFile = new ReleaseFile("testfile.txt", 123,
      actualLastModified, 10
    );
    assertEquals("testfile.txt", releaseFile.filename);
    assertEquals(123, releaseFile.size);
    assertEquals(actualLastModified, releaseFile.lastModified);
    assertEquals(10, releaseFile.downloads);
  }

  @Test
  public void sizeGetsUpdatedIfModificationTimeIsDifferent() {
    ReleaseFile releaseFile = new ReleaseFile("testfile.txt", 123, 1234567890,
      10
    );
    assertEquals("testfile.txt", releaseFile.filename);
    assertEquals(actualSize, releaseFile.size);
    assertEquals(actualLastModified, releaseFile.lastModified);
    assertEquals(10, releaseFile.downloads);
  }

  @Test
  public void creationWithJustFilename() {
    ReleaseFile releaseFile = new ReleaseFile("testfile.txt");
    assertEquals("testfile.txt", releaseFile.filename);
    assertEquals(actualSize, releaseFile.size);
    assertEquals(actualLastModified, releaseFile.lastModified);
    assertEquals(0, releaseFile.downloads);
  }

  @Test
  public void getters() {
    ReleaseFile releaseFile = new ReleaseFile("testfile.txt");
    assertEquals("testfile.txt", releaseFile.getFilename());

    releaseFile.size = 12345;
    assertEquals("12345 bytes", releaseFile.getSize());

    releaseFile.downloads = 34;
    assertEquals("34", releaseFile.getDownloads());
  }

  @Test
  public void toStringTest() {
    ReleaseFile releaseFile = new ReleaseFile("testfile.txt");
    releaseFile.size = 123;
    releaseFile.lastModified = 876543210;
    releaseFile.downloads = 321;

    assertEquals("testfile.txt\t123\t876543210\t321", releaseFile.toString());
  }

  @Test
  public void parse() {
    ReleaseFile releaseFile = ReleaseFile.parse(".",
      "testfile.txt\t123\t" + actualLastModified + "\t321"
    );
    assertEquals("./testfile.txt", releaseFile.filename);
    assertEquals(123, releaseFile.size);
    assertEquals(321, releaseFile.downloads);
    assertEquals(actualLastModified, releaseFile.lastModified);
  }
}
