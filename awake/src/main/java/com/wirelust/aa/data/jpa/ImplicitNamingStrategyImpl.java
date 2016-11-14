package com.wirelust.aa.data.jpa;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource;
import org.hibernate.boot.model.naming.ImplicitIndexColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitIndexNameSource;
import org.hibernate.boot.model.naming.ImplicitMapKeyColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.naming.ImplicitUniqueKeyNameSource;
import org.hibernate.boot.model.naming.NamingHelper;

/**
 * Date: 13-Nov-2016
 *
 * @author T. Curran
 */
public class ImplicitNamingStrategyImpl extends ImplicitNamingStrategyJpaCompliantImpl {

	public Identifier determineMapKeyColumnName(ImplicitMapKeyColumnNameSource source) {
		return this.toIdentifier(this.transformAttributePath(source.getPluralAttributePath()) + "_key", source
			.getBuildingContext());
	}

	public Identifier determineListIndexColumnName(ImplicitIndexColumnNameSource source) {
		return this.toIdentifier(this.transformAttributePath(source.getPluralAttributePath()) + "_order", source
			.getBuildingContext());
	}

	public Identifier determineForeignKeyName(ImplicitForeignKeyNameSource source) {
		return this.toIdentifier(NamingHelper.INSTANCE.generateHashedFkName("fk_", source.getTableName(), source
			.getReferencedTableName(), source.getColumnNames()), source.getBuildingContext());
	}

	public Identifier determineUniqueKeyName(ImplicitUniqueKeyNameSource source) {
		// This doesn't appear to be getting called so the unique keys still come out as UK_
		// https://hibernate.atlassian.net/browse/HHH-11103
		return this.toIdentifier(NamingHelper.INSTANCE.generateHashedConstraintName("uk_", source.getTableName(),
			source.getColumnNames()), source.getBuildingContext());
	}

	public Identifier determineIndexName(ImplicitIndexNameSource source) {
		return this.toIdentifier(NamingHelper.INSTANCE.generateHashedConstraintName("ix_", source.getTableName(),
			source.getColumnNames()), source.getBuildingContext());
	}

}
