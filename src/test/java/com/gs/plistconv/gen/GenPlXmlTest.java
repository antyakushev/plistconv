package com.gs.plistconv.gen;

import com.gs.plist4j.primitives.PlistPrimitive;
import com.gs.plist4j.primitives.PlistValue;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;

/**
 * @author Kirill Chernyavskiy
 */
public class GenPlXmlTest {

//    @Test
    //FIXME
    public void actGeneratesXmlPlistStream() throws IOException {
        HashMap<String, PlistValue> wrap = new HashMap<>();
        wrap.put("TARGET", new PlistPrimitive(42));
        PipedInputStream pinput = new PipedInputStream();
        PipedOutputStream pout = new PipedOutputStream(pinput);
        ;
//        assertThat(
//            new XmlPlistFile(testFile).read().dictionary().get("TARGET").number().intValue(),
//            equalTo(42)
//        );
    }
}