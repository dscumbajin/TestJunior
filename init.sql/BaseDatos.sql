-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         9.0.0 - MySQL Community Server - GPL
-- SO del servidor:              Linux
-- HeidiSQL Versión:             12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para restspring
CREATE DATABASE IF NOT EXISTS `restspring` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `restspring`;

-- Volcando estructura para tabla restspring.clientes
DROP TABLE IF EXISTS `clientes`;
CREATE TABLE IF NOT EXISTS `clientes` (
  `contrasena` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `estado` tinyint(1) DEFAULT '1',
  `cliente_id` bigint NOT NULL,
  PRIMARY KEY (`cliente_id`),
  CONSTRAINT `FK3b2u85sny49u8gmk3bkrt3gjy` FOREIGN KEY (`cliente_id`) REFERENCES `personas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla restspring.clientes: ~2 rows (aproximadamente)
INSERT INTO `clientes` (`contrasena`, `estado`, `cliente_id`) VALUES
	('5678', 1, 1),
	('1234', 1, 2),
	('1245', 1, 3);

-- Volcando estructura para tabla restspring.cuentas
DROP TABLE IF EXISTS `cuentas`;
CREATE TABLE IF NOT EXISTS `cuentas` (
  `cuenta_id` bigint NOT NULL AUTO_INCREMENT,
  `estado` tinyint(1) DEFAULT '1',
  `numero` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `saldo_inicial` double NOT NULL,
  `tipo` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `cliente_id` bigint NOT NULL,
  PRIMARY KEY (`cuenta_id`),
  UNIQUE KEY `UK7sa6xm9anjkmpftqttuyhko56` (`numero`),
  KEY `FK65yk2321jpusl3fk96lqehrli` (`cliente_id`),
  CONSTRAINT `FK65yk2321jpusl3fk96lqehrli` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`cliente_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla restspring.cuentas: ~0 rows (aproximadamente)
INSERT INTO `cuentas` (`cuenta_id`, `estado`, `numero`, `saldo_inicial`, `tipo`, `cliente_id`) VALUES
	(1, 1, '478758', 2000, 'Ahorro', 2),
	(2, 1, '225487', 100, 'Corriente', 1),
	(3, 1, '495878', 0, 'Ahorros', 3),
	(4, 1, '496825', 540, 'Ahorros', 1),
	(5, 1, '585545', 1000, 'Corriente', 2);

-- Volcando estructura para tabla restspring.movimientos
DROP TABLE IF EXISTS `movimientos`;
CREATE TABLE IF NOT EXISTS `movimientos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha` datetime(6) NOT NULL,
  `saldo` double NOT NULL,
  `tipo` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `valor` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `cuenta_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4moe88hxuohcysas5h70mdc09` (`cuenta_id`),
  CONSTRAINT `FK4moe88hxuohcysas5h70mdc09` FOREIGN KEY (`cuenta_id`) REFERENCES `cuentas` (`cuenta_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla restspring.movimientos: ~0 rows (aproximadamente)
INSERT INTO `movimientos` (`id`, `fecha`, `saldo`, `tipo`, `valor`, `cuenta_id`) VALUES
	(1, '2024-07-10 19:11:19.938000', 1425, 'Retiro', '575', 1),
	(2, '2024-07-10 19:12:26.801000', 700, 'Deposito', '600', 2),
	(3, '2024-07-10 19:12:48.613000', 150, 'Deposito', '150', 3),
	(4, '2024-07-10 19:13:12.155000', 0, 'Retiro', '540', 4);

-- Volcando estructura para tabla restspring.personas
DROP TABLE IF EXISTS `personas`;
CREATE TABLE IF NOT EXISTS `personas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `direccion` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `edad` int DEFAULT NULL,
  `genero` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `identificacion` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `telefono` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdpxdn543sbyt8xkvsqha0l1li` (`identificacion`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla restspring.personas: ~2 rows (aproximadamente)
INSERT INTO `personas` (`id`, `direccion`, `edad`, `genero`, `identificacion`, `nombre`, `telefono`) VALUES
	(1, ' Amazonas y NNUU ', 21, 'Femenino', '0975489650', 'Marianela Montalvo', '097548965'),
	(2, 'Otavalo sn y principal ', 18, 'Masculino', '0982547850', 'Jose Lema', '098254785'),
	(3, '13 junio y Equinoccial', 40, 'Masculino', '0988745870', 'Juan Osorio ', '098874587');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
