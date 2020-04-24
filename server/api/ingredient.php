<?php
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(-1);
require_once('../model/IngredientModel.php');
require_once('../database/IngredientStatement.php');
require_once('Api.php');

class Ingredient extends Api{

  private $model;

  function __construct(){

  }

  function insert() : void{
    $this->model = new IngredientModel();
    $this->model->setName($_GET['name']);
    $this->model->setDescription($_GET['description']);



    echo $_GET['name'];
    echo $_GET['description'];
    $code = null;
    try{
      $ingredientStatement = new IngredientStatement();
      $ingredientStatement->insert($this->model);
    }catch(PDOException $e){
      $e->getCode();
      parent::setHttpCode($e->getCode());
    }


    $code = substr($code, 0, 2);

    parent::setHttpCode($code);
  }
}

$ingredient = new Ingredient();
$ingredient->insert();

 ?>
