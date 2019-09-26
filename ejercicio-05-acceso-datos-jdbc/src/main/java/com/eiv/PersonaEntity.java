package com.eiv;

public class PersonaEntity {

    private Integer tipoDocumentoId;
    private Long numeroDocumento;
    private String nombres;
    
    public PersonaEntity() {
    }

    public PersonaEntity(Integer tipoDocumentoId, Long numeroDocumento, String nombres) {
        super();
        this.tipoDocumentoId = tipoDocumentoId;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
    }

    public Integer getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(Integer tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombres == null) ? 0 : nombres.hashCode());
        result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
        result = prime * result + ((tipoDocumentoId == null) ? 0 : tipoDocumentoId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PersonaEntity other = (PersonaEntity) obj;
        if (nombres == null) {
            if (other.nombres != null) {
                return false;
            }
        } else if (!nombres.equals(other.nombres)) {
            return false;
        }
        if (numeroDocumento == null) {
            if (other.numeroDocumento != null) {
                return false;
            }
        } else if (!numeroDocumento.equals(other.numeroDocumento)) {
            return false;
        }
        if (tipoDocumentoId == null) {
            if (other.tipoDocumentoId != null) {
                return false;
            }
        } else if (!tipoDocumentoId.equals(other.tipoDocumentoId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersonaEntity [tipoDocumentoId=" + tipoDocumentoId 
                + ", numeroDocumento=" + numeroDocumento
                + ", nombres=" + nombres + "]";
    }    
}
