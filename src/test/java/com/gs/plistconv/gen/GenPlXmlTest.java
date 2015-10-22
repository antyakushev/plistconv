package com.gs.plistconv.gen;

import com.gs.plist4j.primitives.PlistPrimitive;
import com.gs.plist4j.primitives.PlistValue;
import com.gs.plist4j.xml.XmlPlistFile;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author Kirill Chernyavskiy
 */
public class GenPlXmlTest {

    private File testFile;

    @Before
    public void setUp() throws IOException {
        testFile = File.createTempFile("GenPlXmlTest-", ".tmp");
    }

    @After
    public void tearDown() {
        testFile.delete();
    }

    @Test
    public void actGeneratesXmlPlistStream() throws IOException {
        HashMap<String, PlistValue> wrap = new HashMap<>();
        wrap.put("TARGET", new PlistPrimitive(42));
        try (OutputStream os = new FileOutputStream(testFile)) {
            IOUtils.copy(new GenPlXml().act(new PlistPrimitive(wrap)), os);
        }
        assertThat(
            new XmlPlistFile(testFile).read().dictionary().get("TARGET").number().intValue(),
            equalTo(42)
        );
    }
}