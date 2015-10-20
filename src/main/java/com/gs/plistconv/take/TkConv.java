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
import com.gs.plistconv.proc.PcStream;
import com.jcabi.aspects.Immutable;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.RsWithBody;
import org.takes.rs.RsWithStatus;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * @author Kirill Chernyavskiy
 */
@Immutable
public final class TkConv implements Take {

    private final PcStream process;
    private final GenStream generate;

    public TkConv(@NotNull PcStream process, @NotNull GenStream generate) {
        this.process = process;
        this.generate = generate;
    }

    @Override
    public Response act(Request req) throws IOException {
        return new RsWithStatus(
            new RsWithBody(
                generate.act(
                    process.act(
                        req.body()
                    )
                )
            ),
            200
        );
    }
}
