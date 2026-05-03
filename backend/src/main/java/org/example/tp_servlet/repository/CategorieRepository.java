package org.example.tp_servlet.repository;

import jakarta.persistence.EntityManager;
import org.example.tp_servlet.Model.Categorie;
import org.example.tp_servlet.util.JpaUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategorieRepository {

    public Categorie save(Categorie categorie) {
        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();
        if (categorie.getId() == 0) {
            em.persist(categorie);
        } else {
            categorie = em.merge(categorie);
        }
        em.getTransaction().commit();
        em.close();
        return categorie;
    }

    public List<Categorie> findAll() {
        EntityManager em = JpaUtil.createEntityManager();
        List<Categorie> categories = em.createQuery("SELECT c FROM Categorie c", Categorie.class).getResultList();
        em.close();
        return categories;
    }

    public Optional<Categorie> findById(int id) {
        EntityManager em = JpaUtil.createEntityManager();
        Categorie categorie = em.find(Categorie.class, id);
        em.close();
        return Optional.ofNullable(categorie);
    }

    public void deleteById(int id) {
        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();
        Categorie categorie = em.find(Categorie.class, id);
        if (categorie != null) {
            em.remove(categorie);
        }
        em.getTransaction().commit();
        em.close();
    }
}
