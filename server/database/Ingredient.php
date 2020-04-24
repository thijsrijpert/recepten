<?php
namespace database;
require_once('../database/Database.php');
require_once('../model/IngredientModel.php');
class IngredientStatement{

  private $stmt;
  
  function __construct(){
    $sql = "INSERT INTO Ingredient VALUES (:name , :description , :is_aproved , :username)";
    $this->stmt = Database::getConnection()->prepare($sql);
  }
  function insert($model){
    $name = $model->getName();
    $description = $model->getDescription();
    $is_approved = $model->getIs_approved();
    $username = $model->getUsername();
    $this->stmt->bindParam(':name', $name);
    $this->stmt->bindParam(':description', $description);
    $this->stmt->bindParam(':is_approved', $is_approved);
    $this->stmt->bindParam(':username', $username);
    $this->stmt->execute();

    return $this->stmt->errorcode();
  }
}

 ?>
