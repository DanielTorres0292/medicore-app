CREATE DATABASE  IF NOT EXISTS `medicore_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `medicore_db`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: medicore_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `consultas`
--

DROP TABLE IF EXISTS `consultas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consultas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `expediente_paciente` varchar(20) COLLATE utf8mb4_spanish_ci NOT NULL,
  `id_medico` int NOT NULL,
  `temperatura` decimal(4,2) DEFAULT NULL,
  `presion` varchar(15) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `peso` decimal(5,2) DEFAULT NULL,
  `pulso` int DEFAULT NULL,
  `diagnostico` text COLLATE utf8mb4_spanish_ci NOT NULL,
  `tratamiento` text COLLATE utf8mb4_spanish_ci,
  `estado` enum('Atendida','Cancelada') COLLATE utf8mb4_spanish_ci DEFAULT 'Atendida',
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `expediente_paciente` (`expediente_paciente`),
  KEY `id_medico` (`id_medico`),
  CONSTRAINT `consultas_ibfk_1` FOREIGN KEY (`expediente_paciente`) REFERENCES `pacientes` (`expediente`),
  CONSTRAINT `consultas_ibfk_2` FOREIGN KEY (`id_medico`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consultas`
--

LOCK TABLES `consultas` WRITE;
/*!40000 ALTER TABLE `consultas` DISABLE KEYS */;
INSERT INTO `consultas` VALUES (1,'EXP-0012',2,36.80,'120/80',62.00,76,'Rinitis alérgica','Antihistamínico por 5 días','Atendida','2026-10-23 20:34:42');
/*!40000 ALTER TABLE `consultas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facturas`
--

DROP TABLE IF EXISTS `facturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facturas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_consulta` int NOT NULL,
  `monto_consulta` decimal(10,2) DEFAULT '0.00',
  `monto_medicamentos` decimal(10,2) DEFAULT '0.00',
  `total` decimal(10,2) DEFAULT '0.00',
  `saldo` decimal(10,2) DEFAULT '0.00',
  `estado` enum('Pendiente de pago','Pagada') COLLATE utf8mb4_spanish_ci DEFAULT 'Pendiente de pago',
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `id_consulta` (`id_consulta`),
  CONSTRAINT `facturas_ibfk_1` FOREIGN KEY (`id_consulta`) REFERENCES `consultas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturas`
--

LOCK TABLES `facturas` WRITE;
/*!40000 ALTER TABLE `facturas` DISABLE KEYS */;
INSERT INTO `facturas` VALUES (1,1,600.00,90.00,690.00,390.00,'Pendiente de pago','2026-10-23 20:44:51');
/*!40000 ALTER TABLE `facturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicamentos`
--

DROP TABLE IF EXISTS `medicamentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicamentos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) COLLATE utf8mb4_spanish_ci NOT NULL,
  `existencia` int DEFAULT '0',
  `stock_minimo` int DEFAULT '20',
  `lote` varchar(50) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `caducidad` date DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicamentos`
--

LOCK TABLES `medicamentos` WRITE;
/*!40000 ALTER TABLE `medicamentos` DISABLE KEYS */;
INSERT INTO `medicamentos` VALUES (1,'Paracetamol 500 mg',18,20,'L-1001','2027-12-01',90.00),(2,'Loratadina 10 mg',14,15,'L-1002','2026-11-18',120.00),(3,'Amoxicilina 500 mg',60,20,'L-1003','2028-05-20',250.00),(4,'Ibuprofeno 400 mg',50,20,'L-2402','2028-06-30',110.00);
/*!40000 ALTER TABLE `medicamentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pacientes`
--

DROP TABLE IF EXISTS `pacientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pacientes` (
  `expediente` varchar(20) COLLATE utf8mb4_spanish_ci NOT NULL,
  `nombres` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `apellidos` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `telefono` varchar(15) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `correo` varchar(100) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `seguro_medico` varchar(50) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `creado_en` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`expediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pacientes`
--

LOCK TABLES `pacientes` WRITE;
/*!40000 ALTER TABLE `pacientes` DISABLE KEYS */;
INSERT INTO `pacientes` VALUES ('EXP-0012','Ana','Torres Núñez','1992-03-14','6421002030','ana.torres@correo.mx','GNP','2026-10-23 19:04:09');
/*!40000 ALTER TABLE `pacientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagos`
--

DROP TABLE IF EXISTS `pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_factura` int NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `metodo` enum('Efectivo','Tarjeta') COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `id_factura` (`id_factura`),
  CONSTRAINT `pagos_ibfk_1` FOREIGN KEY (`id_factura`) REFERENCES `facturas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos`
--

LOCK TABLES `pagos` WRITE;
/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
INSERT INTO `pagos` VALUES (2,1,300.00,'Efectivo','2026-10-23 20:45:00');
/*!40000 ALTER TABLE `pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receta_detalles`
--

DROP TABLE IF EXISTS `receta_detalles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receta_detalles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_receta` int NOT NULL,
  `id_medicamento` int NOT NULL,
  `dosis` varchar(100) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `dias` int DEFAULT NULL,
  `cantidad_solicitada` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_receta` (`id_receta`),
  KEY `id_medicamento` (`id_medicamento`),
  CONSTRAINT `receta_detalles_ibfk_1` FOREIGN KEY (`id_receta`) REFERENCES `recetas` (`id`),
  CONSTRAINT `receta_detalles_ibfk_2` FOREIGN KEY (`id_medicamento`) REFERENCES `medicamentos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receta_detalles`
--

LOCK TABLES `receta_detalles` WRITE;
/*!40000 ALTER TABLE `receta_detalles` DISABLE KEYS */;
INSERT INTO `receta_detalles` VALUES (1,1,1,'1 cada 8 horas',3,2),(2,1,2,'1 cada 24 horas',5,1);
/*!40000 ALTER TABLE `receta_detalles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recetas`
--

DROP TABLE IF EXISTS `recetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recetas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_consulta` int NOT NULL,
  `estado` enum('Pendiente','Surtida') COLLATE utf8mb4_spanish_ci DEFAULT 'Pendiente',
  `fecha` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `id_consulta` (`id_consulta`),
  CONSTRAINT `recetas_ibfk_1` FOREIGN KEY (`id_consulta`) REFERENCES `consultas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recetas`
--

LOCK TABLES `recetas` WRITE;
/*!40000 ALTER TABLE `recetas` DISABLE KEYS */;
INSERT INTO `recetas` VALUES (1,1,'Pendiente','2026-10-23 20:35:38');
/*!40000 ALTER TABLE `recetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `correo` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_spanish_ci NOT NULL,
  `rol` enum('Recepción','Médico','Farmacia','Facturación','Administrador') COLLATE utf8mb4_spanish_ci NOT NULL,
  `intentos_fallidos` int DEFAULT '0',
  `bloqueado` tinyint(1) DEFAULT '0',
  `creado_en` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Marta López','recepcion@medicore.com','123456','Recepción',0,0,'2026-10-23 19:04:09'),(2,'Dra. Pérez','medico@medicore.com','123456','Médico',0,0,'2026-10-23 19:04:09'),(3,'Farmacia Central','farmacia@medicore.com','123456','Farmacia',0,0,'2026-10-23 19:04:09'),(4,'Caja / Cobros','facturacion@medicore.com','123456','Facturación',0,0,'2026-10-23 19:04:09'),(5,'Administrador Maestro','admin@medicore.mx','admin123','Administrador',0,0,'2026-10-23 21:04:43');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-24 19:50:10
