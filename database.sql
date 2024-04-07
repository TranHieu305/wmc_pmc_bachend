-- Create a new database if it does not exist
CREATE DATABASE IF NOT EXISTS wms;

-- Switch to the database context
USE wms;

-- Create a table to store WAREHOUSE information
CREATE TABLE warehouse (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    address VARCHAR(255),
    longitude DECIMAL(10, 6),
    latitude DECIMAL(10, 6),
    supervisor TEXT,
    status VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);