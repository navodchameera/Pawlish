-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 28, 2026 at 05:32 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pawlish`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE `appointment` (
  `id` int(11) NOT NULL,
  `pet_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  `groomer_id` int(11) NOT NULL,
  `appointment_date` date NOT NULL,
  `appointment_time` time NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`id`, `pet_id`, `service_id`, `groomer_id`, `appointment_date`, `appointment_time`, `status`) VALUES
(1, 1, 3, 1, '2026-11-02', '09:30:00', 'In progress'),
(2, 4, 1, 3, '2026-10-30', '13:30:00', 'Done'),
(3, 1, 2, 5, '2026-10-28', '09:00:00', 'Done'),
(6, 2, 3, 1, '2026-10-28', '09:00:00', 'In progress'),
(7, 1, 2, 3, '2026-10-28', '12:30:00', 'Done'),
(40, 1, 1, 1, '2026-08-19', '09:00:00', 'Done'),
(41, 2, 2, 3, '2026-08-22', '11:00:00', 'Done'),
(42, 3, 3, 5, '2026-08-25', '13:00:00', 'Done'),
(43, 1, 2, 6, '2026-08-28', '09:00:00', 'Done'),
(44, 2, 3, 7, '2026-08-31', '11:00:00', 'Done'),
(45, 3, 1, 8, '2026-09-03', '13:00:00', 'Done'),
(46, 1, 3, 1, '2026-09-06', '09:00:00', 'Cancelled'),
(47, 2, 1, 3, '2026-09-09', '11:00:00', 'Done'),
(48, 3, 2, 5, '2026-09-12', '13:00:00', 'Done'),
(49, 1, 1, 6, '2026-09-15', '09:00:00', 'Done'),
(50, 2, 2, 7, '2026-09-18', '11:00:00', 'Done'),
(51, 3, 3, 8, '2026-09-21', '13:00:00', 'Cancelled'),
(52, 1, 2, 1, '2026-09-28', '09:00:00', 'Done'),
(53, 2, 3, 3, '2026-10-01', '11:00:00', 'Done'),
(54, 3, 1, 5, '2026-10-04', '13:00:00', 'Done'),
(55, 1, 3, 6, '2026-10-07', '09:00:00', 'Done'),
(56, 2, 1, 7, '2026-10-10', '11:00:00', 'Done'),
(57, 3, 2, 8, '2026-10-13', '13:00:00', 'Done'),
(58, 1, 1, 1, '2026-10-16', '09:00:00', 'Done'),
(59, 2, 2, 3, '2026-10-18', '11:00:00', 'Cancelled'),
(60, 3, 3, 5, '2026-10-20', '13:00:00', 'Done'),
(61, 1, 2, 6, '2026-10-22', '09:00:00', 'Done'),
(62, 2, 3, 7, '2026-10-24', '11:00:00', 'Done'),
(63, 3, 1, 8, '2026-10-26', '13:00:00', 'Done'),
(64, 1, 1, 1, '2026-10-28', '08:00:00', 'Done'),
(65, 2, 2, 3, '2026-10-28', '09:30:00', 'Done'),
(66, 3, 3, 5, '2026-10-28', '11:00:00', 'Done'),
(67, 1, 2, 6, '2026-10-28', '12:30:00', 'Done'),
(68, 2, 3, 7, '2026-10-28', '14:00:00', 'Done'),
(69, 3, 1, 8, '2026-10-28', '15:30:00', 'Done'),
(70, 1, 3, 7, '2026-10-28', '17:00:00', 'In progress'),
(71, 2, 2, 8, '2026-10-28', '18:30:00', 'Booked');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL,
  `address` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `name`, `phone`, `email`, `address`) VALUES
(1, 'Nimal Perera', '0770000001', 'nimal@example.com', '12 Temple Road, Galle'),
(2, 'Kasuni Silva', '0710000002', 'kasuni@example.com', '45 Main Street, Matara'),
(3, 'Amal Fernando', '0760000003', 'amal@example.com', '78 Beach Road, Hikkaduwa'),
(4, 'Dilini Jayasinghe', '0750000004', 'dilini@example.com', '23 Flower Road, Colombo'),
(5, 'Sahan Kumara', '0700000005', 'sahan@example.com', '56 Lake Road, Kandy'),
(6, 'Tharushi Dias', '0780000006', 'tharushi@example.com', '89 Station Road, Ambalangoda'),
(7, 'Ravindu De Silva', '0720000007', 'ravindu@example.com', '34 Church Road, Negombo'),
(8, 'Ishara Wickramasinghe', '0770000008', 'ishara@example.com', '67 School Lane, Unawatuna'),
(9, 'Chamod Senanayake', '0710000009', 'chamod@example.com', '90 Hill Road, Badulla'),
(10, 'Nethmi Gunawardena', '0760000010', 'nethmi@example.com', '15 Park Avenue, Tangalle'),
(12, 'Navod Chameera', '1234567890', 'navod@mail.com', 'Galle');

