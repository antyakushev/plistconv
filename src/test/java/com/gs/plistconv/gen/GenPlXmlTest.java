package com.gs.plistconv.gen;

import com.gs.plist4j.primitives.PlistPrimitive;
import com.gs.plist4j.primitives.PlistValue;
import com.gs.plist4j.xml.XmlPlistInput;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Kirill Chernyavskiy
 */
public class GenPlXmlTest {

    @Test
    public void actGeneratesXmlPlistStream() throws IOException {
        HashMap<String, PlistValue> wrap = new HashMap<>();
        wrap.put("TARGET", new PlistPrimitive(42));
        PipedInputStream input = new PipedInputStream();
        try (PipedOutputStream output = new PipedOutputStream(input)) {
            IOUtils.copy(new GenPlXml().act(new PlistPrimitive(wrap)), output);
        }
        assertThat(
            new XmlPlistInput(input).read().dictionary().get("TARGET").number().intValue(),
            is(equalTo(42))
        );
    }
}