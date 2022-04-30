-- phpMyAdmin SQL Dump
-- version 3.5.8.2
-- http://www.phpmyadmin.net
--
-- Host: md333.wedos.net:3306
-- Generation Time: Apr 30, 2022 at 11:34 AM
-- Server version: 10.3.27-MariaDB-log
-- PHP Version: 5.4.23

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `d298683_ak8po`
--

-- --------------------------------------------------------

--
-- Table structure for table `games`
--

CREATE TABLE IF NOT EXISTS `games` (
  `id_game` int(11) NOT NULL AUTO_INCREMENT,
  `game_title` varchar(16) COLLATE utf8_czech_ci DEFAULT NULL,
  `white_player` varchar(16) COLLATE utf8_czech_ci DEFAULT NULL,
  `black_player` varchar(16) COLLATE utf8_czech_ci DEFAULT NULL,
  `game_state` enum('new','ongoing','finished','daily') COLLATE utf8_czech_ci NOT NULL DEFAULT 'new',
  `initial_fen` varchar(100) COLLATE utf8_czech_ci NOT NULL DEFAULT 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1',
  PRIMARY KEY (`id_game`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `moves`
--

CREATE TABLE IF NOT EXISTS `moves` (
  `id_move` int(11) NOT NULL AUTO_INCREMENT,
  `order` int(11) NOT NULL,
  `half_move` tinyint(1) NOT NULL DEFAULT 0,
  `src_row` int(11) NOT NULL,
  `src_col` enum('a','b','c','d','e','f','g','h') COLLATE utf8_czech_ci NOT NULL,
  `trg_row` int(11) NOT NULL,
  `trg_col` enum('a','b','c','d','e','f','g','h') COLLATE utf8_czech_ci NOT NULL,
  `id_game` int(11) NOT NULL,
  PRIMARY KEY (`id_move`),
  KEY `id_game` (`id_game`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci AUTO_INCREMENT=3 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `moves`
--
ALTER TABLE `moves`
  ADD CONSTRAINT `moves_ibfk_2` FOREIGN KEY (`id_game`) REFERENCES `games` (`id_game`) ON DELETE CASCADE ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
