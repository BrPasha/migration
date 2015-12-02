package migration.core.model.rdb;

public enum RDBRelationType {
	primaryToForeign,
	primaryToPrimary,
	oneToOne,
	oneToMany,
	manyToOne,
	manyToMany
}
