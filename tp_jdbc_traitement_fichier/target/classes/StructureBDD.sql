-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 01 août 2020 à 22:36
-- Version du serveur :  10.4.13-MariaDB
-- Version de PHP : 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

DROP DATABASE `traitement_fichier` ;

--
-- Base de données : `traitement_fichier`
--
CREATE DATABASE IF NOT EXISTS `traitement_fichier` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `traitement_fichier`;

-- --------------------------------------------------------

--
-- Structure de la table `additif`
--

DROP TABLE IF EXISTS `additif`;
CREATE TABLE `additif` (
  `id_Additif` int(11) NOT NULL,
  `nom_Additif` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `allergene`
--

DROP TABLE IF EXISTS `allergene`;
CREATE TABLE `allergene` (
  `id_Allergene` int(11) NOT NULL,
  `nom_Allergene` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

DROP TABLE IF EXISTS `categorie`;
CREATE TABLE `categorie` (
  `id_Categorie` int(11) NOT NULL,
  `nom_Categorie` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
CREATE TABLE `ingredient` (
  `id_Ingredient` int(11) NOT NULL,
  `nom_Ingredient` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `jointure_prod_additif`
--

DROP TABLE IF EXISTS `jointure_prod_additif`;
CREATE TABLE `jointure_prod_additif` (
  `id_Produit` int(11) NOT NULL,
  `id_Additif` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `jointure_prod_allergene`
--

DROP TABLE IF EXISTS `jointure_prod_allergene`;
CREATE TABLE `jointure_prod_allergene` (
  `id_Produit` int(11) NOT NULL,
  `id_Allergene` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `jointure_prod_ingredient`
--

DROP TABLE IF EXISTS `jointure_prod_ingredient`;
CREATE TABLE `jointure_prod_ingredient` (
  `id_Produit` int(11) NOT NULL,
  `id_Ingredient` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `jointure_prod_marque`
--

DROP TABLE IF EXISTS `jointure_prod_marque`;
CREATE TABLE `jointure_prod_marque` (
  `id_Produit` int(11) NOT NULL,
  `id_Marque` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `marque`
--

DROP TABLE IF EXISTS `marque`;
CREATE TABLE `marque` (
  `id_Marque` int(11) NOT NULL,
  `nom_Marque` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE `produit` (
  `id_Produit` int(11) NOT NULL,
  `nom_Produit` varchar(500) NOT NULL,
  `grade_Nutri_Produit` varchar(10) NOT NULL,
  `id_Categorie` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `additif`
--
ALTER TABLE `additif`
  ADD PRIMARY KEY (`id_Additif`),
  ADD UNIQUE KEY `nom_Additif` (`nom_Additif`);

--
-- Index pour la table `allergene`
--
ALTER TABLE `allergene`
  ADD PRIMARY KEY (`id_Allergene`);

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`id_Categorie`);

--
-- Index pour la table `ingredient`
--
ALTER TABLE `ingredient`
  ADD PRIMARY KEY (`id_Ingredient`),
  ADD UNIQUE KEY `nom_Ingredient` (`nom_Ingredient`) USING HASH;

--
-- Index pour la table `jointure_prod_additif`
--
ALTER TABLE `jointure_prod_additif`
  ADD KEY `FK_Additif` (`id_Additif`),
  ADD KEY `FK_Produit_Additif` (`id_Produit`);

--
-- Index pour la table `jointure_prod_allergene`
--
ALTER TABLE `jointure_prod_allergene`
  ADD KEY `FK_Produit` (`id_Produit`),
  ADD KEY `FK_Allergene` (`id_Allergene`);

--
-- Index pour la table `jointure_prod_ingredient`
--
ALTER TABLE `jointure_prod_ingredient`
  ADD KEY `FK_Ingredient` (`id_Ingredient`),
  ADD KEY `FK_Produit_Ingredient` (`id_Produit`);

--
-- Index pour la table `jointure_prod_marque`
--
ALTER TABLE `jointure_prod_marque`
  ADD KEY `FK_Marque` (`id_Marque`),
  ADD KEY `FK_Produit_Marque` (`id_Produit`);

--
-- Index pour la table `marque`
--
ALTER TABLE `marque`
  ADD PRIMARY KEY (`id_Marque`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id_Produit`),
  ADD UNIQUE KEY `nom_Produit` (`nom_Produit`),
  ADD KEY `FK_Categorie` (`id_Categorie`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `additif`
--
ALTER TABLE `additif`
  MODIFY `id_Additif` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `allergene`
--
ALTER TABLE `allergene`
  MODIFY `id_Allergene` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `categorie`
--
ALTER TABLE `categorie`
  MODIFY `id_Categorie` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `ingredient`
--
ALTER TABLE `ingredient`
  MODIFY `id_Ingredient` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `marque`
--
ALTER TABLE `marque`
  MODIFY `id_Marque` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id_Produit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `jointure_prod_additif`
--
ALTER TABLE `jointure_prod_additif`
  ADD CONSTRAINT `FK_Additif` FOREIGN KEY (`id_Additif`) REFERENCES `additif` (`id_Additif`),
  ADD CONSTRAINT `FK_Produit_Additif` FOREIGN KEY (`id_Produit`) REFERENCES `produit` (`id_Produit`);

--
-- Contraintes pour la table `jointure_prod_allergene`
--
ALTER TABLE `jointure_prod_allergene`
  ADD CONSTRAINT `FK_Allergene` FOREIGN KEY (`id_Allergene`) REFERENCES `allergene` (`id_Allergene`),
  ADD CONSTRAINT `FK_Produit` FOREIGN KEY (`id_Produit`) REFERENCES `produit` (`id_Produit`);

--
-- Contraintes pour la table `jointure_prod_ingredient`
--
ALTER TABLE `jointure_prod_ingredient`
  ADD CONSTRAINT `FK_Ingredient` FOREIGN KEY (`id_Ingredient`) REFERENCES `ingredient` (`id_Ingredient`),
  ADD CONSTRAINT `FK_Produit_Ingredient` FOREIGN KEY (`id_Produit`) REFERENCES `produit` (`id_Produit`);

--
-- Contraintes pour la table `jointure_prod_marque`
--
ALTER TABLE `jointure_prod_marque`
  ADD CONSTRAINT `FK_Marque` FOREIGN KEY (`id_Marque`) REFERENCES `marque` (`id_Marque`),
  ADD CONSTRAINT `FK_Produit_Marque` FOREIGN KEY (`id_Produit`) REFERENCES `produit` (`id_Produit`);

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `FK_Categorie` FOREIGN KEY (`id_Categorie`) REFERENCES `categorie` (`id_Categorie`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
