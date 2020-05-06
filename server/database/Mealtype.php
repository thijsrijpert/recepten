<?php
namespace database;
  require_once('../database/Database.php');
  require_once('../model/Mealtype.php');
  class Mealtype{

      private $stmt;

      function __construct(){
          $sql = "INSERT INTO Mealtype VALUES (:name)";
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