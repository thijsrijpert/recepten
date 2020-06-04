<?php
namespace api;
ini_set('display_startup_errors', 1);
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);
require_once(dirname(__FILE__,2) . '/model/Recipe.php');
require_once(dirname(__FILE__,2) . '/database/Query.php');
require_once(dirname(__FILE__,2) . '/database/QueryBuilder.php');
require_once(dirname(__FILE__, 1) . '/CRUDInterface.php');
require_once(dirname(__FILE__,2) . '/database/Recipe.php');
require_once(dirname(__FILE__,2) . '/database/RecipeIngredient.php');
require_once(dirname(__FILE__,2) . '/exception/NullPointerException.php');
require_once(dirname(__FILE__,1) . '/Api.php');

class Recipe extends Api{

  private $model;

  function __construct(){
      parent::__construct();
      set_error_handler(array($this, 'error_handler'));
  }

  function insert(){
      try{

          $entityBody = json_decode(file_get_contents('php://input'));
          $modelSelect = new \model\Recipe(null, $_GET['name']);
          $this->model = new \model\Recipe(null, $_GET['name'], $_GET['description'], $_GET['isApproved'], $_GET['countrycode'], $_GET['username'], $_GET['mealtype_name'], $_GET['religion_id'], $_GET['time_of_day'] );
          $wherestatement = "name-eq-12";
          $selectcombo = "id";
          $query = new \database\Query($modelSelect);
          $query->setSelectArguments(parent::rebuildArguments($selectcombo));
          $query->setWhereArguments(parent::rebuildArguments($wherestatement));
          $dbbuilder = new \database\QueryBuilder($query);
          $dbbuilder->generateSql();
          \var_dump($dbbuilder);
          $recipeStatement = new \database\Recipe($dbbuilder);

          $code = $recipeStatement->insert($this->model);

          $code = substr($code, 0, 2);
          if($code === "00"){
            $result = $recipeStatement->select($modelSelect);
            \var_dump($result);
            if($result[0] === "00000"){
              echo "test";
              $recipeingredient =  new \database\RecipeIngredient();
              $recipeId = ($result[1][0][0])->getId();
              $recipeingredient->insert($entityBody, $recipeId);
            }else{
              parent::setHttpCode($result[0]);
            }
          }else{
            parent::setHttpCode($code);
          }

          parent::setHttpCode($code);

      }catch(\PDOException $e){
          parent::setHttpCode($e->getCode());
      }catch(\exception\NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
          restore_error_handler();
      }
  }

  public function select(){
    try{
      $this->model = $this->getWhereModel();
      $queryBuilder = parent::buildQuery($this->model);

      $codeAndResult = (new \database\Recipe($queryBuilder))->select($this->model);
      $code = substr($codeAndResult[0],0,2);

      if($codeAndResult[0] == '00'){
          header('Content-Type: application/json');
          echo json_encode($codeAndResult[1][0]);
      }

      parent::setHttpCode($code);
    }catch(\PDOException $e){
        parent::setHttpCode($e->getCode());
    }catch(\exception\NullPointerException $e){
      header('HTTP/1.0 400 Bad Request');
      //set the datatype to json for consistancy with all select query's
      header('Content-Type: application/json');
      //return the error code for easy debug
      echo json_encode($e->getMessage());
      restore_error_handler();
    }
  }

