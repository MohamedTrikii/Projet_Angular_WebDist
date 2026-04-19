package org.example.tp_servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class CategorieDAO {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp_pu");

    public static void ajouter(Categorie c) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        em.close();
    }

    public static List<Categorie> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Categorie> list = em.createQuery("SELECT c FROM Categorie c", Categorie.class).getResultList();
        em.close();
        return list;
    }

    public static void supprimer(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Categorie c = em.find(Categorie.class, id);
        if (c != null) {
            em.remove(c);
        }
        em.getTransaction().commit();
        em.close();
    }

    public static Categorie get(int id) {
        EntityManager em = emf.createEntityManager();
        Categorie c = em.find(Categorie.class, id);
        em.close();
        return c;
    }

    public static void modifier(Categorie c) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(c);
        em.getTransaction().commit();
        em.close();
    }
}
