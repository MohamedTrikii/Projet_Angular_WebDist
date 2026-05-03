package org.example.tp_servlet.repository;

import jakarta.persistence.EntityManager;
import org.example.tp_servlet.Model.Projet;
import org.example.tp_servlet.util.JpaUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjetRepository {

    public Projet save(Projet projet) {
        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();
        if (projet.getId() == 0) {
            em.persist(projet);
        } else {
            projet = em.merge(projet);
        }
        em.getTransaction().commit();
        em.close();
        return projet;
    }

    public List<Projet> findAll() {
        EntityManager em = JpaUtil.createEntityManager();
        List<Projet> projets = em.createQuery("SELECT p FROM Projet p", Projet.class).getResultList();
        em.close();
        return projets;
    }

    public Optional<Projet> findById(int id) {
        EntityManager em = JpaUtil.createEntityManager();
        Projet projet = em.find(Projet.class, id);
        em.close();
        return Optional.ofNullable(projet);
    }

    public void deleteById(int id) {
        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();
        Projet projet = em.find(Projet.class, id);
        if (projet != null) {
            em.remove(projet);
        }
        em.getTransaction().commit();
        em.close();
    }
}
