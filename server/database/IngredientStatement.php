<?php
require_once('../database/Database.php');
require_once('../model/IngredientModel.php');
class IngredientStatement{

  private $stmt;
  function __construct(){
    $sql = "INSERT INTO Ingredient VALUES (:name , :description)";
    $this->stmt = Database::getConnection()->prepare($sql);
  }
  function insert($model){
    $name = $model->getName();
    $description = $model->getDescription();
    $this->stmt->bindParam(':name', $name);
    $this->stmt->bindParam(':description', $description);
    $this->stmt->execute();

    return $this->stmt->errorcode();
  }
}

 ?>
