package com.mundodental.entidad;

import com.mundodental.anotaciones.AutoIncrement;
import com.mundodental.anotaciones.Entity;
import com.mundodental.anotaciones.FieldName;
import com.mundodental.anotaciones.NotNull;
import com.mundodental.anotaciones.PrimaryKey;

@Entity(table = "Permiso")
public class Permiso {
    @PrimaryKey
    @AutoIncrement
    @FieldName(name = "idpermiso")
    private int idpermiso;
    @NotNull
    private int idmenu;
    @NotNull
    private int idRol;

    public Permiso() {
    }

    public Permiso( int idmenu, int idrol) {
        ///this.idpermiso = idpermiso;
        this.idmenu = idmenu;
        this.idRol = idrol;
    }

    public int getIdpermiso() {
        return idpermiso;
    }

    public void setIdpermiso(int idpermiso) {
        this.idpermiso = idpermiso;
    }

    public int getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(int idmenu) {
        this.idmenu = idmenu;
    }

    public int getIdrol() {
        return idRol;
    }

    public void setIdrol(int idrol) {
        this.idRol = idrol;
    }
    
}
