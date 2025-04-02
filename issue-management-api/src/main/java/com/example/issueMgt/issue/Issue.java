/*
 * Copyright (c) 2025, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
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

package com.example.issueMgt.issue;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;

public record Issue(@Id Long id,
                   @NotNull(message = "name is required") @Pattern(regexp = "^[a-zA-Z ]+$", message = "name must be a string") String name,
                   @NotNull(message = "description is required") @Pattern(regexp = "^[a-zA-Z ]+$", message = "description must be a string") String description,
                   @NotNull(message = "issue link is required") @URL(message = "Issue link must be a URL") String issueLink) {

    public Issue(
            Long id,
            String name,
            String description,
            String issueLink
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.issueLink = issueLink;
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String issueLink() {
        return issueLink;
    }

    public Issue updateWith(Issue item) {
        return new Issue(
                this.id,
                item.name,
                item.description,
                item.issueLink
        );
    }
}

