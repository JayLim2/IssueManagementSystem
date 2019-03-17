package org.sergei.komarov.services;

import org.sergei.komarov.models.IssueType;
import org.sergei.komarov.repositories.IssueTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueTypesService {
    private final IssueTypesRepository issueTypesRepository;

    @Autowired
    public IssueTypesService(IssueTypesRepository issueTypesRepository) {
        this.issueTypesRepository = issueTypesRepository;
    }

    public List<IssueType> getAll() {
        return issueTypesRepository.findAll();
    }

    public void save(IssueType issueType) {
        issueTypesRepository.save(issueType);
    }

    public void saveAll(List<IssueType> issueTypes) {
        issueTypesRepository.saveAll(issueTypes);
    }
}
