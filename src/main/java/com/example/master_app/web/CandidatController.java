package com.example.master_app.web;

import com.example.master_app.entities.Candidat;
import com.example.master_app.entities.Entretien;
import com.example.master_app.enumes.Role;
import com.example.master_app.Repository.UtilisateurRepository;
import com.example.master_app.services.EntretienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/candidat")
public class CandidatController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntretienService entretienService;


    @Autowired
    private AuthenticationManager authenticationManager;

    // Affichage du formulaire d'inscription
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("candidat", new Candidat());
        return "register_candidat";
    }

    // Traitement du formulaire d'inscription
    @PostMapping("/register")
    public String registerCandidat(@ModelAttribute Candidat candidat,
                                   @RequestParam("cvFile") MultipartFile cvFile) {
        // Mot de passe brut avant encodage
        String rawPassword = candidat.getMotDePasse();

        // Traitement du fichier (CV)
        if (!cvFile.isEmpty()) {
            try {
                // Dossier où les CV seront enregistrés (chemin absolu)
                String uploadDir = "C:/Users/AYMEN/Desktop/PFA-v/PFA/uploads/cv/";

                // Créez les dossiers si ils n'existent pas
                Files.createDirectories(Paths.get(uploadDir));

                // Nom du fichier
                String fileName = cvFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);

                // Sauvegarde du fichier
                cvFile.transferTo(filePath.toFile());

                // Enregistrer le chemin du fichier dans l'entité
                candidat.setCvPath(filePath.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/candidat/register?errorUpload"; // Retourner en cas d'erreur
            }
        }

        // Encodage du mot de passe
        candidat.setMotDePasse(passwordEncoder.encode(rawPassword));
        candidat.setRole(Role.CANDIDAT);

        // Sauvegarde dans la base de données
        utilisateurRepository.save(candidat);

        // Authentification automatique
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(candidat.getEmail(), rawPassword);

            Authentication auth = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            return "redirect:/candidat/login?error"; // Retourner en cas d'erreur d'authentification
        }

        // Redirection vers la page d'accueil candidat
        return "redirect:/candidat/home";
    }

    // Affichage de la page de connexion
    @GetMapping("/login")
    public String showLoginForm() {
        return "login_candidat";
    }

    // Page d'accueil du candidat
    @GetMapping("/home")
    public String candidatHome() {
        return "home_candidat"; // juste la page de bienvenue
    }


    @GetMapping("/entretiens")
    public String afficherEntretiens(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Candidat candidat = (Candidat) utilisateurRepository.findByEmail(email).orElse(null);

        if (candidat != null) {
            List<Entretien> entretiens = entretienService.getEntretiensByCandidatId(candidat.getId());
            model.addAttribute("entretiens", entretiens);
        }

        return "notif_candidat"; // nouvelle page Thymeleaf
    }


}
