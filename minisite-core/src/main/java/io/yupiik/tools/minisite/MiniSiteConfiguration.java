/*
 * Copyright (c) 2020 - Yupiik SAS - https://www.yupiik.com
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
package io.yupiik.tools.minisite;

import io.yupiik.tools.common.asciidoctor.AsciidoctorConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.asciidoctor.Asciidoctor;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class MiniSiteConfiguration {
    private Supplier<ClassLoader> actionClassLoader;
    private Path source;
    private Path target;
    private List<File> templateDirs;
    private Map<String, Object> attributes;
    private String title;
    private String description;
    private String logoText;
    private String logo;
    private String indexText;
    private String indexSubTitle;
    private String copyright;
    private String linkedInCompany;
    private String customHead;
    private String customScripts;
    private String customMenu;
    private String siteBase;
    private boolean useDefaultAssets;
    private String searchIndexName;
    private boolean generateBlog;
    private int blogPageSize;
    private boolean generateIndex;
    private boolean generateSiteMap;
    private List<String> templatePrefixes;
    private boolean templateAddLeftMenu;
    private List<String> templateSuffixes;
    private List<PreAction> preActions;
    private boolean skipRendering;
    private String customGems;
    private String projectVersion;
    private String projectName;
    private String projectArtifactId;
    private List<String> requires;
    private AsciidoctorConfiguration asciidoctorConfiguration;
    private BiFunction<AsciidoctorConfiguration, Function<Asciidoctor, Object>, Object> asciidoctorPool;
    private boolean reverseBlogOrder;
    private boolean addIndexRegistrationPerCategory;
    private boolean skipIndexTitleDocumentationText;
    private String logoSideText;
    private Map<String, BlogCategoryConfiguration> blogCategoriesCustomizations;
    private Map<String, String> templateExtensionPoints;
    private boolean injectYupiikTemplateExtensionPoints;

    public void fixConfig() {
        if (requires == null) { // ensure we don't load reveal.js by default since we disabled extraction of gems
            requires = List.of();
        }
        if (blogCategoriesCustomizations == null) {
            blogCategoriesCustomizations = Map.of();
        }

        // ensure writable
        templateExtensionPoints = new HashMap<>(templateExtensionPoints == null ? Map.of() : templateExtensionPoints);
        // ensure there are defaults for built-in custom extension points (empty for not yupiik case)
        templateExtensionPoints.putIfAbsent("socialLinks", (injectYupiikTemplateExtensionPoints ? "" +
                // add github
                "<li class=\"list-inline-item\"><a href=\"https://www.github.com/{{linkedInCompany}}/\"><i class=\"fab fa-github fa-fw\"></i></a></li>" +
                "" : "") +
                // always linkedin
                "<li class=\"list-inline-item\"><a href=\"https://www.linkedin.com/company/{{linkedInCompany}}/\"><i class=\"fab fa-linkedin fa-fw\"></i></a></li>");
        templateExtensionPoints.putIfAbsent("copyrightLine", "" +
                "<small class=\"copyright\">{{copyright}}</small>" + (injectYupiikTemplateExtensionPoints ? "" +
                // add terms of service and privacy policy
                " | <a href=\"https://www.{{linkedInCompany}}.com/terms-of-service/\">Terms of service</a> | " +
                "<a href=\"https://www.{{linkedInCompany}}.com/privacy-policy/\">Privacy policy</a>" : ""));
    }

    @Data
    public static class BlogCategoryConfiguration {
        private int order = -1;
        private String homePageName;
        private String icon;
        private String description;
    }
}
