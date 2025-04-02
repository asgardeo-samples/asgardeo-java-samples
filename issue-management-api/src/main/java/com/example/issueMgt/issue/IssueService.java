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

import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableMapRepositories
public class IssueService {

    private final CrudRepository<Issue, Long> repository;

    public IssueService(CrudRepository<Issue, Long> repository) {
        this.repository = repository;
        this.repository.saveAll(defaultIssues());
    }

    private static List<Issue> defaultIssues() {
        return List.of(
                new Issue(1L, "Submit button alignment issue", "Submit button in user creation modal is not align properly", "https://github.com/issueMgt/issues/1"),
                new Issue(2L, "JWT token doesn't contain required claims", "JWT token doesn't contain the aud claim", "https://github.com/issueMgt/issues/2")
        );
    }

    public List<Issue> findAll() {
        List<Issue> list = new ArrayList<>();
        Iterable<Issue> Issues = repository.findAll();
        Issues.forEach(list::add);
        return list;
    }

    public Optional<Issue> find(Long id) {
        return repository.findById(id);
    }

    public Issue create(Issue Issue) {
        // To ensure the Issue ID remains unique,
        // use the current timestamp.
        Issue copy = new Issue(
                new Date().getTime(),
                Issue.name(),
                Issue.description(),
                Issue.issueLink()
        );
        return repository.save(copy);
    }

    public Optional<Issue> update(Long id, Issue newIssue) {
        // Only update an Issue if it can be found first.
        return repository.findById(id)
                .map(oldIssue -> {
                    Issue updated = oldIssue.updateWith(newIssue);
                    return repository.save(updated);
                });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
