package com.project.daniel.error.exceptions;

import com.project.daniel.enums.CrudOperation;
import com.project.daniel.enums.EntityType;

public class EntityCrudException extends Exception{
    public EntityCrudException(final EntityType entityType, final CrudOperation operation) {
        super("Failed to " + operation + " entity of type: " + entityType);
    }
}