-- --------------------------------------------------------

--
-- Table structure for table `pet`
--

CREATE TABLE `pet` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `species` varchar(30) NOT NULL,
  `breed` varchar(50) NOT NULL,
  `birthday` date NOT NULL,
  `weight` decimal(10,0) NOT NULL,
  `owner_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pet`
--

INSERT INTO `pet` (`id`, `name`, `species`, `breed`, `birthday`, `weight`, `owner_id`) VALUES
(1, 'Buddy', 'Dog', 'Golden Retriever', '2022-03-15', 28, 1),
(2, 'Luna', 'Cat', 'Persian', '2023-06-20', 4, 2),
(3, 'Max', 'Dog', 'German Shepherd', '2021-11-10', 32, 3),
(4, 'Milo', 'Cat', 'Siamese', '2024-01-05', 3, 4),
(5, 'Bella', 'Dog', 'Labrador Retriever', '2022-08-12', 25, 5),
(6, 'Coco', 'Rabbit', 'Holland Lop', '2024-04-18', 2, 6),
(7, 'Rocky', 'Dog', 'Rottweiler', '2020-09-25', 42, 7),
(8, 'Simba', 'Cat', 'Domestic Shorthair', '2023-12-08', 5, 8),
(9, 'Daisy', 'Dog', 'Beagle', '2023-02-14', 10, 9);

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `duration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`id`, `name`, `description`, `price`, `duration`) VALUES
(1, 'Full Grooming', 'Bath, haircut, nail trimming and ear cleaning', 4500.00, 90),
(2, 'Bath and Blow Dry', 'Shampoo bath and coat drying', 2500.00, 60),
(3, 'Nail Trimming', 'Trim and smooth pet nails', 800.00, 15);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(64) NOT NULL,
  `firstName` varchar(100) NOT NULL,
  `LastName` varchar(100) NOT NULL,
  `Phone` varchar(10) NOT NULL,
  `Address` varchar(500) NOT NULL,
  `role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `firstName`, `LastName`, `Phone`, `Address`, `role`) VALUES
(1, 'admin', 'admin123', 'Admin', 'Admin', '0771234567', 'Kandy', 'ADMIN'),
(3, 'sam', 'pawlish123', 'Samadhi', 'Diwyanjali', '0987654321', 'Galle', 'ADMIN'),
(5, 'navod', 'navod123', 'Navod', 'Chameera', '1234567890', 'Galle', 'ADMIN'),
(6, 'sachintha', 'sachintha123', 'Sachintha', 'Nimasara', '1234567890', 'Galle', 'EMPLOYEE'),
(7, 'kasun', 'staff123', 'Kasun', 'Perera', '0770000001', 'Demo address, Galle', 'EMPLOYEE'),
(8, 'tharushi', 'staff123', 'Tharushi', 'Silva', '0770000002', 'Demo address, Matara', 'EMPLOYEE'),
(10, 'test', 'test123', 'Alex', 'Roin', '1234567890', 'Galle', 'EMPLOYEE');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_appointment_pet` (`pet_id`),
  ADD KEY `fk_appointment_service` (`service_id`),
  ADD KEY `fk_appointment_groomer` (`groomer_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pet`
--
ALTER TABLE `pet`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pet_owner` (`owner_id`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointment`
--
ALTER TABLE `appointment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `pet`
--
ALTER TABLE `pet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `appointment`
--
ALTER TABLE `appointment`
  ADD CONSTRAINT `fk_appointment_groomer` FOREIGN KEY (`groomer_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_appointment_pet` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_appointment_service` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pet`
--
ALTER TABLE `pet`
  ADD CONSTRAINT `fk_pet_owner` FOREIGN KEY (`owner_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
