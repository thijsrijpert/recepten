<?php
  require_once('../database/Database.php');
  require_once('../model/ReligieModel.php');
  class ReligieStatement {

      private $stmt;
      function __construct(){
          $sql = "INSERT INTO Tijdvakken (name) VALUES (:name)";
          $this->stmt = Database::getConnection()->prepare($sql);
      }
      function insert($model) {
          $name = $model->getName();
          $this->stmt->bindParam(':name', $name);
          $this->stmt->execute();

          return $this->stmt->errorCode();
      }
  }
?>
