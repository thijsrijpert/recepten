<?php
namespace database;
  require_once('../database/Database.php');
  require_once('../model/Country.php');
  class Country{

      private $stmt;

      function __construct(){
          $sql = "INSERT INTO Country VALUES (:country_code, :name, :description)";
          $this->stmt = \database\Database::getConnection()->prepare($sql);
      }

      function insert($model) {
          $country_code = $model->getCountry_code();
          $name = $model->getName();
          $description = $model->getDescription();
          $this->stmt->bindParam(':country_code', $country_code);
          $this->stmt->bindParam(':name', $name);
          $this->stmt->bindParam(':description', $description);
          $this->stmt->execute();

          return $this->stmt->errorCode();
      }
  }
?>
