package com.wirelust.aa.data.jpa;

import java.io.Serializable;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Date: 12-Nov-2016
 *
 * @author T. Curran
 */
public class PhysicalNamingStrategyImpl extends PluralNamingStrategy
	implements org.hibernate.boot.model.naming.PhysicalNamingStrategy, Serializable {

	private static final long serialVersionUID = -352159175339462656L;

	@Override
	public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		return identifier;
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		return identifier;
	}

	@Override
	public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		return Identifier.toIdentifier(super.classToTableName(identifier.getText()));
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		return identifier;
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		return jdbcEnvironment.getIdentifierHelper().toIdentifier(addUnderscores(identifier.getText()));
	}
}
