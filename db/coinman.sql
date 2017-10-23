-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 23, 2017 lúc 02:22 CH
-- Phiên bản máy phục vụ: 5.7.14
-- Phiên bản PHP: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `coinman`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `coinbot`
--

CREATE TABLE `coinbot` (
  `id` bigint(20) NOT NULL,
  `account_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `account_password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  `buy_limit` int(11) DEFAULT '0',
  `coin_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `first_price` double DEFAULT '0',
  `is_bought` tinyint(1) DEFAULT '0',
  `last_price` double DEFAULT '0',
  `last_price_got` double DEFAULT '0',
  `platform` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sell_limit` int(11) DEFAULT '0',
  `volume` double DEFAULT '0',
  `user_id` bigint(20) DEFAULT NULL,
  `last_mod_date` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `coinbot`
--

INSERT INTO `coinbot` (`id`, `account_name`, `account_password`, `active`, `buy_limit`, `coin_code`, `first_price`, `is_bought`, `last_price`, `last_price_got`, `platform`, `sell_limit`, `volume`, `user_id`, `last_mod_date`) VALUES
(2, 'test123', 'test', 1, 5, 'LTC', NULL, 0, NULL, NULL, 'Bittrex', 6, 100, 1, '2017-10-23 21:16:59'),
(3, 'a', 'b', 1, 5, 'ETH', NULL, 0, NULL, NULL, 'abc', 5, 100, 1, '2017-10-23 21:20:10'),
(4, NULL, NULL, 1, 6, 'TX', NULL, 0, NULL, NULL, 'aaa', 6, 100, 1, '2017-10-23 21:21:13');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `coinbot_aud`
--

CREATE TABLE `coinbot_aud` (
  `id` bigint(20) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` tinyint(4) DEFAULT NULL,
  `account_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `account_password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  `buy_limit` int(11) DEFAULT '0',
  `coin_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `first_price` double DEFAULT '0',
  `is_bought` tinyint(1) DEFAULT '0',
  `last_price` double DEFAULT '0',
  `last_price_got` double DEFAULT '0',
  `platform` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sell_limit` int(11) DEFAULT '0',
  `volume` double DEFAULT '0',
  `last_mod_date` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `coinbot_aud`
--

INSERT INTO `coinbot_aud` (`id`, `rev`, `revtype`, `account_name`, `account_password`, `active`, `buy_limit`, `coin_code`, `first_price`, `is_bought`, `last_price`, `last_price_got`, `platform`, `sell_limit`, `volume`, `last_mod_date`) VALUES
(2, 1, 0, 'test', 'test', 1, 5, 'LTC', NULL, NULL, NULL, NULL, 'bittrex', 5, 100, NULL),
(2, 2, 1, 'test', 'test', 1, 5, 'LTC', NULL, NULL, NULL, NULL, 'Bittrex', 5, 100, NULL),
(2, 3, 1, 'test', 'test', 1, 5, 'LTC', NULL, NULL, NULL, NULL, 'Bittrex', 6, 100, NULL),
(2, 4, 1, 'test123', 'test', 1, 5, 'LTC', NULL, NULL, NULL, NULL, 'Bittrex', 6, 100, '2017-10-23 21:16:59'),
(3, 5, 0, NULL, NULL, 1, 5, 'ETH', NULL, NULL, NULL, NULL, 'abc', 5, 100, '2017-10-23 21:18:04'),
(3, 6, 1, 'a', 'b', 1, 5, 'ETH', NULL, NULL, NULL, NULL, 'abc', 5, 100, '2017-10-23 21:20:10'),
(4, 7, 0, NULL, NULL, 1, 6, 'TX', NULL, 0, NULL, NULL, 'aaa', 6, 100, '2017-10-23 21:21:13');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `revinfo`
--

CREATE TABLE `revinfo` (
  `rev` int(11) NOT NULL,
  `revtstmp` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `revinfo`
--

INSERT INTO `revinfo` (`rev`, `revtstmp`) VALUES
(1, 1508767662979),
(2, 1508767697057),
(3, 1508767913994),
(4, 1508768218799),
(5, 1508768284156),
(6, 1508768410040),
(7, 1508768473398);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'User'),
(2, 'Guest');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `username`) VALUES
(1, NULL, '$2a$10$xoihGSMBtmDwVantDFZmGupp/fMNGk0UsYgxqrccX/EZWVlw2dYKO', 'hiepnvh'),
(2, NULL, '$2a$10$6yGn0iTZAmed28/yLLYRgeEhZM4BPQWhSD5hf6QXd0BGhS0k4MVsC', 'tungle');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_role`
--

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `coinbot`
--
ALTER TABLE `coinbot`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp2sl7h9is3wsaxm7xq74t88ld` (`user_id`);

--
-- Chỉ mục cho bảng `coinbot_aud`
--
ALTER TABLE `coinbot_aud`
  ADD PRIMARY KEY (`id`,`rev`),
  ADD KEY `FKrkfds466nb1h3molq6pqxssl` (`rev`);

--
-- Chỉ mục cho bảng `revinfo`
--
ALTER TABLE `revinfo`
  ADD PRIMARY KEY (`rev`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `coinbot`
--
ALTER TABLE `coinbot`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT cho bảng `revinfo`
--
ALTER TABLE `revinfo`
  MODIFY `rev` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT cho bảng `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
