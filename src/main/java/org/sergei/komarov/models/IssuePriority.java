package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//@Entity
//@Table(name = "issue_priorities")
@NoArgsConstructor
@Data
public class IssuePriority {
    //@Id
    private String priorityTitle;
    private List<Issue> issues;
}