-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 17, 2021 at 12:03 PM
-- Server version: 10.4.20-MariaDB
-- PHP Version: 8.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `training-spring-boot-db`
--

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(15);

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `APPLICATION` varchar(20) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `LOG_DATE` date NOT NULL DEFAULT current_timestamp(),
  `LOGGER` varchar(250) NOT NULL,
  `LOG_LEVEL` varchar(10) NOT NULL,
  `MESSAGE` varchar(4000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE `permission` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `permission`
--

INSERT INTO `permission` (`id`, `name`) VALUES
(2, 'CREATE'),
(4, 'DELETE'),
(1, 'READ'),
(3, 'UPDATE');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `name` varchar(255) NOT NULL,
  `updated_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `created_date`, `name`, `updated_date`) VALUES
(1, '2021-08-07', 'FRESHER', '2021-08-17'),
(2, '2021-08-09', 'JUNIOR', '2021-08-12'),
(3, '2021-08-10', 'SENIOR', '2021-08-16'),
(4, '2021-08-10', 'MANAGER', '2021-08-17');

-- --------------------------------------------------------

--
-- Table structure for table `role_permission`
--

CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `role_permission`
--

INSERT INTO `role_permission` (`role_id`, `permission_id`) VALUES
(2, 1),
(2, 2),
(3, 1),
(3, 2),
(3, 3),
(4, 1),
(4, 2),
(4, 3),
(4, 4),
(1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `password` varchar(255) NOT NULL,
  `updated_date` date NOT NULL,
  `username` varchar(255) NOT NULL,
  `role` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `created_date`, `password`, `updated_date`, `username`, `role`) VALUES
(1, '2021-08-06', '$2a$12$5j2iLFyZyo47tzcrgNiedO4wxNjGPaeIIBOoD575yrICb5cHaDhYy', '2021-08-12', 'node', 4),
(2, '2021-08-08', '$2a$10$Yw30Z0bYnpDuOeLt1x4aPOgDmgrJtPgjo2sB2TQei6SaI0.c0povu', '2021-08-16', 'NODETHUHAIii', 2),
(6, '2021-08-10', '$2a$10$.OML8MAsL74bTIfQd..TZ.2Q1IEPmnVE4etEIvouyQlzqAvYn1ADe', '2021-08-17', 'node5', 1),
(10, '2021-08-16', '$2a$10$g2iOJYEZDwf4lIFjTSYUY.LtZ/hmF0WGjVx8.Mmi.pZrHNrRwONC6', '2021-08-17', 'node9', 2),
(13, '2021-08-17', '$2a$10$l6kV9naRLGnlCgNKgEUdsefhxhmQQQKrhtz31qeyePQw1cGZEaI22', '2021-08-17', 'node15', 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `permission`
--
ALTER TABLE `permission`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_2ojme20jpga3r4r79tdso17gi` (`name`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`);

--
-- Indexes for table `role_permission`
--
ALTER TABLE `role_permission`
  ADD KEY `FKf8yllw1ecvwqy3ehyxawqa1qp` (`permission_id`),
  ADD KEY `FKa6jx8n8xkesmjmv6jqug6bg68` (`role_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  ADD KEY `FKl5alypubd40lwejc45vl35wjb` (`role`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `permission`
--
ALTER TABLE `permission`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `role_permission`
--
ALTER TABLE `role_permission`
  ADD CONSTRAINT `FKa6jx8n8xkesmjmv6jqug6bg68` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `FKf8yllw1ecvwqy3ehyxawqa1qp` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKl5alypubd40lwejc45vl35wjb` FOREIGN KEY (`role`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
