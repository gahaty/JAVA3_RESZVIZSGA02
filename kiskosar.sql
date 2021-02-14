-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2021. Feb 14. 11:18
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
(42, 'Samsung TV', '4K', 100000, 95000),
(43, 'Sony Bravia TV', 'FullHD', 138000, 138000),
(49, 'LG', '8K', 200000, 180000);

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
(7, 'SchnellProduct Inc.', 'Balázs Iván', 'balazs.ivan@gmail.com', 896516),
(24, 'Nimfas Corp.', 'Charlie Firpo', 'shipping@nimfas.com', 777888999),
(25, 'Coimbra\'s Inc.', 'Bastiano Coimbra', 'shipping@coimbras.com', 666777888),
(26, 'K1 Corp.', 'Mr. K1', 'shipping@k1.com', 555666777),
(27, 'Futrinka Inc.', 'Futrinka Jenő', 'shipping@futrinka.hu', 444555666),
(28, 'Fastest Sprinter Corp.', 'Barry Allen', 'shipping@fsprinter.com', 333444555),
(29, 'GreenSmash Inc.', 'Bruce Banner', 'shipping@gsmash.com', 222333444),
(30, 'Stark Industries Inc.', 'Tony Stark', 'shipping@sindustries.com', 111222333),
(31, 'SuperSuper Corp.', 'Clark Kent', 'shipping@supersuper.com', 123456789);

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
(18, 6, 42),
(19, 6, 43),
(25, 6, 49);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT a táblához `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT a táblához `suppliers_product`
--
ALTER TABLE `suppliers_product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

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
