package com.example.master_app.web;

import com.example.master_app.entities.Candidat;
import com.example.master_app.entities.Entretien;
import com.example.master_app.entities.Recruteur;
import com.example.master_app.services.EntretienService;
import com.example.master_app.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/entretien")
public class EntretienController {

    @Autowired
    private EntretienService entretienService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping
    public String getAllEntretiens(Model model) {
        List<Entretien> entretiens = entretienService.getAllEntretiens();
        model.addAttribute("entretiens", entretiens);
        return "entretien/list";
    }

    @GetMapping("/new")
    public String showEntretienForm(Model model) {
        model.addAttribute("entretien", new Entretien());
        // Charger les candidats et recruteurs
        List<Candidat> candidats = utilisateurRepository.findAll()
                .stream()
                .filter(u -> u instanceof Candidat)
                .map(u -> (Candidat) u)
                .collect(Collectors.toList());
        List<Recruteur> recruteurs = utilisateurRepository.findAll()
                .stream()
                .filter(u -> u instanceof Recruteur)
                .map(u -> (Recruteur) u)
                .collect(Collectors.toList());
        if (candidats.isEmpty()) {
            model.addAttribute("error", "Aucun candidat trouvé dans la base de données.");
        }
        if (recruteurs.isEmpty()) {
            model.addAttribute("error", "Aucun recruteur trouvé dans la base de données.");
        }
        model.addAttribute("candidats", candidats);
        model.addAttribute("recruteurs", recruteurs);
        return "entretien/form";
    }






    @GetMapping("/mes-entretiens")
    public String getMesEntretiens(Model model) {
        // Récupérer l'utilisateur connecté
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;

        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            email = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        // Trouver le candidat par email
        Candidat candidat = (Candidat) utilisateurRepository.findByEmail(email).orElse(null);
        if (candidat == null) {
            model.addAttribute("error", "Candidat non trouvé.");
            return "redirect:/login";
        }

        // Chercher ses entretiens
        List<Entretien> mesEntretiens = entretienService.getEntretiensByCandidat(candidat);
        model.addAttribute("entretiens", mesEntretiens);
        return "candidat/mes_entretiens"; // page à créer
    }






    @PostMapping
    public String createEntretien(@ModelAttribute Entretien entretien, Model model) throws Exception {
        entretienService.createEntretien(entretien);
        return "redirect:/entretien";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Entretien entretien = entretienService.getEntretienById(id);
        if (entretien == null) {
            model.addAttribute("error", "Entretien non trouvé");
            return "redirect:/entretien";
        }
        model.addAttribute("entretien", entretien);
        // Charger les candidats et recruteurs
        List<Candidat> candidats = utilisateurRepository.findAll()
                .stream()
                .filter(u -> u instanceof Candidat)
                .map(u -> (Candidat) u)
                .collect(Collectors.toList());
        List<Recruteur> recruteurs = utilisateurRepository.findAll()
                .stream()
                .filter(u -> u instanceof Recruteur)
                .map(u -> (Recruteur) u)
                .collect(Collectors.toList());
        if (candidats.isEmpty()) {
            model.addAttribute("error", "Aucun candidat trouvé dans la base de données.");
        }
        model.addAttribute("candidats", candidats);
        model.addAttribute("recruteurs", recruteurs);
        return "entretien/form";
    }

    @PostMapping("/update/{id}")
    public String updateEntretien(@PathVariable int id, @ModelAttribute Entretien entretien) {
        entretienService.updateEntretien(id, entretien);
        return "redirect:/entretien";
    }

    @GetMapping("/delete/{id}")
    public String deleteEntretien(@PathVariable int id) {
        entretienService.deleteEntretien(id);
        return "redirect:/entretien";
    }
}