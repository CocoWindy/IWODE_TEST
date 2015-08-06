-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Aug 06, 2015 at 11:44 AM
-- Server version: 5.5.42
-- PHP Version: 5.6.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `iwode`
--

-- --------------------------------------------------------

--
-- Table structure for table `iwode_booking`
--

CREATE TABLE `iwode_booking` (
  `id` int(11) NOT NULL,
  `orderNum` varchar(50) CHARACTER SET utf8 NOT NULL,
  `ownerId` int(11) NOT NULL,
  `userName` varchar(50) CHARACTER SET utf8 NOT NULL,
  `phone` varchar(50) CHARACTER SET utf8 NOT NULL,
  `startTime` varchar(50) CHARACTER SET utf8 NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 NOT NULL,
  `height` varchar(50) CHARACTER SET utf8 NOT NULL,
  `weight` varchar(50) CHARACTER SET utf8 NOT NULL,
  `status` int(11) NOT NULL COMMENT '0：正在预约；1：预约取消；2：预约成功-已沟通；3：服务中；4：预约完成',
  `tailorId` int(11) NOT NULL,
  `isComment` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `iwode_coupon`
--

CREATE TABLE `iwode_coupon` (
  `id` int(11) NOT NULL,
  `ownerId` int(11) NOT NULL,
  `code` varchar(50) CHARACTER SET utf8 NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `endTime` varchar(50) CHARACTER SET utf8 NOT NULL,
  `status` int(11) NOT NULL COMMENT '0：表示可用；1：表示已使用；2表示已过期'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `iwode_member_user`
--

CREATE TABLE `iwode_member_user` (
  `id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `phone` varchar(50) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `iwode_member_user`
--

INSERT INTO `iwode_member_user` (`id`, `name`, `phone`) VALUES
(2, '12341234123', '12341234123');

-- --------------------------------------------------------

--
-- Table structure for table `iwode_tailor`
--

CREATE TABLE `iwode_tailor` (
  `id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `phone` varchar(50) CHARACTER SET utf8 NOT NULL,
  `avatar` longblob
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `iwode_tailor`
--

INSERT INTO `iwode_tailor` (`id`, `name`, `phone`, `avatar`) VALUES
(1, 'Tied', '13570458168', NULL),
(2, 'Sasa', '15815107631', NULL),
(3, 'Don', '13433309163', NULL),
(4, 'Apple', '13006283941', NULL),
(5, 'Coco', '15017023289', NULL),
(6, 'Amy', '12345678901', NULL),
(7, 'Connie', '00000000000', NULL),
(8, 'Lemon', '11111111111', NULL),
(9, 'Suki', '99999999999', NULL),
(10, 'Rebecca', '13660284237', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `iwode_wechat_link`
--

CREATE TABLE `iwode_wechat_link` (
  `id` int(11) NOT NULL,
  `openId` varchar(50) CHARACTER SET utf8 NOT NULL,
  `unionId` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `memberuserId` int(11) NOT NULL,
  `modifyTime` varchar(20) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `iwode_wechat_link`
--

INSERT INTO `iwode_wechat_link` (`id`, `openId`, `unionId`, `memberuserId`, `modifyTime`, `status`) VALUES
(3, '123456', NULL, 2, '2015-08-04 11:51:01', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `iwode_booking`
--
ALTER TABLE `iwode_booking`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `iwode_coupon`
--
ALTER TABLE `iwode_coupon`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `iwode_member_user`
--
ALTER TABLE `iwode_member_user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `iwode_tailor`
--
ALTER TABLE `iwode_tailor`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `iwode_wechat_link`
--
ALTER TABLE `iwode_wechat_link`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `iwode_booking`
--
ALTER TABLE `iwode_booking`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `iwode_coupon`
--
ALTER TABLE `iwode_coupon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `iwode_member_user`
--
ALTER TABLE `iwode_member_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `iwode_tailor`
--
ALTER TABLE `iwode_tailor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `iwode_wechat_link`
--
ALTER TABLE `iwode_wechat_link`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;