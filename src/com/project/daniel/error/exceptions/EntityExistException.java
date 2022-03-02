package com.project.daniel.error.exceptions;

import com.project.daniel.enums.EntityType;

public class EntityExistException extends  Exception{

    public EntityExistException(EntityType entityType){

        super("This " + entityType + " is already exists!");
    }
}
