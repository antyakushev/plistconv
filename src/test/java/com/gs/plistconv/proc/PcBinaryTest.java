package com.gs.plistconv.proc;

import com.gs.plist4j.primitives.PlistValue;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Kirill Chernyavskiy
 */
public class PcBinaryTest {

    private static final String TARGET1 =
        "YnBsaXN0MDDYAQIDBAUGBwgJDQ4PEBYMC1ZfYXJyYXlVX2RhdGFeX3N0cmluZ19xd2Vxd2VbX2Jv" +
            "b2xfZmFsc2VVX2RpY3RYX251bV80LjJaX2Jvb2xfdHJ1ZVdfbm9tXzQyowoLDFNxd2UQKglLAQER" +
            "EBABAQEBAQFWcXdlcXdlCNMREhMLFAxZX2l0ZW1fbnVtXF9pdGVtX3N0cmluZ1pfaXRlbV9ib29s" +
            "VnF3ZXF3ZQlTNC4yCQgZICY1QUdQW2Nna21ueoGCiZOgq7KztwAAAAAAAAEBAAAAAAAAABgAAAAA" +
            "AAAAAAAAAAAAAAC4";

    private static PlistValue readViaProc(byte[] data) throws IOException {
        return new PcBinary().act(
            new ByteArrayInputStream(
                Base64.getDecoder().decode(data)
            )
        );
    }

    @Test
    public void actWithValidPlistProcessAsPlistValue() throws IOException {
        Map<String, PlistValue> target = readViaProc(TARGET1.getBytes()).dictionary();
        assertThat(
            target.size(),
            is(equalTo(8))
        );
        assertThat(
            target.get("_string_qweqwe").string(),
            is(equalTo("qweqwe"))
        );
    }

    @Test(expected = IOException.class)
    public void actWithCorruptedDataThrowsException() throws IOException {
        readViaProc(
            Base64.getEncoder().encode(
                new byte[]{1, 2, 3, 4}
            )
        );
    }
}