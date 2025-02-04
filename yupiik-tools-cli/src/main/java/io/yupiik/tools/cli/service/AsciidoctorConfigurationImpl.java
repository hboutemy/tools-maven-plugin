/*
 * Copyright (c) 2020 - 2023 - Yupiik SAS - https://www.yupiik.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.yupiik.tools.cli.service;

import io.yupiik.tools.common.asciidoctor.AsciidoctorConfiguration;
import lombok.RequiredArgsConstructor;

import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class AsciidoctorConfigurationImpl implements AsciidoctorConfiguration {
    private final Path gems;
    private final String customGems;
    private final List<String> requires;
    private final PrintStream stdout;
    private final PrintStream stderr;

    @Override
    public Path gems() {
        return gems;
    }

    @Override
    public String customGems() {
        return customGems;
    }

    @Override
    public List<String> requires() {
        return requires;
    }

    @Override
    public Consumer<String> info() {
        return s -> stdout.println("[INFO] " + s);
    }

    @Override
    public Consumer<String> debug() {
        return s -> {
        };
    }

    @Override
    public Consumer<String> warn() {
        return s -> stdout.println("[WARNING] " + s);
    }

    @Override
    public Consumer<String> error() {
        return s -> stderr.println("[ERROR] " + s);
    }
}
