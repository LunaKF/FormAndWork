-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: database:3306
-- Tiempo de generación: 25-03-2025 a las 15:59:30
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
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE `alumno` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `ape1` varchar(255) COLLATE utf32_unicode_ci NOT NULL,
  `ape2` varchar(255) COLLATE utf32_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `id_sector` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `candidatura`
--

CREATE TABLE `candidatura` (
  `id` bigint NOT NULL,
  `id_alumno` bigint NOT NULL,
  `id_oferta` bigint NOT NULL,
  `fecha` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `id_sector` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`id`, `nombre`, `email`, `id_sector`) VALUES
(1, 'EmpresaEjemplo', 'EmpresaEjemplo@gmail.com', 1),
(2, 'EMPRESA', 'AAAA@AAAA', 2),
(3, 'prueba', 'hola@gmail.com', 7),
(23, '5co', 'lunakhanjifustek@gmail.com', 5),
(24, 'pruebaluna', 'lupruebak@gmail.com', 8),
(25, 'prueba16:13', '1613luna@gmail.com', 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `oferta`
--

CREATE TABLE `oferta` (
  `id` bigint NOT NULL,
  `titulo` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `descripcion` varchar(555) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL,
  `id_empresa` bigint NOT NULL,
  `id_sector` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sector`
--

CREATE TABLE `sector` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) CHARACTER SET utf32 COLLATE utf32_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Volcado de datos para la tabla `sector`
--

INSERT INTO `sector` (`id`, `nombre`) VALUES
(1, 'Administración y gestión'),
(2, 'Agraria'),
(3, 'Artes gráficas'),
(4, 'Artes y artesanías'),
(5, 'Comercio y marketing'),
(6, 'Electricidad y electrónica'),
(7, 'Energía y agua'),
(8, 'Fabricación mecánica'),
(9, 'Hostelería y turismo'),
(10, 'Imagen personal'),
(11, 'Imagen y sonido'),
(12, 'Informática y comunicaciones'),
(13, 'Instalación y mantenimiento'),
(14, 'Madera, mueble y corcho'),
(15, 'Marítimo-pesquera'),
(16, 'Química'),
(17, 'Sanidad'),
(18, 'Seguridad y medio ambiente'),
(19, 'Servicios socioculturales y a la comunidad'),
(20, 'Textil, confección y piel'),
(21, 'Transporte y mantenimiento de vehículos'),
(22, 'Vidrio y cerámica');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `candidatura`
--
ALTER TABLE `candidatura`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `oferta`
--
ALTER TABLE `oferta`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `sector`
--
ALTER TABLE `sector`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alumno`
--
ALTER TABLE `alumno`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `candidatura`
--
ALTER TABLE `candidatura`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `oferta`
--
ALTER TABLE `oferta`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sector`
--
ALTER TABLE `sector`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
