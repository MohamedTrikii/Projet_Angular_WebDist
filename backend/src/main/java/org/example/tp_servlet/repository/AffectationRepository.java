package org.example.tp_servlet.repository;

import jakarta.persistence.EntityManager;
import org.example.tp_servlet.Model.Affectation;
import org.example.tp_servlet.util.JpaUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AffectationRepository {

    public Affectation save(Affectation affectation) {
        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();
        if (affectation.getId() == 0) {
            em.persist(affectation);
        } else {
            affectation = em.merge(affectation);
        }
        em.getTransaction().commit();
        em.close();
        return affectation;
    }

    public List<Affectation> findAll() {
        EntityManager em = JpaUtil.createEntityManager();
        List<Affectation> affectations = em.createQuery("SELECT a FROM Affectation a", Affectation.class).getResultList();
        em.close();
        return affectations;
    }

    public Optional<Affectation> findById(int id) {
        EntityManager em = JpaUtil.createEntityManager();
        Affectation affectation = em.find(Affectation.class, id);
        em.close();
        return Optional.ofNullable(affectation);
    }

    public void deleteById(int id) {
        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();
        Affectation affectation = em.find(Affectation.class, id);
        if (affectation != null) {
            em.remove(affectation);
        }
        em.getTransaction().commit();
        em.close();
    }
}
