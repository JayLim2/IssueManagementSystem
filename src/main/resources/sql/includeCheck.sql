ALTER TABLE issue_priorities
  ADD CHECK (issue_priorities.priority_title <> '');
