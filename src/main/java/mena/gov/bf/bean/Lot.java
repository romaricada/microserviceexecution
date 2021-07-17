package mena.gov.bf.bean;


public class Lot {

    private Long id;

    private String libelle;

    private String description;

    private Long activiteId;

    private Boolean deleted;

    private Boolean infructueux;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getActiviteId() {
        return activiteId;
    }

    public void setActiviteId(Long activiteId) {
        this.activiteId = activiteId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getInfructueux() {
        return infructueux;
    }

    public void setInfructueux(Boolean infructueux) {
        this.infructueux = infructueux;
    }
}
