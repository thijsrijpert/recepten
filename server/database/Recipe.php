<?php
namespace database;
require_once('../database/Database.php');
require_once('../model/RecipeModel.php');

class RecipeStatement{

  private $stmt;
  function __construct(){
    $sql = "INSERT INTO Recipe VALUES (:name , :description, :landcode , :username , :mealtype_name , :religion_id , :time_of_day , :is_approved)";
    $this->stmt = Database::getConnection()->prepare($sql);
  }
  function insert($model){
    $name = $model->getName();
    $description = $model->getDescription();
    $landcode = $model->getLandcode();
    $username = $model->getUsername();
    $mealtype_name = $model->getMealtype_name();
    $religion_id = $model->getReligion_id();
    $time_of_day = $model->gettimeofday();
    $is_approved = $model->getIs_approved();
    $this->stmt->bindParam(':id', $name);
    $this->stmt->bindParam(':name', $name);
    $this->stmt->bindParam(':description', $description);
    $this->stmt->bindParam(':landcode', $landcode);
    $this->stmt->bindParam(':username', $username);
    $this->stmt->bindParam(':mealtype_name', $mealtype_name);
    $this->stmt->bindParam(':religion_id', $religion_id);
    $this->stmt->bindParam(':time_of_day', $time_of_day);
    $this->stmt->bindParam(':is_approved', $is_approved);

    $this->stmt->execute();

    return $this->stmt->errorcode();
  }

}

 ?>
