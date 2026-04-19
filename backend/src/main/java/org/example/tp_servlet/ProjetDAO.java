package org.example.tp_servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class ProjetDAO {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp_pu");

    public static void ajouter(Projet p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        em.close();
    }

    public static List<Projet> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Projet> list = em.createQuery("SELECT p FROM Projet p", Projet.class).getResultList();
        em.close();
        return list;
    }

    public static void supprimer(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Projet p = em.find(Projet.class, id);
        if (p != null) {
            em.remove(p);
        }
        em.getTransaction().commit();
        em.close();
    }

    public static Projet get(int id) {
        EntityManager em = emf.createEntityManager();
        Projet p = em.find(Projet.class, id);
        em.close();
        return p;
    }

    public static void modifier(Projet p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
        em.close();
    }
}
