package com.project.daniel.error.exceptions;

import com.project.daniel.enums.EntityType;

public class EntityNotExistException extends Exception {

    public EntityNotExistException(EntityType entityType){
        super("This " + entityType + " is not exists!");
    }

}
