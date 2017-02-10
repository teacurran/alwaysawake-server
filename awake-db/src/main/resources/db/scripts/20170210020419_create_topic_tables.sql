-- // create topic tables
-- Migration SQL that makes the change goes here.

CREATE TABLE topics (
	`id`                BIGINT NOT NULL AUTO_INCREMENT,
	`body`              LONGTEXT,
	`date_created`      DATETIME,
	`date_modified`     DATETIME,
	`replies`           INTEGER,
	`subject`           VARCHAR(255),
	`author_id`         BIGINT,
	`last_responder_id` BIGINT,
	PRIMARY KEY (`id`)
)
	ENGINE = InnoDB;

CREATE TABLE topic_replies (
	`id`            BIGINT NOT NULL AUTO_INCREMENT,
	`body`          LONGTEXT,
	`date_created`  DATETIME,
	`date_modified` DATETIME,
	`author_id`     BIGINT,
	PRIMARY KEY (`id`)
)
	ENGINE = InnoDB;

ALTER TABLE topics
	ADD CONSTRAINT `fk_g6l7ksnivjhgub8ndhdvgjssg`
FOREIGN KEY (`author_id`)
REFERENCES accounts (`id`);

ALTER TABLE topics
	ADD CONSTRAINT `fk_q59j5rrxjl2pyx07ob88ukwnu`
FOREIGN KEY (`last_responder_id`)
REFERENCES accounts (`id`);

ALTER TABLE topic_replies
	ADD CONSTRAINT `fk_4fd09uy53y80f2oc2p6yws0tg`
FOREIGN KEY (`author_id`)
REFERENCES accounts (`id`);


-- //@UNDO
-- SQL to undo the change goes here.

DROP TABLE topic_replies;
DROP TABLE topics;
