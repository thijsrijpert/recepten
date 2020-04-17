<?php
require_once('../model/ReceptIngredientModel.php');
require_once('../model/ReceptStatement');
require_once('Api.php');

class ReceptIngredient extends Api{

  private $model;

  function __construct(){

  }

  function insert() : void{
    $this->model = new ReceptIngredient();
    $this->model->setReceptId();
    $this->model->setIngredientName();

    echo $_GET['recept_id'];
    echo $_GET['ingredient_name'];
    $code = null;
    try{
      $receptIngredientStatement = new ReceptIngredientStatement();
      $receptIngredientStatement->insert($this->model);
    }catch(PDOException $e){
        $e->getCode();
        parent::setHttpCode($e->getCode());
        }
  }
}

$receptIngredient = new ReceptIngredient();
$receptIngredient->insert();

 ?>
