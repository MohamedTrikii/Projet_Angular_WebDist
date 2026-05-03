package org.example.tp_servlet.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.example.tp_servlet.Model.Utilisateur;
import org.example.tp_servlet.util.JpaUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UtilisateurRepository {

    public Utilisateur save(Utilisateur utilisateur) {
        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();
        if (utilisateur.getId() == 0) {
            em.persist(utilisateur);
        } else {
            utilisateur = em.merge(utilisateur);
        }
        em.getTransaction().commit();
        em.close();
        return utilisateur;
    }

    public List<Utilisateur> findAll() {
        EntityManager em = JpaUtil.createEntityManager();
        List<Utilisateur> users = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
        em.close();
        return users;
    }

    public Optional<Utilisateur> findById(int id) {
        EntityManager em = JpaUtil.createEntityManager();
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        em.close();
        return Optional.ofNullable(utilisateur);
    }

    public Optional<Utilisateur> findByEmail(String email) {
        EntityManager em = JpaUtil.createEntityManager();
        TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class);
        query.setParameter("email", email);
        try {
            Utilisateur utilisateur = query.getSingleResult();
            return Optional.of(utilisateur);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public void deleteById(int id) {
        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
        em.getTransaction().commit();
        em.close();
    }
}
