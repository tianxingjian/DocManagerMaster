DROP TABLE IF EXISTS `doc_patch`;
DROP TABLE IF EXISTS `doc_bugmessage`;

CREATE TABLE
    `doc_bugmessage`
    (
        `bugId` char(13) NOT NULL COMMENT '������ϢId',
        `productName` VARCHAR(20) DEFAULT '~' COMMENT '��Ʒ��Ϣ',
        `projectName` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '��Ŀ��Ϣ',
        `versionNo` VARCHAR(30) DEFAULT '~' COMMENT '�汾��',
        `relpath` VARCHAR(20) NULL COMMENT '�����洢���·��',
        `ts` DATETIME COMMENT '��¼����ʱ��',
        PRIMARY KEY (`bugId`)
    )
    ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
    

CREATE TABLE
    `doc_patch`
    (   
        `id` bigint(5) NOT NULL AUTO_INCREMENT COMMENT 'id',
        `bugId` char(13) NOT NULL COMMENT '������ϢId',
        `filename` VARCHAR(20) DEFAULT '~' COMMENT '�ļ�����',
        `ts` DATETIME COMMENT '��¼����ʱ��',
        PRIMARY KEY (`id`),
        FOREIGN KEY (bugId) REFERENCES doc_bugmessage(bugId)
                     ON DELETE CASCADE
    )
    ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;    