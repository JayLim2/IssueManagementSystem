package org.sergei.komarov.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "issue_types")
@NoArgsConstructor
@Data
public class IssueType {
    @Id
    private String title;
    private List<Issue> issues;
}
