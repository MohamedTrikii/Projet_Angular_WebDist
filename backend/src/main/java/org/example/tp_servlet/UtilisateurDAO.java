package org.example.tp_servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class UtilisateurDAO {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp_pu");

    public static void ajouter(Utilisateur u) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        em.close();
    }

    public static List<Utilisateur> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Utilisateur> list = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
        em.close();
        return list;
    }

    public static void supprimer(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Utilisateur u = em.find(Utilisateur.class, id);
        if (u != null) {
            em.remove(u);
        }
        em.getTransaction().commit();
        em.close();
    }

    public static Utilisateur get(int id) {
        EntityManager em = emf.createEntityManager();
        Utilisateur u = em.find(Utilisateur.class, id);
        em.close();
        return u;
    }
    
    public static void modifier(Utilisateur u) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(u);
        em.getTransaction().commit();
        em.close();
    }
}
