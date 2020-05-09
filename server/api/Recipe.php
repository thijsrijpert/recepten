<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__,2) . '/model/Recipe.php');
require_once(dirname(__FILE__,2) . '/database/Recipe.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/Api.php');

class Recipe extends Api{

  private $model;

  function __construct(){
      parent::__construct();
      set_error_handler(array($this, 'error_handler'));
  }

  function insert() : void{
      try{
          $this->model = new model\Recipe($_GET['id']);
          $this->model = new model\Recipe($_GET['name']);
          $this->model = new model\Recipe($_GET['description']);
          $this->model = new model\Recipe($_GET['is_approved']);
          $this->model = new model\Recipe($_GET['countrycode']);
          $this->model = new model\Recipe($_GET['username']);
          $this->model = new model\Recipe($_GET['mealtype_name']);
          $this->model = new model\Recipe($_GET['religion_id']);
          $this->model = new model\Recipe($_GET['time_of_day']);


          $recipeStatement = new database\Recipe();
          $code = $recipeStatement->insert($this->model);

          $code = substr($code, 0, 2);

          parent::setHttpCode($code);

      }catch(\PDOException $e){
          parent::setHttpCode($e->getCode());
      }catch(exception\NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
          restore_error_handler();
      }
  }

  public function select(){
    try{
      $this->model = new \model\Recipe();
      $queryBuilder = parent::buildQuery($this->model);

      if(null != $_GET['where']){
          $arguments = parent::rebuildArguments($_GET['where']);
          $approvedArguments = $this->model->getVariables();
          foreach($arguments as $value){
              if($value[0] == 'id'){
                  $this->model->setId($value[2]);
              }else if($value[0] == 'name'){
                  $this->model->setName($value[2]);
              }else if($value[0] == 'description'){
                  $this->model->setDescription($value[2]);
              }else if($value[0] == 'is_approved'){
                  $this->model->setIs_approved($value[2]);
              }else if($value[0] == 'countrycode'){
                  $this->model->setCountrycode($value[2]);
              }else if($value[0] == 'username'){
                  $this->model->setUsername($value[2]);
              }else if($value[0] == 'mealtype_name'){
                  $this->model->setMealtype_name($value[2]);
              }else if($value[0] == 'religion_id'){
                  $this->model->setReligion_id($value[2]);
              }else if($value[0] == 'time_of_day'){
                  $this->model->setTime_of_day($value[2]);
              }
          }
      }

      $recipeStatement = new \database\Recipe($queryBuilder);
      $codeAndResult = $recipeStatement->select($this->model);

      if($codeAndResult[0][1] == '00'){
          header('Content-Type: application/json');
          echo json_encode($codeAndResult[1][0]);
      }

      $code = substr($code, 0, 2);

      parent::setHttpCode($code);
    }catch(\PDOException $e){
        parent::setHttpCode($e->getCode());
    }catch(\exception\NullPointerException $e){
        header('HTTP/1.0 400 Bad Request');
        restore_error_handler();
    }
  }

  function error_handler($errno, $errstr, $errfile, $errline){
      if($errstr == 'Undefined index: name'){
          throw new \exception\NullPointerException("Get value isn't passed");
      }else{
          restore_error_handler();
      }
  }
}

$recipe = new Recipe();
if(isset($_GET['name'])){
    $recipe->insert();
}else{

}

 ?>
