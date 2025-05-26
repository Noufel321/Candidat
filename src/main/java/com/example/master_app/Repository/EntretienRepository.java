package com.example.master_app.Repository;


import com.example.master_app.entities.Candidat;
import com.example.master_app.entities.Entretien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntretienRepository extends JpaRepository<Entretien, Integer> {
    // Requête personnalisée pour trouver tous les entretiens d'un recruteur
    List<Entretien> findByRecruteurId(int recruteurId);

    // Requête personnalisée pour trouver tous les entretiens d'un candidat
    List<Entretien> findByCandidatId(int candidatId);

    List<Entretien> findByCandidat(Candidat candidat);

}