<?php
require_once('../database/Database.php');
require_once('../model/TijdvakModel.php');
class ReceptIngredientStatement{

  private $stmt;
  function __construct(){
    $sql = "INSERT INTO Recept_Ingredient VALUES (:recept_id , :recept_name)";
    $this->stmt = Database::getConnection()->prepare($sql);
  }
  function insert($model){
    $recept_id = $model->getReceptId();
    $recept_name = $model->getIngredientName();
    $this->stmt->bindParam(':recept_id', $recept_id);
    $this->stmt->bindParam(':recept_name', $recept_name);
    $this->stmt->execute();

    return $this->stmt->errorcode();
  }
}

 ?>
