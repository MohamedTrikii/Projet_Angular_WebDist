package org.example.tp_servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class AffectationDAO {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp_pu");

    public static void ajouter(Affectation a) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        em.close();
    }

    public static List<Affectation> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Affectation> list = em.createQuery("SELECT a FROM Affectation a", Affectation.class).getResultList();
        em.close();
        return list;
    }

    public static void supprimer(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Affectation a = em.find(Affectation.class, id);
        if (a != null) {
            em.remove(a);
        }
        em.getTransaction().commit();
        em.close();
    }

    public static Affectation get(int id) {
        EntityManager em = emf.createEntityManager();
        Affectation a = em.find(Affectation.class, id);
        em.close();
        return a;
    }

    public static void modifier(Affectation a) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(a);
        em.getTransaction().commit();
        em.close();
    }
}
