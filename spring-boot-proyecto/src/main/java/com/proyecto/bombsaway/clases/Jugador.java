package com.proyecto.bombsaway.clases;

import com.proyecto.bombsaway.dtos.DTOAvion;
import com.proyecto.bombsaway.dtos.DTOUsuario;

import javax.json.*;
import java.util.ArrayList;
import java.util.List;


public class Jugador {
    private int id;
    private String nombre;
    private List<Avion> listAviones;
    private Base base;
    private List<Artilleria> listArtilleria;

	public Jugador(String nomUsuario) {
        this.nombre = nomUsuario;
        this.listAviones = new ArrayList<Avion>();
        this.base = null;
        this.listArtilleria = new ArrayList<Artilleria>();
    }

    public DTOUsuario getDTO() {
        DTOUsuario res = new DTOUsuario(this.nombre);
        List<DTOAvion> resListAviones = new ArrayList<DTOAvion>();
        for (Avion avion: this.listAviones) {
            DTOAvion avionDTO = avion.getDTO();
            resListAviones.add(avionDTO);
        }
        res.setListAviones(resListAviones);
        return res;
    }

    public void updateAvion(Avion nuevoAvion) {
        Avion avionActual = this.listAviones.get(nuevoAvion.getId());
        avionActual.setVida(nuevoAvion.getVida());
        avionActual.setCombustible(nuevoAvion.getCombustible());
        avionActual.setPosicion(nuevoAvion.getPosicion());
        avionActual.setListaBalas(nuevoAvion.getListBalas());
    }

    public int getId() {
        return id;
    }

//    @Override
//    public String toString() {
//        String res = "Usuario: [" + this.nombre + ", " + this.listAviones.toString() + " ]";
//        return res;
//    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setListAviones(List<Avion> listAviones) {
        this.listAviones = listAviones;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Avion> getListAviones() {
        return listAviones;
    }
    
    public Base getBase() {
  		return base;
  	}

  	public void setBase(Base base) {
  		this.base = base;
  	}
  	
    public List<Artilleria> getListArtilleria() {
		return listArtilleria;
	}

	public void setListArtilleria(List<Artilleria> listArtilleria) {
		this.listArtilleria = listArtilleria;
	}

	public JsonObject toJSON() {
        JsonArrayBuilder arrAvionesBld = Json.createArrayBuilder();
        int i = 0;
        for (Avion avion: this.listAviones) {
            arrAvionesBld.add(this.listAviones.get(i).toJSON());
            i++;
        }
        JsonObject res = Json.createObjectBuilder()
                .add("jugador:", Json.createObjectBuilder()
                        .add("nombre", this.nombre)
                        .add("listAviones:", arrAvionesBld.build())
                ).build();
        return res;
    }

    public void setId(int id) {
        this.id = id;
    }
}
