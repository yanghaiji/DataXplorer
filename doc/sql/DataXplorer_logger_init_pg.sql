-- Table structure for data_xplorer_logger
DROP TABLE IF EXISTS data_xplorer_logger;
CREATE TABLE data_xplorer_logger (
  id serial PRIMARY KEY,
  trace_id varchar(255) NOT NULL,
  method varchar(255),
  url varchar(255),
  query varchar(255),
  body text,
  action_time integer,
  ip varchar(255),
  app_name varchar(255),
  request_type integer,
  create_time timestamp,
  create_by varchar(255),
  error_msg text,
  source_type integer NOT NULL,
  insert_time timestamp
);

-- Table structure for data_xplorer_dict_common
DROP TABLE IF EXISTS data_xplorer_dict_common;
CREATE TABLE data_xplorer_dict_common (
  id serial PRIMARY KEY,
  dict_code varchar(64) NOT NULL,
  dict_desc varchar(64) NOT NULL,
  category_code varchar(64) NOT NULL,
  category_desc varchar(64),
  sort_no smallint NOT NULL DEFAULT 999,
  data_type varchar(64) NOT NULL DEFAULT 'STRING',
  remark varchar(128),
  locate_code varchar(64),
  create_by varchar(64) DEFAULT '0',
  update_by varchar(64) DEFAULT '0',
  create_time timestamptz DEFAULT NOW(),
  update_time timestamptz DEFAULT NOW(),
  version smallint NOT NULL DEFAULT 0
);

-- Records of data_xplorer_dict_common
INSERT INTO data_xplorer_dict_common (dict_code, dict_desc, category_code, category_desc, sort_no, data_type, remark, locate_code, create_by, update_by, create_time, update_time, version) VALUES
('0', 'feign', 'request_type', 'data_xplorer_logger', 999, 'STRING', NULL, 'request_type', '0', '0', '2023-09-25 05:14:10', '2023-09-25 05:19:33', 0),
('1', 'interceptor', 'request_type', 'data_xplorer_logger', 999, 'STRING', NULL, 'request_type', '0', '0', '2023-09-25 05:16:13', '2023-09-25 05:19:36', 0),
('0', 'automatic', 'source_type', 'data_xplorer_logger', 999, 'STRING', NULL, 'source_type', '0', '0', '2023-09-25 05:16:13', '2023-09-25 05:16:22', 0),
('1', 'manual', 'source_type', 'data_xplorer_logger', 999, 'STRING', NULL, 'source_type', '0', '0', '2023-09-25 05:16:13', '2023-09-25 05:16:22', 0);


CREATE TABLE data_xplorer_frontend_event_logger (
  id serial PRIMARY KEY,
  trace_id character varying(255) NOT NULL,
  event_name character varying(255) NOT NULL,
  element_id character varying(255),
  element_type character varying(255),
  page_url text,
  user_id character varying(255),
  event_time timestamp,
  source_type character varying(255),
  others_body text,
  create_time timestamp DEFAULT current_timestamp
);

CREATE TABLE data_xplorer_custom_track (
    id SERIAL PRIMARY KEY,
    trace_id VARCHAR(255),
    operation_type INTEGER,
    request_parameter TEXT,
    app_name VARCHAR(255),
    create_time TIMESTAMP,
    create_by VARCHAR(255),
    error_msg text,
    insert_time TIMESTAMP
);
