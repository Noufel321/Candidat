package com.example.master_app.services;

import com.example.master_app.Repository.UtilisateurRepository;
import com.example.master_app.entities.Candidat;
import com.example.master_app.entities.Recruteur;
import com.example.master_app.entities.RH;
import com.example.master_app.entities.Utilisateur;
import com.example.master_app.enumes.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur getUtilisateurById(int id) {
        return utilisateurRepository.findById(id).orElse(null);
    }



    public Optional<Utilisateur> getUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }


    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        // Créer la bonne sous-classe en fonction du rôle
        Utilisateur utilisateurToSave;
        if (utilisateur.getRole() == Role.CANDIDAT) {
            utilisateurToSave = new Candidat();
        } else if (utilisateur.getRole() == Role.RECRUTEUR) {
            utilisateurToSave = new Recruteur();
        } else if (utilisateur.getRole() == Role.RH) {
            utilisateurToSave = new RH();
        } else {
            throw new IllegalArgumentException("Rôle non supporté : " + utilisateur.getRole());
        }

        // Copier les champs de base
        utilisateurToSave.setNom(utilisateur.getNom());
        utilisateurToSave.setEmail(utilisateur.getEmail());
        utilisateurToSave.setMotDePasse(utilisateur.getMotDePasse());
        utilisateurToSave.setRole(utilisateur.getRole());

        // Persister l'objet
        return utilisateurRepository.save(utilisateurToSave);
    }

    public Utilisateur updateUtilisateur(int id, Utilisateur utilisateur) {
        Utilisateur existingUtilisateur = utilisateurRepository.findById(id).orElse(null);
        if (existingUtilisateur == null) {
            throw new IllegalArgumentException("Utilisateur avec l'ID " + id + " non trouvé");
        }
        existingUtilisateur.setEmail(utilisateur.getEmail());
        existingUtilisateur.setMotDePasse(utilisateur.getMotDePasse());
        existingUtilisateur.setNom(utilisateur.getNom());
        existingUtilisateur.setRole(utilisateur.getRole());
        return utilisateurRepository.save(existingUtilisateur);
    }

    public void deleteUtilisateur(int id) {
        Utilisateur existingUtilisateur = utilisateurRepository.findById(id).orElse(null);
        if (existingUtilisateur == null) {
            throw new IllegalArgumentException("Utilisateur avec l'ID " + id + " non trouvé");
        }
        utilisateurRepository.deleteById(id);
    }
}