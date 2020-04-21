<?php
namespace database;
  require_once('../database/Database.php');
  require_once('../model/TimeOfDay.php');
  class TimeOfDay {

      private $stmt;

      function __construct(){
          $sql = "INSERT INTO TimeOfDays VALUES (:name)";
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
