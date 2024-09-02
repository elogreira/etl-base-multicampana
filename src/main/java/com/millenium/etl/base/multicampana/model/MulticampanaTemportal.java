package com.millenium.etl.base.multicampana.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.millenium.etl.common.model.GuionSalidaBase;

@Entity
@Table(name = MulticampanaTemportal.TABLE_NAME , uniqueConstraints = @UniqueConstraint(columnNames = {"documento", "tipoDocumento"}))
public class MulticampanaTemportal extends GuionSalidaBase {
	
	public static final String TABLE_NAME = "tmp_guion_multicampana";
	

}
