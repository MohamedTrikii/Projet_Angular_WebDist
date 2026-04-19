@echo off
echo ==============================================================
echo Demarrage du projet (Frontend Angular + Backend JEE)
echo ==============================================================

echo [1/2] Preparation du Backend...
echo Assurez-vous d'avoir WAMP/XAMPP demarre avec MySQL port 3306!
echo Le Backend (WAR) est concu pour un serveur d'application type Tomcat 10+ ou GlassFish (supportant Jakarta EE 10 / Servlets 6)
echo Deployez le fichier backend/target/ROOT.war sur votre serveur.

echo.
echo [2/2] Demarrage du Frontend Angular...
cd frontend
start cmd /k "npm start"

echo.
echo Le Frontend devrait bientot etre disponible sur http://localhost:4200/
echo L'API s'attend a etre connectee sur http://localhost:8080/api/
pause
