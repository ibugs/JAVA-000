DROP table IF EXISTS `person`;
CREATE TABLE `person`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `age`  int(11) NULL DEFAULT null,
    primary key (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0;