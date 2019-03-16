package org.sergei.komarov.services;

import org.sergei.komarov.models.TestModel;
import org.sergei.komarov.repositories.TestModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestModelsService {
    private TestModelRepository testModelRepository;

    @Autowired
    public TestModelsService(TestModelRepository testModelRepository) {
        this.testModelRepository = testModelRepository;
        System.err.println("AAA");
    }

    public TestModel save(TestModel model) {
        return testModelRepository.save(model);
    }

    public Iterable<TestModel> getAll() {
        return testModelRepository.findAll();
    }
}
