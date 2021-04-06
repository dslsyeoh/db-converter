/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.repository;

import dsl.db.converter.domain.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("testRepository")
public interface TestRepository extends JpaRepository<TestEntity, Long>
{
}
