<?php
namespace api;
require_once('../model/RecipeModel.php');
require_once('../database/RecipeStatement');
require_once('Api.php');

class Recept extends Api{

  private $model;

  function __construct(){
      parent::__construct();
      //set_error_handler('error_handler');
  }

  function insert() : void{
      try{
          $this->model = new model\Recipe($_GET['id']);
          $this->model = new model\Recipe($_GET['name']);
          $this->model = new model\Recipe($_GET['description']);
          $this->model = new model\Recipe($_GET['landcode']);
          $this->model = new model\Recipe($_GET['username']);
          $this->model = new model\Recipe($_GET['mealtype_name']);
          $this->model = new model\Recipe($_GET['religion_id']);
          $this->model = new model\Recipe($_GET['time_of_day']);
          $this->model = new model\Recipe($_GET['is_aproved']);

          $RecipeStatement = new database\Recipe();
          $code = $religieStatement->insert($this->model);

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

$recept = new Recept();
$recept->insert();

 ?>
