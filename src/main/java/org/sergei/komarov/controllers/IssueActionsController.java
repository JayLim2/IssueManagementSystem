package org.sergei.komarov.controllers;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.CommentWrapper;
import org.sergei.komarov.models.Issue;
import org.sergei.komarov.models.ServiceComment;
import org.sergei.komarov.services.IssueActionsService;
import org.sergei.komarov.services.IssuesService;
import org.sergei.komarov.services.UsersService;
import org.sergei.komarov.utils.Validators;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/issueActions")
@AllArgsConstructor
public class IssueActionsController {

    private final IssuesService issuesService;
    private final IssueActionsService issueActionsService;
    private final UsersService usersService;

    @PostMapping("/add")
    public Map<String, Object> addComment(int issueId, CommentWrapper commentWrapper) {
        Map<String, Object> attrs = new HashMap<>();

        Issue issue = issuesService.getById(issueId);
        if (issue == null) {
            return attrs;
        }

        String comment = Optional.ofNullable(commentWrapper.getComment()).orElse("").trim();
        ;
        String message = Validators.validateComment(comment);

        if (message == null) {
            issueActionsService.createAndSave(
                    issue,
                    usersService.getCurrentUser().getEmployee(),
                    ServiceComment.POSTED_COMMENT,
                    comment
            );
            issue.setUpdatedDateTime(LocalDateTime.now());
            issuesService.save(issue);
            attrs.put("info", "Комментарий опубликован.");
        } else {
            attrs.put("error", message);
        }

        return attrs;
    }
}
