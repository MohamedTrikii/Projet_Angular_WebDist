package org.example.tp_servlet;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.List;

/**
 * Ce "seeder" est exécuté automatiquement au démarrage du serveur (ex: Tomcat/GlassFish).
 * Il s'assure qu'il y a toujours un utilisateur "admin" s'il n'y en a aucun dans la BD.
 */
@WebListener
public class AppStartupSeeder implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Démarrage de l'application : Vérification de la base de données...");
        
        try {
            List<Utilisateur> users = UtilisateurDAO.findAll();
            
            // Si la base est vide (ou qu'on ne trouve pas l'admin)
            boolean adminExists = false;
            for (Utilisateur u : users) {
                if ("admin@admin.com".equals(u.getEmail())) {
                    adminExists = true;
                    break;
                }
            }
            
            if (!adminExists) {
                System.out.println("Aucun administrateur par défaut trouvé, création en cours (admin@admin.com)");
                Utilisateur admin = new Utilisateur();
                admin.setNom("Administrateur");
                admin.setPrenom("Système");
                admin.setEmail("admin@admin.com");
                admin.setPassword("admin");
                admin.setRole("ADMIN");
                
                UtilisateurDAO.ajouter(admin);
                System.out.println("L'utilisateur administrateur (admin@admin.com / admin) a été inséré dans la base MySQL.");
            } else {
                System.out.println("La base de données contient déjà un administrateur.");
            }
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'insertion de l'administrateur par défaut : " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Optionnel : fermer la connexion EntityManagerFactory à l'arrêt du serveur
    }
}
