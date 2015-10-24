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

import org.takes.facets.fallback.TkFallback;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.misc.Opt;
import org.takes.rq.RqHref;
import org.takes.rs.RsText;
import org.takes.rs.RsWithStatus;
import org.takes.tk.TkWrap;

/**
 * @author Kirill Chernyavskiy
 */
final class TkMain extends TkWrap {

    public TkMain() {
        super(
            new TkFallback(
                new TkFork(
                    new FkRegex(
                        "^/convert",
                        new TkConvert()
                    )
                ),
                fail -> new Opt.Single<>(
                    new RsWithStatus(
                        new RsText(
                            String.format(
                                "'%s' not found",
                                new RqHref.Base(fail).href().path()
                            )
                        ),
                        404
                    )
                )
            )
        );
    }
}
