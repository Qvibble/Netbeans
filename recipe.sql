-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 11, 2021 at 02:27 PM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `recipe`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(9, 'Efterrätt'),
(4, 'Fisk'),
(2, 'Fläsk'),
(7, 'Förrätt'),
(3, 'Kyckling'),
(1, 'Nöt'),
(8, 'Varmrätt'),
(6, 'Veganskt'),
(5, 'Vegetariskt');

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `id` int(11) NOT NULL,
  `content` varchar(256) NOT NULL,
  `date` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`id`, `content`, `date`) VALUES
(1, 'comment 1', '2020-1212-1'),
(2, 'kommentar 2', '2021- 56 -6'),
(6, 'koemmt 6', 'datum'),
(17, 'ja 17', '20554-05-5'),
(18, 'this is a comment', '2021/03/04 - 15:07'),
(19, 'this is another comment', '2021/03/04 - 15:24'),
(20, 'test comment', '2021/03/04 - 17:16');

-- --------------------------------------------------------

--
-- Table structure for table `comment_user`
--

CREATE TABLE `comment_user` (
  `comment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `comment_user`
--

INSERT INTO `comment_user` (`comment_id`, `user_id`) VALUES
(1, 1),
(2, 1),
(6, 2),
(17, 3),
(18, 1),
(19, 1),
(20, 4);

-- --------------------------------------------------------

--
-- Table structure for table `ingredients`
--

CREATE TABLE `ingredients` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ingredients`
--

INSERT INTO `ingredients` (`id`, `name`) VALUES
(6, 'Asd'),
(10, 'Asdasd'),
(7, 'Bröd'),
(2, 'bulle'),
(4, 'Katt'),
(3, 'Kokosnöt'),
(1, 'Kött'),
(11, 'Mjöl'),
(8, 'Nudlar'),
(5, 'Rtytry'),
(9, 'Vatten');

-- --------------------------------------------------------

--
-- Table structure for table `likes`
--

CREATE TABLE `likes` (
  `user_id` int(11) NOT NULL,
  `recipe_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `recipes`
--

CREATE TABLE `recipes` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `steps` varchar(1024) NOT NULL,
  `image` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `recipes`
--

INSERT INTO `recipes` (`id`, `name`, `description`, `steps`, `image`) VALUES
(8, 'Köttbullar', 'Kött och bullar', '1.Köp kött,2.Köttbullar', 'bild.png'),
(9, 'as', 'asdsa', '1.dwddw|', 'C:\\fakepath\\alogo.png'),
(10, 'thdt', '6trfgh', '1.k,jk|', 'C:\\fakepath\\heart.png'),
(14, 'gaag', 'aaaaa', '1.asdasdsa|', 'images/1eXOQW5M.png'),
(15, 'daw', 'awd', '1.dfg|', 'images/ikO6VUOj.png'),
(16, 'sw', 'asd', '1.wad|', 'images/gWPEG5Rg.png'),
(17, 'iug', 'sdf', '1.sdf|', 'images/CxHoLntg.png'),
(18, 'Ramen', 'Det är nudlar', '1.Koka vatten|2.Lägg i nudlar|3.Ät nudlar|', 'images/nFZJCFU3.png'),
(19, 'Masj', 'asdasd', '1.asdas|', 'images/n1TWnKAF.png'),
(20, 'dsasa', 'asd', '1.asd|', 'images/AUEENqmH.png'),
(21, 'asdsad', 'sadsad', '1.asdsad|', 'images/fVq598aP.png'),
(22, 'Kaka', 'kaka', '1.mjöööööööööööööl|', 'images/8+8Nx59N.png');

-- --------------------------------------------------------

--
-- Table structure for table `recipe_categories`
--

CREATE TABLE `recipe_categories` (
  `recipe_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `recipe_categories`
--

INSERT INTO `recipe_categories` (`recipe_id`, `category_id`) VALUES
(22, 2),
(22, 5),
(22, 9);

-- --------------------------------------------------------

--
-- Table structure for table `recipe_comments`
--

CREATE TABLE `recipe_comments` (
  `recipe_id` int(11) NOT NULL,
  `comment_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `recipe_comments`
--

INSERT INTO `recipe_comments` (`recipe_id`, `comment_id`) VALUES
(1, 1),
(1, 2),
(1, 6),
(1, 17),
(2, 12),
(2, 19),
(2, 20);

-- --------------------------------------------------------

--
-- Table structure for table `recipe_ingredients`
--

CREATE TABLE `recipe_ingredients` (
  `recipe_id` int(11) NOT NULL,
  `amount` varchar(64) NOT NULL,
  `ingredient_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `recipe_ingredients`
--

INSERT INTO `recipe_ingredients` (`recipe_id`, `amount`, `ingredient_id`) VALUES
(8, '2kg', 1),
(8, '2dl', 2),
(8, '1', 3),
(9, '2 kg', 4),
(10, '67676', 5),
(11, 'asd', 6),
(12, 'asd', 6),
(13, 'asd', 6),
(14, '2 kg', 7),
(15, '1 dl', 7),
(16, '2 kg', 7),
(17, '54 dl', 7),
(18, '1 kg', 8),
(18, '17 dl', 9),
(19, '2 kg', 7),
(20, '2 kg', 6),
(21, 'asdsda', 10),
(22, '2 kg', 11);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(128) NOT NULL,
  `mail` varchar(128) NOT NULL,
  `hashed_password` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `mail`, `hashed_password`) VALUES
(1, 'BEpios', 'awdawd@fdwa.com', '123'),
(2, 'asnda', 'awdawd@daw.com', ''),
(3, 'ismeuser', 'wadas@asd.com', ''),
(4, 'newTestUser', 'test@mail.com', '$2a$12$oQjEkjohN5nvd3PJ5jNSWO34mp6isMWpyGqomN4f9eJqOglGyF3t6'),
(5, 'test', 'asd@mail.ob', '$2a$12$9yQOcnZn0oi4w1TgGdeZz.TWn0xr8Juo.zEMQSxAGXmyNCsK2MO0.'),
(6, 'abc', 'test@maiul.com', '$2a$12$pZ03XH.HStvbMpuXYCxTCuZ.LtLN1CTPEvOQtSjJ51YpygP6GKJAa'),
(7, 'bba', 'tese@mail.com', '$2a$12$0x6Eqcw9bqIjpwFOVV7S4uaHZ0.1m.iMWfkj0zzfTFuDMvJlat.Ai'),
(8, 'aaa', 'asd@mail.ob', '$2a$12$HRMSSk83sEGO3vQT.2cyUe3QUqh644X2SrkcDgyUCM8e3vlJPIF5q');

-- --------------------------------------------------------

--
-- Table structure for table `user_recipes`
--

CREATE TABLE `user_recipes` (
  `user_id` int(11) NOT NULL,
  `recipe_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_recipes`
--

INSERT INTO `user_recipes` (`user_id`, `recipe_id`) VALUES
(4, 8),
(6, 9),
(6, 10),
(4, 14),
(4, 15),
(4, 16),
(4, 17),
(4, 18),
(4, 19),
(4, 20),
(4, 21),
(4, 22);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `comment_user`
--
ALTER TABLE `comment_user`
  ADD PRIMARY KEY (`comment_id`);

--
-- Indexes for table `ingredients`
--
ALTER TABLE `ingredients`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `recipes`
--
ALTER TABLE `recipes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`username`);

--
-- Indexes for table `user_recipes`
--
ALTER TABLE `user_recipes`
  ADD UNIQUE KEY `recipe_id` (`recipe_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `ingredients`
--
ALTER TABLE `ingredients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `recipes`
--
ALTER TABLE `recipes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
