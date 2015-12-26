package com.gs.plistconv.gen;

import com.gs.plist4j.binary.BinaryPlistInput;
import com.gs.plist4j.primitives.PlistPrimitive;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Kirill Chernyavskiy
 */
public class GenBinaryTest {

    @Test
    public void actGeneratesBinaryPlistStream() throws IOException {
        PipedInputStream input = new PipedInputStream();
        try (PipedOutputStream output = new PipedOutputStream(input)) {
            IOUtils.copy(new GenBinary().act(new PlistPrimitive(42)), output);
        }
        assertThat(
            new BinaryPlistInput(input).read().number().intValue(),
            is(equalTo(42))
        );
    }
}