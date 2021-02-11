-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2021. Feb 11. 19:55
-- Kiszolgáló verziója: 10.4.14-MariaDB
-- PHP verzió: 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `kiskosar`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `type` varchar(30) NOT NULL,
  `price` int(10) NOT NULL,
  `supplier_price` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `product`
--

INSERT INTO `product` (`id`, `name`, `type`, `price`, `supplier_price`) VALUES
(23, 'Samsung TV', 'OLED', 100000, 90000),
(24, 'SONY TV', 'UHD8K', 230000, 210000);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `suppliers`
--

CREATE TABLE `suppliers` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `contact` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `phone` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `suppliers`
--

INSERT INTO `suppliers` (`id`, `name`, `contact`, `email`, `phone`) VALUES
(6, 'SpeedTransfer Kft.', 'Kis Tamás', 'kiss.tamas@gmail.com', 21683151),
(7, 'SchnellProduct Inc.', 'Balázs Iván', 'balazs.ivan@gmail.com', 896516);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `suppliers_product`
--

CREATE TABLE `suppliers_product` (
  `id` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `suppliers_product`
--

INSERT INTO `suppliers_product` (`id`, `supplier_id`, `product_id`) VALUES
(6, 6, 23),
(7, 7, 24);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `suppliers_product`
--
ALTER TABLE `suppliers_product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cross_supplier` (`supplier_id`),
  ADD KEY `cross_product` (`product_id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT a táblához `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT a táblához `suppliers_product`
--
ALTER TABLE `suppliers_product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `suppliers_product`
--
ALTER TABLE `suppliers_product`
  ADD CONSTRAINT `cross_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `cross_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
