package com.gs.plistconv.gen;

import com.gs.plist4j.primitives.PlistPrimitive;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Kirill Chernyavskiy
 */
public class GenBinaryTest {

    private File testFile;

    @Before
    public void setUp() throws IOException {
        testFile = File.createTempFile("GenBinaryTest-", ".tmp");
    }

    @After
    public void tearDown() {
        testFile.delete();
    }

    //    @Test
    //FIXME
    public void actGeneratesBinaryPlistStream() throws IOException {
        try (OutputStream os = new FileOutputStream(testFile)) {
            IOUtils.copy(new GenBinary().act(new PlistPrimitive(42)), os);
        }
//        assertThat(
//            new BinaryPlistFile(testFile).read().number().intValue(),
//            equalTo(42)
//        );
    }
}