  function update(){
    try{
      $modelNew = new \model\Recipe();
      $modelOld = $this->getWhereModel();

      $queryBuilderSelect = parent::buildQuery($modelOld);
      $queryBuilderUpdate = parent::buildUpdate($modelNew);

      if(null != $_GET['set']){
          $arguments = parent::rebuildArguments($_GET['set']);
          $approvedArguments = $modelNew->getVariables();
          foreach($arguments as $value){

              if($value[0] == 'id'){
                  $modelNew->setId($value[1]);
              }else if($value[0] == 'name'){
                  $modelNew->setName($value[1]);
              }else if($value[0] == 'description'){
                  $modelNew->setDescription($value[1]);
              }else if($value[0] == 'isApproved'){
                  $modelNew->setIs_approved($value[1]);
              }else if($value[0] == 'countrycode'){
                  $modelNew->setCountrycode($value[1]);
              }else if($value[0] == 'username'){
                  $modelNew->setUsername($value[1]);
              }else if($value[0] == 'mealtype_name'){
                  $modelNew->setMealtype_name($value[1]);
              }else if($value[0] == 'religion_id'){
                  $modelNew->setReligion_id($value[1]);
              }else if($value[0] == 'time_of_day'){
                  $modelNew->setTime_of_day($value[1]);
          }
        }
    }


    $statement = new \database\Recipe($queryBuilderSelect, $queryBuilderUpdate);
    $result = $statement->select($modelOld);

    if(count($result[1][0]) === 1){
        $code = substr($statement->update($modelNew, $modelOld), 0, 2);
        parent::setHttpCode($code);
    }else{
        throw new \exception\NullPointerException("The request changed more than one record, please change your where scope");
    }
    }catch(\PDOException $e){
      parent::setHttpCode($e->getCode());
    }catch(\exception\NullPointerException $e){
        header('HTTP/1.0 400 Bad Request');
        //set the datatype to json for consistancy with all select query's
        header('Content-Type: application/json');
        //return the error code for easy debug
        echo json_encode($e->getMessage());
        restore_error_handler();
    }
  }

  public function delete(){
      try{
          $model = new \model\Recipe();
          if(null != $_GET['delete']){
              $arguments = parent::rebuildArguments($_GET['delete']);
              foreach($arguments as $value){
                  if($value[0] == 'id'){

                      $model->setId($value[1]);
                  }
              }
          }
          $statement = new \database\Recipe();
          $code = $statement->delete($model);
          $code = substr($code, 0, 2);
          echo $code;
          parent::setHttpCode($code);
      }catch(\PDOException $e){
          parent::setHttpCode($e->getCode());
      }catch(\exception\NullPointerException $e){
          header('HTTP/1.0 400 Bad Request');
          //set the datatype to json for consistancy with all select query's
          header('Content-Type: application/json');
          //return the error code for easy debug
          echo json_encode($e->getMessage());
          restore_error_handler();
      }
  }

  function error_handler($errno, $errstr, $errfile, $errline){
      if($errstr == 'Undefined index: name' || $errstr == 'Undefined index: description' || $errstr == 'Undefined index: isApproved' || $errstr == 'Undefined index: countrycode'|| $errstr == 'Undefined index: username' || $errstr == 'Undefined index: mealtype_name'
     || $errstr == 'Undefined index: religion_id'|| $errstr == 'Undefined index: time_of_day'){
          throw new \exception\NullPointerException("Get value isn't passed");
      }else{
          restore_error_handler();
      }
  }

function getWhereModel() : \model\Model {

    $model = new \model\Recipe();

          if(null != $_GET['where']){
              $arguments = parent::rebuildArguments($_GET['where']);
              $approvedArguments = $model->getVariables();
              foreach($arguments as $value){
                  if($value[0] == 'id'){
                      $model->setId($value[2]);
                  }else if($value[0] == 'name'){
                      $model->setName($value[2]);
                  }else if($value[0] == 'description'){
                      $model->setDescription($value[2]);
                  }else if($value[0] == 'isApproved'){
                      $model->setIs_approved($value[2]);
                  }else if($value[0] == 'countrycode'){
                      $model->setCountrycode($value[2]);
                  }else if($value[0] == 'username'){
                      $model->setUsername($value[2]);
                  }else if($value[0] == 'mealtype_name'){
                      $model->setMealtype_name($value[2]);
                  }else if($value[0] == 'religion_id'){
                      $model->setReligion_id($value[2]);
                  }else if($value[0] == 'time_of_day'){
                      $model->setTime_of_day($value[2]);

              }
            }
        }
        return $model;
    }

}

$recipe = new Recipe();
if(isset($_GET['name'])){
    $recipe->insert();
}else if(isset($_GET['set'])){
  $recipe->update();
}else if (isset($_GET['delete'])){
  $recipe->delete();
}else{
  $recipe->select();
}

 ?>
