<?php
namespace database;
require_once('../database/Database.php');
require_once('../model/TijdvakModel.php');
class ReceptIngredient{

  function __construct(){
    $sql = "INSERT INTO Recept_Ingredient (recept_id, ingredient_name) VALUES (:recept_id , :ingredient_name)";
    $this->stmt = Database::getConnection()->prepare($sql);
    $this->stmt = \database\Database::getConnection()->prepare($sql);
  }
  function insert(\model\Model $model){
    $recept_id = $model->getReceptId();
    $ingredient_name = $model->getIngredientName();
    $this->stmt->bindParam(':recept_id', $recept_id);
    $this->stmt->bindParam(':ingredient_name', $ingredient_name);
    $this->stmt->execute();

    return $this->stmt->errorcode();
  }

  function select(\model\Model $model) : array{
    if(null != $model->getReceptId()){
      $this->select[0]->bindParam(':recept_id', $model->getReceptId());
    }
    if(null != $model->getIngredientName()){
      $this->select[0]->bindParam(':ingred', $model->getIngredientName());
    }

    $this->select[0]->execute();

    $results = $this->select[0]->fetchAll(\PDO::FETCH_CLASS|\PDO::FETCH_PROPS_LATE, 'model\ReceptIngredient');

    return array($this->select[0]->errorCode(), array($results));


  }

  function error_handler($errno, $errstr, $errfile, $errline){

  }

}

 ?>
