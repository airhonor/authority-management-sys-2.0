/*
 Navicat Premium Data Transfer

 Source Server         : ubuntu20.04
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 192.167.112.33:3306
 Source Schema         : oauth-2.0

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 10/08/2021 17:09:12
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals`
(
    `userId`         varchar(256) DEFAULT NULL,
    `clientId`       varchar(256) DEFAULT NULL,
    `partnerKey`     varchar(32)  DEFAULT NULL,
    `scope`          varchar(256) DEFAULT NULL,
    `status`         varchar(10)  DEFAULT NULL,
    `expiresAt`      datetime     DEFAULT NULL,
    `lastModifiedAt` datetime     DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

SET
FOREIGN_KEY_CHECKS = 1;
