package com.example.master_app.services;

import com.example.master_app.Repository.EntretienRepository;
import com.example.master_app.entities.Candidat;
import com.example.master_app.entities.Entretien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntretienService {

    @Autowired
    private EntretienRepository entretienRepository;

    public List<Entretien> getEntretiensByCandidat(Candidat candidat) {
        return entretienRepository.findByCandidat(candidat);
    }

    public List<Entretien> getEntretiensByCandidatId(int candidatId) {
        return entretienRepository.findAll()
                .stream()
                .filter(e -> e.getCandidat().getId() == candidatId)
                .collect(Collectors.toList());
    }


    public List<Entretien> getAllEntretiens() {
        return entretienRepository.findAll();
    }

    public Entretien getEntretienById(int id) {
        return entretienRepository.findById(id).orElse(null);
    }

    public Entretien createEntretien(Entretien entretien) throws Exception {
        return entretienRepository.save(entretien);
    }

    public Entretien updateEntretien(int id, Entretien entretien) {
        Entretien existingEntretien = entretienRepository.findById(id).orElse(null);
        if (existingEntretien == null) {
            throw new IllegalArgumentException("Entretien avec l'ID " + id + " non trouvé");
        }
        existingEntretien.setDate(entretien.getDate());
        existingEntretien.setLieu(entretien.getLieu());
        existingEntretien.setStatut(entretien.getStatut());
        existingEntretien.setRecruteur(entretien.getRecruteur());
        existingEntretien.setCandidat(entretien.getCandidat());
        existingEntretien.setCompteRendu(entretien.getCompteRendu());
        return entretienRepository.save(existingEntretien);
    }

    public void deleteEntretien(int id) {
        Entretien existingEntretien = entretienRepository.findById(id).orElse(null);
        if (existingEntretien == null) {
            throw new IllegalArgumentException("Entretien avec l'ID " + id + " non trouvé");
        }
        entretienRepository.deleteById(id);
    }
}