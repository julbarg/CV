CREATE TABLE `last_setting_file` (
  `id_last_setting_file` bigint(15) NOT NULL AUTO_INCREMENT,
  `name_file` varchar(100) NOT NULL,
  `url` varchar(150) NOT NULL,
  PRIMARY KEY (`id_last_setting_file`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8;

CREATE TABLE `type_multivalue` (
  `id_type_multivalue` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id_type_multivalue`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `multivalue` (
  `id_multivalue` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(25) NOT NULL,
  `name` varchar(100) NOT NULL,
  `id_type_multivalue` int(11) NOT NULL,
  PRIMARY KEY (`id_multivalue`),
  KEY `fk_multivalue_01_idx` (`id_type_multivalue`),
  CONSTRAINT `fk_multivalue_01` FOREIGN KEY (`id_type_multivalue`) REFERENCES `type_multivalue` (`id_type_multivalue`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

CREATE TABLE `country` (
  `id_country` varchar(9) NOT NULL,
  `name` varchar(45) NOT NULL,
  `geocode` varchar(6) NOT NULL,
  `lat_center` varchar(100) NOT NULL,
  `lng_center` varchar(100) NOT NULL,
  `zoom` int(11) NOT NULL,
  PRIMARY KEY (`id_country`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `departament` (
  `id_departament` varchar(3) NOT NULL,
  `name` varchar(45) NOT NULL,
  `geocode` varchar(6) NOT NULL,
  `lat_center` varchar(100) NOT NULL,
  `lng_center` varchar(100) NOT NULL,
  `zoom` int(11) NOT NULL,
  PRIMARY KEY (`id_departament`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `city` (
  `id_city` varchar(6) NOT NULL,
  `name` varchar(45) NOT NULL,
  `id_departament` varchar(3) NOT NULL,
  PRIMARY KEY (`id_city`),
  KEY `fk_city_01_idx` (`id_departament`),
  CONSTRAINT `fk_city_01` FOREIGN KEY (`id_departament`) REFERENCES `departament` (`id_departament`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `admin` (
  `user` varchar(100) NOT NULL,
  PRIMARY KEY (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `client_profile` (
  `id_client_profile` bigint(15) NOT NULL AUTO_INCREMENT,
  `id_client` bigint(15) NOT NULL,
  `nit_client` varchar(20) NOT NULL,
  `name_client` varchar(100) NOT NULL,
  `state` varchar(2) NOT NULL,
  `observation_state` longtext,
  PRIMARY KEY (`id_client_profile`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 COMMENT='Perfil del Cliente';


CREATE TABLE `client_contact` (
  `id_client_contact` bigint(15) NOT NULL AUTO_INCREMENT,
  `name_contact` varchar(45) NOT NULL,
  `email` varchar(60) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `mobil` varchar(20) DEFAULT NULL,
  `type_contact` varchar(25) DEFAULT NULL,
  `id_client_profile` bigint(15) NOT NULL,
  PRIMARY KEY (`id_client_contact`),
  KEY `fk_contact_profile_01_idx` (`id_client_profile`),
  CONSTRAINT `fk_client_contact_01` FOREIGN KEY (`id_client_profile`) REFERENCES `client_profile` (`id_client_profile`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='Contactos del Perfil del Cliente';

CREATE TABLE `client_file` (
  `id_client_file` bigint(15) NOT NULL AUTO_INCREMENT,
  `id_client_profile` bigint(15) NOT NULL,
  `name_file` varchar(100) NOT NULL,
  `url` varchar(150) NOT NULL,
  PRIMARY KEY (`id_client_file`),
  KEY `fk_client_file_01_idx` (`id_client_profile`),
  CONSTRAINT `fk_client_file_01` FOREIGN KEY (`id_client_profile`) REFERENCES `client_profile` (`id_client_profile`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

CREATE TABLE `client_service` (
  `id_client_service` bigint(15) NOT NULL AUTO_INCREMENT,
  `lat` varchar(100) NOT NULL,
  `lng` varchar(100) NOT NULL,
  `id_department` varchar(3) DEFAULT NULL,
  `id_city` varchar(6) DEFAULT NULL,
  `id_country` varchar(9) DEFAULT NULL,
  `direction` varchar(150) NOT NULL,
  `alias` varchar(45) NOT NULL,
  `type_service` varchar(25) DEFAULT NULL,
  `code_service` varchar(45) NOT NULL,
  `main_point` varchar(2) NOT NULL,
  `backup` varchar(2) NOT NULL,
  `id_last_settings_file` bigint(15) DEFAULT NULL,
  `id_provider_last_mile` varchar(25) DEFAULT NULL,
  `code_service_last_mile` varchar(45) DEFAULT NULL,
  `id_client_profile` bigint(15) DEFAULT NULL,
  `description` longtext,
  `observation_state` longtext,
  `state` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id_client_service`),
  KEY `fk_customer_service_01_idx` (`id_client_profile`),
  KEY `fk_client_service_02_idx` (`id_department`),
  KEY `fk_client_service_03_idx` (`id_city`),
  KEY `fk_client_service_04_idx` (`id_last_settings_file`),
  KEY `fk_cliente_service_05_idx` (`id_country`),
  CONSTRAINT `fk_client_service_01` FOREIGN KEY (`id_client_profile`) REFERENCES `client_profile` (`id_client_profile`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_client_service_02` FOREIGN KEY (`id_department`) REFERENCES `departament` (`id_departament`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_client_service_03` FOREIGN KEY (`id_city`) REFERENCES `city` (`id_city`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_client_service_04` FOREIGN KEY (`id_last_settings_file`) REFERENCES `last_setting_file` (`id_last_setting_file`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_cliente_service_05` FOREIGN KEY (`id_country`) REFERENCES `country` (`id_country`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;

CREATE TABLE `service_contact` (
  `id_service_contact` bigint(15) NOT NULL AUTO_INCREMENT,
  `name_contact` varchar(100) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `mobil` varchar(45) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `schedule` varchar(25) DEFAULT NULL,
  `id_client_service` bigint(15) NOT NULL,
  PRIMARY KEY (`id_service_contact`),
  KEY `fk_service_contact_01_idx` (`id_client_service`),
  CONSTRAINT `fk_service_contact_01` FOREIGN KEY (`id_client_service`) REFERENCES `client_service` (`id_client_service`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;


CREATE TABLE `service_file` (
  `id_service_file` bigint(15) NOT NULL AUTO_INCREMENT,
  `id_client_service` bigint(15) NOT NULL,
  `name_file` varchar(100) NOT NULL,
  `url` varchar(150) NOT NULL,
  PRIMARY KEY (`id_service_file`),
  KEY `fk_service_file_01_idx` (`id_client_service`),
  CONSTRAINT `fk_service_file_01` FOREIGN KEY (`id_client_service`) REFERENCES `client_service` (`id_client_service`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

