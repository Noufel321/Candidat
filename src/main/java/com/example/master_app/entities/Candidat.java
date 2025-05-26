package com.example.master_app.entities;

import com.example.master_app.enumes.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "candidat")
public class Candidat extends Utilisateur {

    private String cvPath;
    private String specialite;

    @Transient
    private MultipartFile cvFile; // ✅ Ne sera pas stocké en base

    public Candidat() {}

    public Candidat(int id, String nom, String email, String motDePasse, Role role, String cvPath, String specialite) {
        super(id, nom, email, motDePasse, role);
        this.cvPath = cvPath;
        this.specialite = specialite;
    }

    public String getCvPath() {
        return cvPath;
    }

    public void setCvPath(String cvPath) {
        this.cvPath = cvPath;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public MultipartFile getCvFile() {
        return cvFile;
    }

    public void setCvFile(MultipartFile cvFile) {
        this.cvFile = cvFile;
    }
}
