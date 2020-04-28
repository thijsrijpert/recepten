<?php
namespace database;
require_once(dirname(__FILE__,1) . '/Database.php');
require_once(dirname(__FILE__,2) . '/model/Religion.php');

  class Religion {

      private $stmt;

      function __construct(){
          $sql = "INSERT INTO Religion (name) VALUES (:name)";
          $this->stmt = \database\Database::getConnection()->prepare($sql);
      }

      function insert($model) {
          $name = $model->getName();
          $this->stmt->bindParam(':name', $name);
          $this->stmt->execute();

          return $this->stmt->errorCode();
      }
  }
?>
