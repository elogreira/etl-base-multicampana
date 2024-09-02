package com.millenium.etl.base.multicampana.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class MulticampanaNativeRepository {

	// Para uso de query nativo
	@PersistenceContext
	EntityManager entityManager;
	
	public int createGuionFromTemporalTable(String targetTable, String temporalTable, long listId, long userId) {
		// Crea el guion
		Query insertGuion = entityManager.createNativeQuery(
				String.format( "INSERT INTO {h-schema}%s ("
						+ "id,"
						+ "documento,"
						+ "tipoDocumento,"
						+ "primerNombre,"
						+ "segundoNombre,"
						+ "primerApellido,"
						+ "segundoApellido,"
						+ "email,"
						+ "telefono1,"
						+ "telefono2,"
						+ "telefono3,"
						+ "telefono4,"
						+ "informacionAdicional1,"
						+ "informacionAdicional2,"
						+ "informacionAdicional3,"
						+ "informacionAdicional4,"
						+ "categoriaFormulario,"
						+ "direccion,"
						+ "municipio,"
						+ "departamento,"
						+ "fechaEvento,"
						+ "horaEvento,"
						+ "lugarEvento,"
						+ "motivo,"
						+ "motivoDescripcion,"
						+ "marcadorLista_id,"
						+ "fechaCreacion,"
						+ "fechaModificacion,"
						+ "idUsuarioCrea,"
						+ "idUsuarioModifica"
						+ ") "
						+ "SELECT "
						+ "nextval('{h-schema}seq_guionbase'),"
						+ "documento,"
						+ "tipoDocumento,"
						+ "primerNombre,"
						+ "segundoNombre,"
						+ "primerApellido,"
						+ "segundoApellido,"
						+ "email,"
						+ "telefono1,"
						+ "telefono2,"
						+ "telefono3,"
						+ "telefono4,"
						+ "informacionAdicional1,"
						+ "informacionAdicional2,"
						+ "informacionAdicional3,"
						+ "informacionAdicional4,"
						+ "categoriaFormulario,"
						+ "direccion,"
						+ "municipio,"
						+ "departamento,"
						+ "fechaEvento,"
						+ "horaEvento,"
						+ "lugarEvento,"
						+ "motivo,"
						+ "motivoDescripcion,"
						+ "%d,"
						+ "current_timestamp,"
						+ "current_timestamp,"
						+ ":userId,"
						+ ":userId "
						+ "FROM {h-schema}%s"
						, targetTable, listId, temporalTable)
				).setParameter("userId", userId);
		return insertGuion.executeUpdate();
	}

}