-- MySQL dump 10.13  Distrib 5.5.57, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: db_DCR
-- ------------------------------------------------------
-- Server version	5.5.57-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_anggota`
--

DROP TABLE IF EXISTS `tb_anggota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_anggota` (
  `id_anggota` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `nama_anggota` varchar(35) NOT NULL,
  `jabatan` varchar(35) NOT NULL,
  `no_induk` varchar(15) NOT NULL,
  PRIMARY KEY (`id_anggota`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_anggota`
--

LOCK TABLES `tb_anggota` WRITE;
/*!40000 ALTER TABLE `tb_anggota` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_anggota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_dcr`
--

DROP TABLE IF EXISTS `tb_dcr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_dcr` (
  `id_dcr` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `nama_dcr` varchar(50) NOT NULL,
  `alamat_dcr` text NOT NULL,
  `foto_dcr` text NOT NULL,
  `lat` varchar(50) NOT NULL,
  `lng` varchar(50) NOT NULL,
  PRIMARY KEY (`id_dcr`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_dcr`
--

LOCK TABLES `tb_dcr` WRITE;
/*!40000 ALTER TABLE `tb_dcr` DISABLE KEYS */;
INSERT INTO `tb_dcr` VALUES (11,1,'Kantor pak bayu','Jl. Fajri sebrang pom bensin pan','20170917_140524.jpg','0.43447983344826','101.46523889154194'),(12,2,'Kantor pak hasan','Jl. Fajri sebrang pom bensin pan','20170917_140524.jpg','0.43447983344826','101.46523889154194'),(13,3,'kantor pak harun','Jl. Fajri sebrang pom bensin pan','20170917_140524.jpg','0.43447983344826','101.46523889154194'),(14,4,'kantor Gandhi','Jl. Fajri sebrang pom bensin pan','20170917_140524.jpg','0.43447983344826','101.46523889154194');
/*!40000 ALTER TABLE `tb_dcr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_event`
--

DROP TABLE IF EXISTS `tb_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_event` (
  `id_event` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `judul_event` varchar(50) NOT NULL,
  `keterangan_event` text NOT NULL,
  `foto_event` text NOT NULL,
  PRIMARY KEY (`id_event`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_event`
--

LOCK TABLES `tb_event` WRITE;
/*!40000 ALTER TABLE `tb_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_komentar`
--

DROP TABLE IF EXISTS `tb_komentar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_komentar` (
  `id_komentar` int(11) NOT NULL AUTO_INCREMENT,
  `id_event` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `komentar` text NOT NULL,
  `tgl_komentar` datetime NOT NULL,
  PRIMARY KEY (`id_komentar`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_komentar`
--

LOCK TABLES `tb_komentar` WRITE;
/*!40000 ALTER TABLE `tb_komentar` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_komentar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_notifikasi`
--

DROP TABLE IF EXISTS `tb_notifikasi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_notifikasi` (
  `id_notifikasi` int(11) NOT NULL AUTO_INCREMENT,
  `id_event` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `isi_notifikasi` varchar(50) NOT NULL,
  `tgl_notifikasi` datetime NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id_notifikasi`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_notifikasi`
--

LOCK TABLES `tb_notifikasi` WRITE;
/*!40000 ALTER TABLE `tb_notifikasi` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_notifikasi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sekolah`
--

DROP TABLE IF EXISTS `tb_sekolah`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sekolah` (
  `id_sekolah` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `nama_sekolah` varchar(50) NOT NULL,
  `alamat_sekolah` text NOT NULL,
  `foto_sekolah` text NOT NULL,
  `lat` varchar(50) NOT NULL,
  `lng` varchar(50) NOT NULL,
  PRIMARY KEY (`id_sekolah`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sekolah`
--

LOCK TABLES `tb_sekolah` WRITE;
/*!40000 ALTER TABLE `tb_sekolah` DISABLE KEYS */;
INSERT INTO `tb_sekolah` VALUES (9,4,'Sekolah 1','','20170917_140524.jpg','0.43447983344826','101.46523889154194'),(10,4,'Sekolah 2','','20170917_140524.jpg','',''),(11,4,'Sekolah 3','','20170917_140524.jpg','','');
/*!40000 ALTER TABLE `tb_sekolah` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_users`
--

DROP TABLE IF EXISTS `tb_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL,
  `no_induk` varchar(15) NOT NULL,
  `user_login` varchar(35) NOT NULL,
  `password` text NOT NULL,
  `level` int(11) NOT NULL,
  `nama_user` varchar(35) NOT NULL,
  `hp_user` varchar(15) NOT NULL,
  `token` text NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_users`
--

LOCK TABLES `tb_users` WRITE;
/*!40000 ALTER TABLE `tb_users` DISABLE KEYS */;
INSERT INTO `tb_users` VALUES (1,0,'853251242','hasanudin','e7ef293a709760f8cd27c970ce3de52e5b45c53d',1,'Pak Bayu','0823999999','fVmayccU-rc:APA91bGVu7mrgyInbPhfHTRK7IXefMsnx-_g9OK_abM9LxvlMgeyfsPfxkp_mJcazgl1VtsFAsvFqpdd_1uQCT55ZCwytLRkVGXlUigVFhYAwI61avGhIbuttUmWE2qyfH5p9mShf4C9'),(2,1,'sekian','dibawah1','e7ef293a709760f8cd27c970ce3de52e5b45c53d',2,'Pak Hasan','sekian',''),(3,2,'969685235','dibawah2','e7ef293a709760f8cd27c970ce3de52e5b45c53d',3,'Pak harun','08239636963','dsOLvn5OyAM:APA91bHtJvB74ofzvxH7mkLPR9raHiMJWiGk13-lPo2fgwwR9IsffzbSnUhTCX7w2O7euHh5vdZVLND-ojW2xyJhEbsy0-Eo9ZnaG85xqA08_iT1OPENF9YPZBh96o3Oy3zdfeHNlfs4'),(4,3,'5','dibawah3','e7ef293a709760f8cd27c970ce3de52e5b45c53d',4,'Gandhi','5','fVmayccU-rc:APA91bGVu7mrgyInbPhfHTRK7IXefMsnx-_g9OK_abM9LxvlMgeyfsPfxkp_mJcazgl1VtsFAsvFqpdd_1uQCT55ZCwytLRkVGXlUigVFhYAwI61avGhIbuttUmWE2qyfH5p9mShf4C9');
/*!40000 ALTER TABLE `tb_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-20  4:02:43
