DROP TABLE IF EXISTS `doc_patch`;
DROP TABLE IF EXISTS `doc_bugmessage`;

CREATE TABLE
    `doc_bugmessage`
    (
        `bugId` char(13) NOT NULL COMMENT '问题信息Id',
        `productName` VARCHAR(20) DEFAULT '~' COMMENT '产品信息',
        `projectName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '项目信息',
        `versionNo` VARCHAR(30) DEFAULT '~' COMMENT '版本号',
        `relpath` VARCHAR(20) NULL COMMENT '附件存储相对路径',
        `ts` DATETIME COMMENT '记录增加时间',
        PRIMARY KEY (`bugId`)
    )
    ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
    

CREATE TABLE
    `doc_patch`
    (   
        `id` bigint(5) NOT NULL AUTO_INCREMENT COMMENT 'id',
        `bugId` char(13) NOT NULL COMMENT '问题信息Id',
        `filename` VARCHAR(20) DEFAULT '~' COMMENT '文件名称',
        `ts` DATETIME COMMENT '记录增加时间',
        PRIMARY KEY (`id`),
        FOREIGN KEY (bugId) REFERENCES doc_bugmessage(bugId)
                     ON DELETE CASCADE
    )
    ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;    