-- Table structure for data_xplorer_logger

CREATE SEQUENCE data_xplorer_logger_seq;
CREATE SEQUENCE data_xplorer_dict_common_seq;

DROP TABLE data_xplorer_logger;
CREATE TABLE data_xplorer_logger (
  id NUMBER(11) PRIMARY KEY,
  trace_id VARCHAR2(255 CHAR) NOT NULL,
  method VARCHAR2(255 CHAR),
  url VARCHAR2(255 CHAR),
  query VARCHAR2(255 CHAR),
  body CLOB,
  action_time NUMBER(11),
  ip VARCHAR2(255 CHAR),
  app_name VARCHAR2(255 CHAR),
  request_type NUMBER(11),
  create_time TIMESTAMP,
  create_by VARCHAR2(255 CHAR),
  error_msg CLOB,
  source_type NUMBER(255) NOT NULL,
  insert_time TIMESTAMP
);

-- Table structure for data_xplorer_dict_common
DROP TABLE data_xplorer_dict_common;
CREATE TABLE data_xplorer_dict_common (
  id NUMBER(20) PRIMARY KEY,
  dict_code VARCHAR2(64 CHAR) NOT NULL,
  dict_desc VARCHAR2(64 CHAR) NOT NULL,
  category_code VARCHAR2(64 CHAR) NOT NULL,
  category_desc VARCHAR2(64 CHAR),
  sort_no NUMBER(8) NOT NULL DEFAULT 999,
  data_type VARCHAR2(64 CHAR) DEFAULT 'STRING' NOT NULL,
  remark VARCHAR2(128 CHAR),
  locate_code VARCHAR2(64 CHAR),
  create_by VARCHAR2(64 CHAR) DEFAULT '0',
  update_by VARCHAR2(64 CHAR) DEFAULT '0',
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
  version NUMBER(8) NOT NULL DEFAULT 0
);

-- Records of data_xplorer_dict_common
INSERT INTO data_xplorer_dict_common (id, dict_code, dict_desc, category_code, category_desc, sort_no, data_type, remark, locate_code, create_by, update_by, create_time, update_time, version) VALUES
(1, '0', 'feign', 'request_type', 'data_xplorer_logger', 999, 'STRING', NULL, 'request_type', '0', '0', TO_TIMESTAMP('2023-09-25 05:14:10', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2023-09-25 05:19:33', 'YYYY-MM-DD HH24:MI:SS'), 0),
(2, '1', 'interceptor', 'request_type', 'data_xplorer_logger', 999, 'STRING', NULL, 'request_type', '0', '0', TO_TIMESTAMP('2023-09-25 05:16:13', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2023-09-25 05:19:36', 'YYYY-MM-DD HH24:MI:SS'), 0),
(4, '0', 'automatic', 'source_type', 'data_xplorer_logger', 999, 'STRING', NULL, 'source_type', '0', '0', TO_TIMESTAMP('2023-09-25 05:16:13', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2023-09-25 05:16:22', 'YYYY-MM-DD HH24:MI:SS'), 0),
(5, '1', 'manual', 'source_type', 'data_xplorer_logger', 999, 'STRING', NULL, 'source_type', '0', '0', TO_TIMESTAMP('2023-09-25 05:16:13', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2023-09-25 05:16:22', 'YYYY-MM-DD HH24:MI:SS'), 0);


-- Trigger to automatically populate id fields using sequences
CREATE OR REPLACE TRIGGER data_xplorer_logger_id_trigger
BEFORE INSERT ON data_xplorer_logger
FOR EACH ROW
BEGIN
  SELECT data_xplorer_logger_seq.NEXTVAL
  INTO :NEW.id
  FROM dual;
END;

CREATE OR REPLACE TRIGGER data_xplorer_dict_common_id_trigger
BEFORE INSERT ON data_xplorer_dict_common
FOR EACH ROW
BEGIN
  SELECT data_xplorer_dict_common_seq.NEXTVAL
  INTO :NEW.id
  FROM dual;
END;


CREATE SEQUENCE data_xplorer_event_logger_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;


CREATE TABLE data_xplorer_frontend_event_logger (
  id NUMBER PRIMARY KEY,
  trace_id VARCHAR2(255 CHAR) NOT NULL,
  event_name VARCHAR2(255 CHAR) NOT NULL,
  element_id VARCHAR2(255 CHAR),
  element_type VARCHAR2(255 CHAR),
  page_url CLOB,
  user_id VARCHAR2(255 CHAR),
  event_time TIMESTAMP,
  source_type VARCHAR2(255 CHAR),
  others_body CLOB,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE TRIGGER data_xplorer_event_logger_trigger
BEFORE INSERT ON data_xplorer_frontend_event_logger
FOR EACH ROW
BEGIN
  SELECT data_xplorer_event_logger_seq.NEXTVAL INTO :new.id FROM dual;
END;
/
