<?php
  require_once('../database/Database.php');
  require_once('../model/TijdvakModel.php');
  class TijdvakStatement {

      private $stmt;
      function __construct(){
          $sql = "INSERT INTO Tijdvakken VALUES (:name)";
          $this->stmt = Database::getConnection()->prepare($sql);
      }
      function insert($model) {
          $name = $model->getName();
          $this->stmt->bindParam(':name', $name);
          $this->stmt->execute();
      }
  }
?>
