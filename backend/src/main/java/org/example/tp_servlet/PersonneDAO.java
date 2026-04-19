package org.example.tp_servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class PersonneDAO {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp_pu");

    public static void ajouter(Personne p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        em.close();
    }

    public static List<Personne> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Personne> list = em.createQuery("SELECT p FROM Personne p", Personne.class).getResultList();
        em.close();
        return list;
    }

    public static void supprimer(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Personne p = em.find(Personne.class, id);
        if (p != null) {
            em.remove(p);
        }
        em.getTransaction().commit();
        em.close();
    }

    public static Personne get(int id) {
        EntityManager em = emf.createEntityManager();
        Personne p = em.find(Personne.class, id);
        em.close();
        return p;
    }

    public static void modifier(Personne p) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
        em.close();
    }
}
