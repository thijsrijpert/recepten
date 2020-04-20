<?php
echo 'TestS';
  require_once('../database/Database.php');
  echo'TestM';
  require_once('../model/TijdvakModel.php');
  class TijdvakStatement {

      private $stmt;
      function __construct(){
        echo 'Prepare';
          $sql = "INSERT INTO Tijdvakken VALUES (:name)";
          $this->stmt = Database::getConnection()->prepare($sql);
          echo $this->stmt->errorCode();
      }
      function insert($model) {
          $name = $model->getName();
          $this->stmt->bindParam(':name', $name);
          $this->stmt->execute();

          return $this->stmt->errorCode();
      }
  }
?>
