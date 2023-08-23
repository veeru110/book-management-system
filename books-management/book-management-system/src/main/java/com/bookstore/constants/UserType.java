package com.bookstore.constants;

import java.util.Set;

public enum UserType {
    //ADMIN Created by default while setting up application
    BUYER {
        @Override
        public Set<String> getAllowedActions() {
            return Set.of("READ");
        }
    }, SELLER {
        @Override
        public Set<String> getAllowedActions() {
            return Set.of("READ", "WRITE");
        }
    }, ADMIN {
        @Override
        public Set<String> getAllowedActions() {
            return Set.of("*");
        }
    };

    public abstract Set<String> getAllowedActions();


}
