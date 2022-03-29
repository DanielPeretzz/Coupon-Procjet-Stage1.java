package com.project.daniel.couponproject.database.dal;

import com.project.daniel.couponproject.error.exceptions.EntityCrudException;

public abstract class UserDAL <ID,Entity> implements CrudDAL<ID,Entity> {
    public abstract Entity readByEmail(final String email) throws EntityCrudException;
    public abstract Entity readById(final Long id) throws EntityCrudException;
    public abstract Entity readByName(final String name) throws EntityCrudException;

    public boolean isExistsByEmail(final String email) throws EntityCrudException {
        return readByEmail(email) != null;
    }
    public boolean isExistsById(final Long id) throws EntityCrudException {
        return readById(id) != null;
    }
    public boolean isExistsByName(final String name) throws EntityCrudException {
        return readByName(name) != null;
    }
}
