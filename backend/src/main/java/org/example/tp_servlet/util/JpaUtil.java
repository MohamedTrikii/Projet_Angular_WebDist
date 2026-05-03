package org.example.tp_servlet.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("tp_pu");

    private JpaUtil() {
    }

    public static EntityManager createEntityManager() {
        return EMF.createEntityManager();
    }
}
