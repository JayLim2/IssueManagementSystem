package org.sergei.komarov.services;

import lombok.AllArgsConstructor;
import org.sergei.komarov.models.Issue;
import org.sergei.komarov.repositories.IssuesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IssuesService implements JpaService<Issue, Integer> {

    private final IssuesRepository issuesRepository;

    @Override
    public List<Issue> getAll() {
        return issuesRepository.findAll();
    }

    @Override
    public Issue getById(Integer id) {
        return issuesRepository.getOne(id);
    }

    @Override
    public boolean isExistsById(Integer id) {
        return issuesRepository.existsById(id);
    }

    @Override
    public void save(Issue entity) {
        issuesRepository.save(entity);
    }

    @Override
    public void saveAll(Iterable<Issue> iterable) {
        issuesRepository.saveAll(iterable);
    }

    @Override
    public void delete(Issue entity) {
        issuesRepository.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        issuesRepository.deleteById(id);
    }
}
