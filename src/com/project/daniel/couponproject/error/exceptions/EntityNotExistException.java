package com.project.daniel.couponproject.error.exceptions;

import com.project.daniel.couponproject.enums.EntityType;

public class EntityNotExistException extends ApplicationException {

    public EntityNotExistException(EntityType entityType){
        super("This " + entityType + " is not exists!");
    }

}
