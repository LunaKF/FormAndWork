-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: database:3306
-- Tiempo de generación: 16-01-2025 a las 19:03:02
-- Versión del servidor: 8.4.3
-- Versión de PHP: 8.2.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `FormAndWork`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Alumnos`
--

CREATE TABLE `Alumnos` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) COLLATE utf32_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf32_unicode_ci NOT NULL,
  `sector` varchar(255) COLLATE utf32_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Candidaturas`
--

CREATE TABLE `Candidaturas` (
  `idAlumno` int NOT NULL,
  `idOferta` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) COLLATE utf32_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf32_unicode_ci NOT NULL,
  `sector` varchar(255) COLLATE utf32_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Ofertas`
--

CREATE TABLE `Ofertas` (
  `Titulo` varchar(255) COLLATE utf32_unicode_ci NOT NULL,
  `Descripcion` varchar(555) COLLATE utf32_unicode_ci NOT NULL,
  `IdEmpresa` int NOT NULL,
  `IdOferta` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Alumnos`
--
ALTER TABLE `Alumnos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `Candidaturas`
--
ALTER TABLE `Candidaturas`
  ADD PRIMARY KEY (`idAlumno`,`idOferta`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `Ofertas`
--
ALTER TABLE `Ofertas`
  ADD PRIMARY KEY (`IdOferta`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `Alumnos`
--
ALTER TABLE `Alumnos`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `Ofertas`
--
ALTER TABLE `Ofertas`
  MODIFY `IdOferta` bigint NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
