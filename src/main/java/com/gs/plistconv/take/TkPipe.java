/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.gs.plistconv.take;

import com.gs.plistconv.gen.GenStream;
import com.gs.plistconv.proc.PcRequest;
import com.jcabi.aspects.Immutable;
import org.apache.commons.io.IOUtils;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqLengthAware;
import org.takes.rq.RqPrint;
import org.takes.rs.RsWithBody;
import org.takes.rs.RsWithStatus;

import javax.validation.constraints.NotNull;
import java.io.*;

/**
 * @author Kirill Chernyavskiy
 */
@Immutable
public final class TkPipe implements Take {

    private final PcRequest process;
    private final GenStream generate;

    public TkPipe(@NotNull PcRequest process, @NotNull GenStream generate) {
        this.process = process;
        this.generate = generate;
    }

    @Override
    public Response act(Request req) throws IOException {
        try {
            return new RsWithStatus(
                new RsWithBody(
                    generate.act(
                        process.act(
                            new BufferedInputStream(
                                new RqLengthAware(req).body()
                            )
                        )
                    )
                ),
                200
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
