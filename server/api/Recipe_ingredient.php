<?php
namespace api;
require_once('../model/ReceptIngredientModel.php');
require_once('../database/ReceptStatement');
require_once('Api.php');

class ReceptIngredient extends Api{

  private $model;

  function __construct(){
      parent::__construct();
      //set_error_handler('error_handler');
  }

  function insert() : void{
      try{
          $this->model = new model\RecipeIngredient($_GET['recept_id']);
          $this->model = new model\RecipeIngredient($_GET['ingredient_name']);

          $recipe_ingredientStatement = new database\RecipeIngredient();
          $code = $recipe_ingredientStatement->insert($this->model);

          $code = substr($code, 0, 2);

          parent::setHttpCode($code);

      }catch(\PDOException $e){
          parent::setHttpCode($e->getCode());
      }catch(exception\NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
      }
  }

  function error_handler(){
      throw new exception\NullPointerException("Get value isn't passed");
  }
}

$receptIngredient = new ReceptIngredient();
$receptIngredient->insert();

 ?>
