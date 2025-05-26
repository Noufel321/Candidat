package com.example.master_app.web;

import com.example.master_app.entities.Candidat;
import com.example.master_app.enumes.Role;
import com.example.master_app.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@Controller
//@RequestMapping("/auth")

public class AuthController {


//    @Autowired
//    private UtilisateurService utilisateurService;
//
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("candidat", new Candidat());
//        return "auth/register";
//    }
//
//    @PostMapping("/register")
//    public String registerCandidat(@ModelAttribute Candidat candidat, Model model) {
//        try {
//            candidat.setRole(Role.CANDIDAT); // Assure que le rôle est bien défini
//            utilisateurService.createUtilisateur(candidat);
//            return "redirect:/auth/login";
//        } catch (Exception e) {
//            model.addAttribute("error", "Erreur lors de l'inscription : " + e.getMessage());
//            return "auth/register";
//        }
//    }
//
//    @GetMapping("/login")
//    public String showLoginForm(Model model) {
//        model.addAttribute("candidat", new Candidat());
//        return "auth/login";
//    }
//
//    @PostMapping("/login")
//    public String loginCandidat(@ModelAttribute Candidat candidat, Model model) {
//        Candidat user = (Candidat) utilisateurService.getUtilisateurByEmail(candidat.getEmail());
//
//        if (user != null && user.getMotDePasse().equals(candidat.getMotDePasse())) {
//            // Ajouter la session si nécessaire
//            return "redirect:/home";
//        } else {
//            model.addAttribute("error", "Email ou mot de passe incorrect");
//            return "auth/login";
//        }
//    }
}

