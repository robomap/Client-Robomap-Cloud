-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Temps de generació: 22-05-2016 a les 21:31:16
-- Versió del servidor: 10.1.13-MariaDB
-- Versió de PHP: 5.5.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de dades: `espotifai`
--

-- --------------------------------------------------------

--
-- Estructura de la taula `follow`
--

CREATE TABLE `follow` (
  `id` int(11) NOT NULL,
  `following` int(11) NOT NULL,
  `follower` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de la taula `list`
--

CREATE TABLE `list` (
  `id` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de la taula `list_song`
--

CREATE TABLE `list_song` (
  `id` int(11) NOT NULL,
  `list` int(11) NOT NULL,
  `song` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de la taula `song`
--

CREATE TABLE `song` (
  `name` char(50) NOT NULL,
  `gender` char(20) NOT NULL,
  `album` char(50) NOT NULL,
  `artist` char(50) NOT NULL,
  `path` char(100) NOT NULL,
  `numStars` int(11) NOT NULL,
  `numReproductions` int(11) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de la taula `user`
--

CREATE TABLE `user` (
  `nickname` char(20) NOT NULL,
  `password` char(20) NOT NULL,
  `registerDate` date NOT NULL,
  `lastAccesDate` date NOT NULL,
  `numSongLists` int(11) NOT NULL,
  `totalSongs` int(11) NOT NULL,
  `numFollowers` int(11) NOT NULL,
  `numFollowing` int(11) NOT NULL,
  `mail` text NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexos per taules bolcades
--

--
-- Index de la taula `follow`
--
ALTER TABLE `follow`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_following` (`following`),
  ADD KEY `user_follower` (`follower`);

--
-- Index de la taula `list`
--
ALTER TABLE `list`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user` (`user`);

--
-- Index de la taula `list_song`
--
ALTER TABLE `list_song`
  ADD PRIMARY KEY (`id`),
  ADD KEY `list_id` (`list`),
  ADD KEY `song_id` (`song`);

--
-- Index de la taula `song`
--
ALTER TABLE `song`
  ADD PRIMARY KEY (`id`);

--
-- Index de la taula `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per les taules bolcades
--

--
-- AUTO_INCREMENT per la taula `follow`
--
ALTER TABLE `follow`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;
--
-- AUTO_INCREMENT per la taula `list`
--
ALTER TABLE `list`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT per la taula `list_song`
--
ALTER TABLE `list_song`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;
--
-- AUTO_INCREMENT per la taula `song`
--
ALTER TABLE `song`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=182;
--
-- AUTO_INCREMENT per la taula `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;
--
-- Restriccions per taules bolcades
--

--
-- Restriccions per la taula `list`
--
ALTER TABLE `list`
  ADD CONSTRAINT `list_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`);

--
-- Restriccions per la taula `list_song`
--
ALTER TABLE `list_song`
  ADD CONSTRAINT `list_song_ibfk_1` FOREIGN KEY (`list`) REFERENCES `list` (`id`),
  ADD CONSTRAINT `list_song_ibfk_2` FOREIGN KEY (`song`) REFERENCES `song` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
