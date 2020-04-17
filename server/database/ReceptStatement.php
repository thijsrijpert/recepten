<?php

require_once('../database/Database.php');
require_once('../model/ReceptModel.php');

class ReceptStatement{

  private $stmt;
  function __construct(){
    $sql = "INSERT INTO Recept VALUES (:name , :description)";
    $this->stmt = Database::getConnection()->prepare($sql);
  }
  function insert($model){
    $id = $model->getId();
    $name = $model->getName();
    $description = $model->getDescription();
    $landcode = $model->getLandcode();
    $username = $model->getUsername();
    $description = $model->getDescription();
    $this->stmt->bindParam(':id', $name);
    $this->stmt->bindParam(':name', $name);
    $this->stmt->bindParam(':description', $description);
    $this->stmt->bindParam(':landcode', $landcode);
    $this->stmt->bindParam(':username', $username);
    $this->stmt->execute();

    return $this->stmt->errorcode();
  }

}

 ?>
