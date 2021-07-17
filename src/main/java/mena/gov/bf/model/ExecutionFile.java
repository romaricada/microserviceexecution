package mena.gov.bf.model;

import java.util.List;

public class ExecutionFile {
    private Long id;

    private TypeDossier typeDossier;

    private List<DataFile> files;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeDossier getTypeDossier() {
        return typeDossier;
    }

    public void setTypeDossier(TypeDossier typeDossier) {
        this.typeDossier = typeDossier;
    }

    public List<DataFile> getFiles() {
        return files;
    }

    public void setFiles(List<DataFile> files) {
        this.files = files;
    }
}
