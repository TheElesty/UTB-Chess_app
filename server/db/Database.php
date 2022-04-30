<?php 
  class Database {
    private $host = 'md333.wedos.net';
    private $db_name = 'd298683_ak8po';
    private $username = 'w298683_ak8po';
    private $password = 'CnCpK4X3';
    private $conn;

    public function connect() {
      $this->conn = null;

      try { 
        $this->conn = new PDO('mysql:host=' . $this->host . ';dbname=' . $this->db_name, $this->username, $this->password);
        $this->conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
      } catch(PDOException $e) {
        echo 'Connection Error: ' . $e->getMessage();
      }

      return $this->conn;
    }
  }