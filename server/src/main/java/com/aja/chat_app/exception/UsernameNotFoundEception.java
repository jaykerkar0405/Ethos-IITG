package com.aja.chat_app.exception;

public class UsernameNotFoundEception extends RuntimeException {

    public UsernameNotFoundEception(String id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' does not exist in our records");
}

}
