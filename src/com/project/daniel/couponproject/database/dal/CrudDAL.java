package com.project.daniel.couponproject.database.dal;

import com.project.daniel.couponproject.error.exceptions.EntityCrudException;

import java.util.List;

public interface CrudDAL<ID, Entity> {
    ID create(final Entity entity) throws EntityCrudException;
    Entity read(final ID id) throws EntityCrudException;
    void update(final Entity entity) throws EntityCrudException;
    void delete(final ID id) throws EntityCrudException;
    List<Entity> readAll() throws EntityCrudException;
}