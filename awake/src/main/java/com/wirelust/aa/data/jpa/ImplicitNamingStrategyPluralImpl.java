package com.wirelust.aa.data.jpa;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.boot.model.naming.EntityNaming;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitAnyDiscriminatorColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitAnyKeyColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitBasicColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitCollectionTableNameSource;
import org.hibernate.boot.model.naming.ImplicitDiscriminatorColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitEntityNameSource;
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource;
import org.hibernate.boot.model.naming.ImplicitIdentifierColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitIndexColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitIndexNameSource;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitJoinTableNameSource;
import org.hibernate.boot.model.naming.ImplicitMapKeyColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitPrimaryKeyJoinColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitTenantIdColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitUniqueKeyNameSource;
import org.hibernate.boot.model.naming.NamingHelper;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.internal.util.StringHelper;

/**
 * Date: 12-Nov-2016
 *
 * @author T. Curran
 */
public class ImplicitNamingStrategyPluralImpl extends PluralNamingStrategy
	implements ImplicitNamingStrategy, Serializable {

	private static final long serialVersionUID = -352159175339462656L;

	@Override
	public Identifier determinePrimaryTableName(ImplicitEntityNameSource source) {

		if (source == null) {
			// should never happen, but to be defensive...
			throw new HibernateException("Entity naming information was not provided.");
		}

		String entityName = transformEntityName(source.getEntityNaming());

		if (entityName == null) {
			throw new HibernateException("Could not determine primary table name for entity");
		}

		return toIdentifier( super.classToTableName(entityName), source.getBuildingContext() );
	}

	@Override
	public Identifier determineJoinTableName(ImplicitJoinTableNameSource source) {
		// JPA states we should use the following as default:
		//		"The concatenated names of the two associated primary entity tables (owning side
		//		first), separated by an underscore."
		// aka:
		// 		{OWNING SIDE PRIMARY TABLE NAME}_{NON-OWNING SIDE PRIMARY TABLE NAME}
		final String name = source.getOwningPhysicalTableName()
				+ '_'
				+ source.getNonOwningPhysicalTableName();

		return toIdentifier( name, source.getBuildingContext() );
	}

	@Override
	public Identifier determineCollectionTableName(ImplicitCollectionTableNameSource source) {
		// JPA states we should use the following as default:
		//      "The concatenation of the name of the containing entity and the name of the
		//       collection attribute, separated by an underscore.
		// aka:
		//     if owning entity has a JPA entity name: {OWNER JPA ENTITY NAME}_{COLLECTION ATTRIBUTE NAME}
		//     otherwise: {OWNER ENTITY NAME}_{COLLECTION ATTRIBUTE NAME}
		final String entityName = transformEntityName( source.getOwningEntityNaming() );

		final String name = entityName
				+ '_'
				+ transformAttributePath( source.getOwningAttributePath() );

		return toIdentifier( name, source.getBuildingContext() );
	}

	@Override
	public Identifier determineDiscriminatorColumnName(ImplicitDiscriminatorColumnNameSource source) {
		return toIdentifier(
				source.getBuildingContext().getMappingDefaults().getImplicitDiscriminatorColumnName(),
				source.getBuildingContext()
		);
	}

	@Override
	public Identifier determineTenantIdColumnName(ImplicitTenantIdColumnNameSource source) {
		return toIdentifier(
				source.getBuildingContext().getMappingDefaults().getImplicitTenantIdColumnName(),
				source.getBuildingContext()
		);
	}

	@Override
	public Identifier determineIdentifierColumnName(ImplicitIdentifierColumnNameSource source) {
		// JPA states the implicit column name should be the attribute name
		return toIdentifier(
				transformAttributePath( source.getIdentifierAttributePath() ),
				source.getBuildingContext()
		);
	}

	@Override
	public Identifier determineBasicColumnName(ImplicitBasicColumnNameSource source) {
		// JPA states we should use the following as default:
		//     "The property or field name"
		// aka:
		//     The unqualified attribute path.
		return toIdentifier( addUnderscores(transformAttributePath( source.getAttributePath())),
			source.getBuildingContext() );
	}

	@Override
	public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
		// JPA states we should use the following as default:
		//
		//	(1) if there is a "referencing relationship property":
		//		"The concatenation of the following: the name of the referencing relationship
		// 			property or field of the referencing entity or embeddable class; "_"; the
		// 			name of the referenced primary key column."
		//
		//	(2) if there is no such "referencing relationship property", or if the association is
		// 			an element collection:
		//     "The concatenation of the following: the name of the entity; "_"; the name of the
		// 			referenced primary key column"

		// todo : we need to better account for "referencing relationship property"

		final String name;

		if ( source.getNature() == ImplicitJoinColumnNameSource.Nature.ELEMENT_COLLECTION
				|| source.getAttributePath() == null ) {
			name = transformEntityName( source.getEntityNaming() )
					+ '_'
					+ source.getReferencedColumnName().getText();
		}
		else {
			name = transformAttributePath( source.getAttributePath() )
					+ '_'
					+ source.getReferencedColumnName().getText();
		}

		return toIdentifier( name, source.getBuildingContext() );
	}

	@Override
	public Identifier determinePrimaryKeyJoinColumnName(ImplicitPrimaryKeyJoinColumnNameSource source) {
		// JPA states we should use the following as default:
		// 		"the same name as the primary key column [of the referenced table]"
		return source.getReferencedPrimaryKeyColumnName();
	}

	@Override
	public Identifier determineAnyDiscriminatorColumnName(ImplicitAnyDiscriminatorColumnNameSource source) {
		return toIdentifier(
				transformAttributePath( source.getAttributePath() ) + "_" + source.getBuildingContext().getMappingDefaults().getImplicitDiscriminatorColumnName(),
				source.getBuildingContext()
		);
	}

	@Override
	public Identifier determineAnyKeyColumnName(ImplicitAnyKeyColumnNameSource source) {
		return toIdentifier(
				transformAttributePath( source.getAttributePath() ) + "_" + source.getBuildingContext().getMappingDefaults().getImplicitIdColumnName(),
				source.getBuildingContext()
		);
	}

	@Override
	public Identifier determineMapKeyColumnName(ImplicitMapKeyColumnNameSource source) {
		return toIdentifier(
				transformAttributePath( source.getPluralAttributePath() ) + "_KEY",
				source.getBuildingContext()
		);
	}

	@Override
	public Identifier determineListIndexColumnName(ImplicitIndexColumnNameSource source) {
		return toIdentifier(
				transformAttributePath( source.getPluralAttributePath() ) + "_ORDER",
				source.getBuildingContext()
		);
	}

	@Override
	public Identifier determineForeignKeyName(ImplicitForeignKeyNameSource source) {
		return toIdentifier(
				NamingHelper.INSTANCE.generateHashedFkName(
						"FK",
						source.getTableName(),
						source.getReferencedTableName(),
						source.getColumnNames()
				),
				source.getBuildingContext()
		);
	}

	@Override
	public Identifier determineUniqueKeyName(ImplicitUniqueKeyNameSource source) {
		for (Identifier id : source.getColumnNames()) {
			id.getCanonicalName()
		}
		return toIdentifier(
				NamingHelper.INSTANCE.generateHashedConstraintName(
						"UK",
						source.getTableName(),
						source.getColumnNames()
				),
				source.getBuildingContext()
		);
	}

	@Override
	public Identifier determineIndexName(ImplicitIndexNameSource source) {
		return toIdentifier(
				NamingHelper.INSTANCE.generateHashedConstraintName(
						"IDX",
						source.getTableName(),
						source.getColumnNames()
				),
				source.getBuildingContext()
		);
	}

	private String transformEntityName(EntityNaming entityNaming) {
		// prefer the JPA entity name, if specified...
		if ( StringHelper.isNotEmpty( entityNaming.getJpaEntityName() ) ) {
			return entityNaming.getJpaEntityName();
		}
		else {
			// otherwise, use the Hibernate entity name
			return StringHelper.unqualify( entityNaming.getEntityName() );
		}
	}

	/**
	 * For JPA standards we typically need the unqualified name.  However, a more usable
	 * impl tends to use the whole path.  This method provides an easy hook for subclasses
	 * to accomplish that
	 *
	 * @param attributePath The attribute path
	 *
	 * @return The extracted name
	 */
	private String transformAttributePath(AttributePath attributePath) {
		return attributePath.getProperty();
	}

	/**
	 * Easy hook to build an Identifier using the keyword safe IdentifierHelper.
	 *
	 * @param stringForm The String form of the name
	 * @param buildingContext Access to the IdentifierHelper
	 *
	 * @return The identifier
	 */
	private Identifier toIdentifier(String stringForm, MetadataBuildingContext buildingContext) {
		return buildingContext.getMetadataCollector()
				.getDatabase()
				.getJdbcEnvironment()
				.getIdentifierHelper()
				.toIdentifier( stringForm );
	}

}
