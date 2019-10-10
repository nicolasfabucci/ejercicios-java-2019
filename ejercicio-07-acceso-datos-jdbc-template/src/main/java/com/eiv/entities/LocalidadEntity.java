package com.eiv.entities;

public class LocalidadEntity {

    private long id;
    private String nombre;
    private ProvinciaEntity provincia;
    
    public LocalidadEntity() {
    }
    
    public LocalidadEntity(long id, String nombre) {
        this(id, nombre, null);
    }

    public LocalidadEntity(long id, String nombre, ProvinciaEntity provincia) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ProvinciaEntity getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaEntity provincia) {
        this.provincia = provincia;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LocalidadEntity other = (LocalidadEntity) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LocalidadEntity [id=" + id + ", nombre=" + nombre + "]";
    }
    
    public static class Builder {
        
        private long id;
        private String nombre;
        private long provinciaId;
        private String provinciaNombre;
        
        private Builder() {
        }
        
        public static Builder newInstance() {
            return new Builder();
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder setProvinciaId(long provinciaId) {
            this.provinciaId = provinciaId;
            return this;
        }

        public Builder setProvinciaNombre(String provinciaNombre) {
            this.provinciaNombre = provinciaNombre;
            return this;
        }
        
        public LocalidadEntity build() {
            ProvinciaEntity provinciaEntity = new ProvinciaEntity(provinciaId, provinciaNombre);
            LocalidadEntity localidadEntity = new LocalidadEntity(id, nombre, provinciaEntity);
            return localidadEntity;
        }
    }
}
