/*
 * Author Steven Yeoh
 * Copyright (c) 2021. All rights reserved.
 */

package dsl.db.converter.handler;

import dsl.db.converter.domain.TestEntity;
import dsl.db.converter.object.TestObject;
import dsl.db.converter.repository.TestRepository;
import dsl.db.converter.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("testService")
public class TestServiceHandler implements TestService
{
    @Autowired
    private TestRepository repository;

    @Override
    public void list()
    {
        List<TestEntity> entities = repository.findAll();

        List<TestObject> objects = entities.stream().map(entity -> {
            TestObject object = new TestObject();
            object.setId(entity.getId());
            object.setColInt(entity.getColInt());
            object.setColDouble(entity.getColDouble());
            object.setColChar(entity.getColChar());
            object.setColString(entity.getColString());
            object.setColLongText(entity.getColLongText());
            object.setColBoolean(entity.isColBoolean());
            object.setColTimestamp(entity.getColTimestamp());
            object.setColDate(entity.getColDate());
            object.setColInstant(entity.getColInstant());
            object.setColEnum(entity.getColEnum());
            object.setColBlob(entity.getColBlob());
            return object;
        }).collect(Collectors.toList());

        objects.forEach(System.out::println);
    }
}
