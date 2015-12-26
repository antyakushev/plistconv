package com.gs.plistconv.proc;

import com.gs.plistconv.rq.RqFakeBody;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Kirill Chernyavskiy
 */
public class PcPlXmlTest {

    private static final String VALID_TARGET =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>_bool_false</key>\n" +
            "\t<false/>\n" +
            "</dict>\n" +
            "</plist>";

    @Test
    public void actWithValidPlistProcessAsPlistValue() throws IOException {
        assertThat(
            new PcPlXml().act(
                new RqFakeBody(
                    VALID_TARGET.getBytes()
                ).body()
            ).dictionary().get("_bool_false").bool(),
            is(false)
        );
    }

    @Test(expected = IOException.class)
    public void actWithCorruptedDataThrowsException() throws IOException {
        new PcPlXml().act(
            new RqFakeBody(
                new byte[]{2, 6, 1, 36, 14, 11}
            ).body()
        );
    }
}