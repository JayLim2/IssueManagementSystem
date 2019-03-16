package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//@Entity
//@Table(name = "issue_types")
@NoArgsConstructor
@Data
public class IssueType {
    //@Id
    private String title;
    private List<Issue> issues;
}
