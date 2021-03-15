package com.proyecto.bombsaway.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bombsaway.clases.ElementoBase;
import com.proyecto.bombsaway.clases.Posicion;
import com.proyecto.bombsaway.daos.IDAOElementoBase;
import com.proyecto.bombsaway.entidades.EntidadBase;
import com.proyecto.bombsaway.entidades.EntidadElementoBase;

@Service
public class ServicioElementoBase {

	@Autowired
	private IDAOElementoBase daoElementoBase;

	public List<EntidadElementoBase> buscarTodosPorIdBase(int idBase) {
		return this.daoElementoBase.findAllByIdBase(idBase);
	}

	public EntidadElementoBase guardar(ElementoBase elementoBase, EntidadBase base) {
		Posicion posicionBase = elementoBase.getPosicion();
		return this.daoElementoBase.save(new EntidadElementoBase(0, elementoBase.getNombre(), base,
				posicionBase.getEjeX(), posicionBase.getEjeY(), posicionBase.getAngulo(), elementoBase.isDestruido()));
	}

}
