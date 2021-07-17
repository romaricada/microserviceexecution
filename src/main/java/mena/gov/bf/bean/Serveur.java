package mena.gov.bf.bean;

import mena.gov.bf.model.TypeServeur;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


public class Serveur implements Serializable {

    private Long id;

    @NotNull
    private String adresse;

    @NotNull
    private Integer port;

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @NotNull
    private Boolean active;


    @NotNull
    private String nomServeur;

    @NotNull
    private String motPasse;

    public String getMotPasse() {
        return motPasse;
    }

    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }

    public TypeServeur getTypeServeur() {
        return typeServeur;
    }

    public void setTypeServeur(TypeServeur typeServeur) {
        this.typeServeur = typeServeur;
    }

    @NotNull
    private TypeServeur typeServeur;

    @NotNull
    private Boolean deleted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getNomServeur() {
        return nomServeur;
    }

    public void setNomServeur(String nomServeur) {
        this.nomServeur = nomServeur;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Serveur Serveur = (Serveur) o;
        if (Serveur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), Serveur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Serveur{" +
            "id=" + id +
            ", adresse='" + adresse + '\'' +
            ", port=" + port +
            ", nomServeur='" + nomServeur + '\'' +
            ", motPasse='" + motPasse + '\'' +
            ", deleted=" + deleted +
            '}';
    }
}
