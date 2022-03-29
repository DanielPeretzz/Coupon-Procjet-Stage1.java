package com.project.daniel.couponproject.error.exceptions;

import com.project.daniel.couponproject.enums.CrudOperation;
import com.project.daniel.couponproject.enums.EntityType;

public class EntityCrudException extends ApplicationException{
    public EntityCrudException(final EntityType entityType, final CrudOperation operation) {
        super("Failed to " + operation + " entity of type: " + entityType);
    }
}
