-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 06, 2019 at 02:53 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `androidwebservice`
--

-- --------------------------------------------------------

--
-- Table structure for table `chatgroup`
--

CREATE TABLE `chatgroup` (
  `id` int(11) NOT NULL,
  `membersend` varchar(150) NOT NULL,
  `textsend` text NOT NULL,
  `datemess` varchar(10) NOT NULL,
  `timemess` varchar(8) NOT NULL,
  `namegroup` varchar(250) NOT NULL,
  `useradd` varchar(150) NOT NULL,
  `datetimeadd` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `chatgroup`
--

INSERT INTO `chatgroup` (`id`, `membersend`, `textsend`, `datemess`, `timemess`, `namegroup`, `useradd`, `datetimeadd`) VALUES
(25, 'nvnghia2212@gmail.com', 'Đã Thêm ngoctien@gmail.com Vào Nhóm !', '', '', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(26, 'nvnghia2212@gmail.com', 'Đã Thêm thanhthien@gmail.com Vào Nhóm !', '', '', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(27, 'nvnghia2212@gmail.com', 'Đã Thêm ngocyen@gmail.com Vào Nhóm !', '', '', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(28, 'ngocyen@gmail.com', 'hello ', '25/05/2019', '08:13', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(29, 'thanhthien@gmail.com', 'xin chào', '25/05/2019', '08:15', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(30, 'ngoctien@gmail.com', 'hello friends', '25/05/2019', '08:18', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(31, 'nvnghia2212@gmail.com', 'xin chào mấy bạn :))', '25/05/2019', '08:35', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(32, 'nvnghia2212@gmail.com', 'Đã Thêm vananh@gmail.com Vào Nhóm !', '', '', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(33, 'nvnghia2212@gmail.com', 'Đã Xóa vananh@gmail.com Khỏi Nhóm !', '', '', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(34, 'nvnghia2212@gmail.com', 'Đã Thêm vananh@gmail.com Vào Nhóm !', '', '', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(35, 'nvnghia2212@gmail.com', 'Đã Xóa vananh@gmail.com Khỏi Nhóm !', '', '', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531');

-- --------------------------------------------------------

--
-- Table structure for table `frienduser`
--

CREATE TABLE `frienduser` (
  `id` int(11) NOT NULL,
  `email` varchar(150) NOT NULL,
  `emailfrienduser` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `frienduser`
--

INSERT INTO `frienduser` (`id`, `email`, `emailfrienduser`) VALUES
(39, 'nvnghia2212@gmail.com', 'vananh@gmail.com'),
(40, 'vananh@gmail.com', 'nvnghia2212@gmail.com'),
(41, 'nvnghia2212@gmail.com', 'ngocyen@gmail.com'),
(42, 'ngocyen@gmail.com', 'nvnghia2212@gmail.com'),
(43, 'nvnghia2212@gmail.com', 'phong@gmail.com'),
(44, 'phong@gmail.com', 'nvnghia2212@gmail.com'),
(45, 'nvnghia2212@gmail.com', 'thanhthien@gmail.com'),
(46, 'thanhthien@gmail.com', 'nvnghia2212@gmail.com'),
(47, 'nvnghia2212@gmail.com', 'ngoctien@gmail.com'),
(48, 'ngoctien@gmail.com', 'nvnghia2212@gmail.com'),
(49, 'nvnghia2212@gmail.com', 'yennhi@gmail.com'),
(50, 'yennhi@gmail.com', 'nvnghia2212@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `groupaddnew`
--

CREATE TABLE `groupaddnew` (
  `id` int(11) NOT NULL,
  `namegroup` varchar(250) NOT NULL,
  `useradd` varchar(150) NOT NULL,
  `datetimeadd` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groupaddnew`
--

INSERT INTO `groupaddnew` (`id`, `namegroup`, `useradd`, `datetimeadd`) VALUES
(17, 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(18, 'test 2', 'nvnghia2212@gmail.com', '22/05/2019 13:26:52:472'),
(19, 'test 3', 'nvnghia2212@gmail.com', '26/05/2019 19:13:21:646'),
(20, 'test 4', 'nvnghia2212@gmail.com', '26/05/2019 19:13:31:664'),
(21, 'test 5', 'nvnghia2212@gmail.com', '26/05/2019 19:13:40:946'),
(22, 'test 6', 'nvnghia2212@gmail.com', '26/05/2019 19:13:55:603');

-- --------------------------------------------------------

--
-- Table structure for table `grouppeople`
--

CREATE TABLE `grouppeople` (
  `id` int(11) NOT NULL,
  `member` varchar(150) NOT NULL,
  `namegroup` varchar(250) NOT NULL,
  `useradd` varchar(150) NOT NULL,
  `datetimeadd` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `grouppeople`
--

INSERT INTO `grouppeople` (`id`, `member`, `namegroup`, `useradd`, `datetimeadd`) VALUES
(24, 'xyz@gmail.com', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(28, 'abc@gmail.com', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(29, 'nvnghia2212@gmail.com', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(30, 'nvnghia2212@gmail.com', 'test 2', 'nvnghia2212@gmail.com', '22/05/2019 13:26:52:472'),
(31, 'ngoctien@gmail.com', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(32, 'thanhthien@gmail.com', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(33, 'ngocyen@gmail.com', 'test 1', 'nvnghia2212@gmail.com', '11/05/2019 07:13:46:531'),
(34, 'nvnghia2212@gmail.com', 'test 3', 'nvnghia2212@gmail.com', '26/05/2019 19:13:21:646'),
(35, 'nvnghia2212@gmail.com', 'test 4', 'nvnghia2212@gmail.com', '26/05/2019 19:13:31:664'),
(36, 'nvnghia2212@gmail.com', 'test 5', 'nvnghia2212@gmail.com', '26/05/2019 19:13:40:946'),
(37, 'nvnghia2212@gmail.com', 'test 6', 'nvnghia2212@gmail.com', '26/05/2019 19:13:55:603');

-- --------------------------------------------------------

--
-- Table structure for table `memberdating`
--

CREATE TABLE `memberdating` (
  `id` int(11) NOT NULL,
  `nameaddress` varchar(255) NOT NULL,
  `dateaddress` varchar(150) NOT NULL,
  `datetimeadd` varchar(150) NOT NULL,
  `useradd` varchar(150) NOT NULL,
  `member` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `memberdating`
--

INSERT INTO `memberdating` (`id`, `nameaddress`, `dateaddress`, `datetimeadd`, `useradd`, `member`) VALUES
(1, 'abc', 'xxxxx', 'xxxxxxxxxxxxx', 'zzz', 'zzz'),
(2, 'Của Nghĩa', 'xxxxx', 'xxxxxxxxxxxxx', 'nvnghia2212@gmail.com', 'nvnghia2212@gmail.com'),
(3, 'Của Nhi', 'xxxxx', 'xxxxxxxxxxxxx', 'yennhi@gmail.com', 'yennhi@gmail.com'),
(4, 'Của Tiền', 'xxxxx', 'xxxxxxxxxxxxx', 'ngoctien@gmail.com', 'ngoctien@gmail.com'),
(5, 'Của Vân Anh', 'xxxxx', 'xxxxxxxxxxxxx', 'vananh@gmail.com', 'vananh@gmail.com'),
(6, 'Của Vân Anh', 'xxxxx', 'xxxxxxxxxxxxx', 'vananh@gmail.com', 'nvnghia2212@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `messenger`
--

CREATE TABLE `messenger` (
  `id` int(11) NOT NULL,
  `emailsend` varchar(150) NOT NULL,
  `textmess` text NOT NULL,
  `emailreceived` varchar(150) NOT NULL,
  `datemess` varchar(10) NOT NULL,
  `timemess` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `messenger`
--

INSERT INTO `messenger` (`id`, `emailsend`, `textmess`, `emailreceived`, `datemess`, `timemess`) VALUES
(24, 'ngoctien@gmail.com', 'hello !!', 'nvnghia2212@gmail.com', '25/05/2019', '08:23'),
(26, 'nvnghia2212@gmail.com', 'chào bạn, có gì không ?', 'ngoctien@gmail.com', '25/05/2019', '08:31');

-- --------------------------------------------------------

--
-- Table structure for table `newdating`
--

CREATE TABLE `newdating` (
  `id` int(11) NOT NULL,
  `nameaddress` varchar(255) NOT NULL,
  `dateaddress` varchar(150) NOT NULL,
  `datetimeadd` varchar(150) NOT NULL,
  `useradd` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `newdating`
--

INSERT INTO `newdating` (`id`, `nameaddress`, `dateaddress`, `datetimeadd`, `useradd`) VALUES
(1, 'abc', 'xxxxx', 'xxxxxxxxxxxxx', 'zzz'),
(2, 'Của Nghĩa', 'xxxxx', 'xxxxxxxxxxxxx', 'nvnghia2212@gmail.com'),
(3, 'Của Nhi', 'xxxxx', 'xxxxxxxxxxxxx', 'yennhi@gmail.com'),
(4, 'Của Tiền', 'xxxxx', 'xxxxxxxxxxxxx', 'ngoctien@gmail.com'),
(5, 'Của Vân Anh', 'xxxxx', 'xxxxxxxxxxxxx', 'vananh@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `requestfrienduser`
--

CREATE TABLE `requestfrienduser` (
  `id` int(11) NOT NULL,
  `email` varchar(150) NOT NULL,
  `emailfrienduser` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `requestfrienduser`
--

INSERT INTO `requestfrienduser` (`id`, `email`, `emailfrienduser`) VALUES
(1, 'phuoc@gmail.com', 'nvnghia2212@gmail.com'),
(6, 'thanhdanh@gmail.com', 'nvnghia2212@gmail.com'),
(9, 'thiensinh@gmail.com', 'nvnghia2212@gmail.com'),
(10, 'huuthe@gmail.com', 'nvnghia2212@gmail.com'),
(11, 'tuanphat@gmail.com', 'nvnghia2212@gmail.com'),
(12, 'haiphong@gmail.com', 'nvnghia2212@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(150) NOT NULL,
  `givenname` varchar(150) NOT NULL,
  `name` varchar(150) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `onloff` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `givenname`, `name`, `latitude`, `longitude`, `onloff`) VALUES
(2, 'phuoc@gmail.com', 'Lê', 'Châu Phước', 10.031064, 105.768947, 1),
(3, 'phong@gmail.com', 'Võ', 'Thanh Phong', 10.02889, 105.771411, 1),
(11, 'hung@gmail.com', 'Thế', 'Tất Hùng', 10.046896, 105.767873, 1),
(13, 'nvnghia2212@gmail.com', 'Nguyễn', 'Nghĩa', 10.046827, 105.769995, 1),
(14, 'vananh@gmail.com', 'Phạm Thị', ' Vân Anh', 10.048112, 105.759845, 0),
(15, 'ngocyen@gmail.com', 'Lê', 'Ngọc Yến', 10.04348, 105.764391, 0),
(16, 'huuchau@gmail.com', 'Lê', 'Hữu Châu', 10.045685, 105.767978, 0),
(17, 'thebao@gmail.com', 'Nguyễn', 'Thế Bảo', 10.04503, 105.766402, 1),
(18, 'thanhthien@gmail.com', 'Mã', 'Lâm Thanh Thiên', 10.04856, 105.772433, 1),
(19, 'thanhdanh@gmail.com', 'Nguyễn', 'Thanh Danh', 10.050071, 105.771725, 1),
(20, 'ngoctien@gmail.com', 'Bùi', 'Ngọc Tiền', 10.050071, 105.765416, 1),
(21, 'nguyenthithao@gmail.com', 'Nguyễn', 'Thị Thảo', 10.045098, 105.76819, 1),
(22, 'yennhi@gmail.com', 'Đinh', 'Yến Nhi', 10.045038, 105.766726, 1),
(23, 'quyen@gmail.com', 'Lê', 'Tố Quyên', 10.044227, 105.76537, 1),
(24, 'camtu@gmail.com', 'Lê', 'Cẩm Tú', 10.049546, 105.767505, 1),
(25, 'kieuphuong@gmail.com', 'Nguyễn', 'Kiều Phương', 10.049192, 105.767913, 1),
(26, 'tuanphat@gmail.com', 'Nguyễn', 'Tuấn Phát', 10.046088, 105.77046, 1),
(27, 'haiphong@gmail.com', 'Nguyễn', 'Hải Phong', 10.043601, 105.76836, 1),
(28, 'huuthe@gmail.com', 'Phạm', 'Hữu Thế', 10.041803, 105.766654, 1),
(29, 'thiensinh@gmail.com', 'Phạm', 'Thiên Sinh', 10.041021, 105.767704, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chatgroup`
--
ALTER TABLE `chatgroup`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `frienduser`
--
ALTER TABLE `frienduser`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `groupaddnew`
--
ALTER TABLE `groupaddnew`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `grouppeople`
--
ALTER TABLE `grouppeople`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `memberdating`
--
ALTER TABLE `memberdating`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `messenger`
--
ALTER TABLE `messenger`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `newdating`
--
ALTER TABLE `newdating`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `requestfrienduser`
--
ALTER TABLE `requestfrienduser`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chatgroup`
--
ALTER TABLE `chatgroup`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `frienduser`
--
ALTER TABLE `frienduser`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `groupaddnew`
--
ALTER TABLE `groupaddnew`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `grouppeople`
--
ALTER TABLE `grouppeople`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `memberdating`
--
ALTER TABLE `memberdating`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `messenger`
--
ALTER TABLE `messenger`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `newdating`
--
ALTER TABLE `newdating`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `requestfrienduser`
--
ALTER TABLE `requestfrienduser`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
