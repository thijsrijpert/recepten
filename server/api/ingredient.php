<?php
namespace api;
require_once('../model/Ingredient.php');
require_once('../database/Ingredient.php');
require_once('Api.php');

class Ingredient extends Api{

  private $model;

  function __construct(){
      parent::__construct();
  }

  function insert() : void{
    try{
      $this->model = new new model\Ingredient($_GET['name']);
      $this->model = new new model\Ingredient($_GET['description']);
      $this->model = new new model\Ingredient($_GET['is_aproved']);
      $this->model = new new model\Ingredient($_GET['username']);

      $ingredientStatement = new database\Ingredient();
      $code = $ingredientStatement->insert($this->model);

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

$ingredient = new Ingredient();
$ingredient->insert();

 ?>
