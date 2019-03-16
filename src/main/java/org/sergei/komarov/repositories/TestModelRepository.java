package org.sergei.komarov.repositories;

import org.sergei.komarov.models.TestModel;
import org.springframework.data.repository.CrudRepository;

public interface TestModelRepository extends CrudRepository<TestModel, Integer> {
}