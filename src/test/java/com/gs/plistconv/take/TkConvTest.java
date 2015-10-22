package com.gs.plistconv.take;

import com.gs.plist4j.primitives.PlistPrimitive;
import com.gs.plistconv.gen.GenStream;
import com.gs.plistconv.matchers.RsMatchers;
import com.gs.plistconv.proc.PcStream;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.takes.rq.RqBytes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Kirill Chernyavskiy
 */
public class TkConvTest {

    private static final PcStream TEST_PROC = stream -> new PlistPrimitive(stream.read());
    private static GenStream TEST_GEN = source -> new ByteArrayInputStream(new byte[]{source.number().byteValue()});

    @Test
    public void actProcessRequestAndGenerateResponse() throws IOException {
        MatcherAssert.assertThat(
            new TkConv(TEST_PROC, TEST_GEN)
                .act(new RqBytes(new ArrayList<>(), new byte[]{42})),
            RsMatchers.hasEqualsBody(new byte[]{42})
        );
    }
}