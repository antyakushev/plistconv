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
package com.gs.plistconv.fork;

import com.jcabi.aspects.Immutable;
import org.takes.Take;
import org.takes.facets.fork.FkParams;
import org.takes.facets.fork.FkWrap;
import org.takes.facets.fork.TkFork;

import javax.validation.constraints.NotNull;

/**
 * @author Kirill Chernyavskiy
 */
@Immutable
public final class FkInOut extends FkWrap {

    public FkInOut(@NotNull String inp, @NotNull String out, @NotNull Take take) {
        super(
            new FkParams(
                "inp",
                inp,
                new TkFork(
                    new FkParams(
                        "out",
                        out,
                        take
                    )
                )
            )
        );
    }
}
