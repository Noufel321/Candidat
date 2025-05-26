package com.example.master_app.Repository;




import com.example.master_app.entities.Utilisateur;
import com.example.master_app.enumes.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    // Requête personnalisée pour trouver un utilisateur par email (utile pour l'authentification)
   // Optional<Utilisateur> findByEmail(String email);
    Optional<Utilisateur> findByEmail(String email);

    // Requête personnalisée pour trouver tous les utilisateurs d'un rôle spécifique
    List<Utilisateur> findByRole(Role role);


}